package com.game.core.room;

import com.game.action.GameCommCmd;
import com.game.core.TableManager;
import com.game.core.action.BaseAction;
import com.game.core.constant.GameConst;
import com.game.core.factory.TableProducer;
import com.game.manager.DBServiceManager;
import com.game.manager.OnlineManager;
import com.game.manager.TimeCacheManager;
import com.game.socket.module.UserVistor;
import com.logger.log.SystemLogger;
import com.lsocket.message.Response;
import com.module.core.ResponseCode;
import com.module.net.NetGame;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public abstract class BaseTableVo<TStatus extends BaseGameStatus,Chair extends BaseChairInfo> implements Runnable{
    protected final Map<Integer,MessageQueue> messageQueue = new HashMap<>();
    public final boolean isMsgCache = false;//是否缓存队列
    private GameOverType gameOverType = GameOverType.NULL;
    private TStatus status;
    private Map<TStatus,BaseStatusData> statusDataMap = new HashMap<>(1);
    private int id;
    private boolean isGoodId = false;
    protected Chair[] chairs;
    protected int ownerId;
    protected int gameId;
    private Map<Integer,Chair> chairMap = new HashMap<>(3);

    private int step;
    private long timeOutTime;
    private int focusIdex;
    /***
     * 房间属性
     */
    private HashMap<AttributeKey, Object> attributeMap = new HashMap<>();
    private TStatus[] allStatus;
    private Set<TStatus> allStatusSet = new HashSet<>();
    /**
     * 当前状态的索引
     */
    private short curStatusIdex = 0;
    private ICardPoolEngine cardPoolEngine;
    /** 当前局数 */
    private int curRount = 1;
    private TableProducer tableProducer;
    protected StepHistory stepHistory;

    private final ReentrantLock tableLock = new ReentrantLock();
    private final ReentrantLock statusLock = new ReentrantLock();
    private final ReentrantLock addRemoveLock = new ReentrantLock();
    private volatile boolean isRun = false;

    private List<Integer> typeList;

    public BaseTableVo(int ownerId,int maxSize,int id,TStatus status,int gameId,TableProducer tableProducer){
        this.setStatus(status);
        this.id = id;
        this.ownerId = ownerId;
        this.gameId = gameId;
        this.tableProducer = tableProducer;
        initStatus();
        initCardPoolEngine();
        initChair(maxSize);
        initStepHistory();
       // lockKey = Tools.getCharacterAndNumber(6);
    }

    protected  void initStepHistory(){
        stepHistory = new StepHistory();
    }

    protected abstract void initStatus();

    protected abstract void initChair(int maxSize);

    public <T extends BaseStatusData> T getStatusData(){
        return (T) statusDataMap.get(status);
    }

    protected void initCardPoolEngine(){
        cardPoolEngine = new BaseCardPoolEngine(gameId,null);
    }

    public boolean addChair(UserVistor visitor){
        addRemoveLock.lock();
        try{
            if(chairMap.size() == chairs.length){
                visitor.sendError(ResponseCode.Error.room_is_full);
                return false;
            }

            if(chairMap.containsKey(visitor.getRoleId())){
                return false;
            }

            Chair chair = createChair(visitor);
            visitor.getGameRole().setRoomId(this.getId());
            DBServiceManager.getInstance().getUserService().updateRoleInfoRoomid(visitor.getRoleId(),this.getId());
            chairMap.put(chair.getId(),chair);
            chairs[chairMap.size()-1] = chair;
            chair.setIdx(chairMap.size()-1);

            messageQueue.put(visitor.getRoleId(),new MessageQueue(visitor,this));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            addRemoveLock.unlock();
        }

        return false;
    }

    public abstract Chair createChair(UserVistor visitor);

    public void removeChair(int charId){
        addRemoveLock.lock();
        try{
            Chair chair = chairMap.remove(charId);
            if(chair == null){
                return;
            }

            chairs[chair.getIdx()] = null;
            //更新数据库

            if(chairMap.isEmpty()){
                TableManager.getInstance().destroyTable(this.getId());
            }
        }catch (Exception e){}finally {
            addRemoveLock.unlock();
        }
    }

    @Override
    public void run() {
        if(isRun){
            return;
        }
        final ReentrantLock putLock = this.statusLock;
        putLock.lock();
        try {
            if(isRun){
                return;
            }
            isRun = true;
        } finally {
            putLock.unlock();
        }

        try{
            trigger();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            isRun = false;
        }
    }

    public final void trigger(){
        BaseAction action = status.getAction();
        if(statusDataMap.get(status).isOver()){
       // if(action.isChangeToNextStatus(this)){
            changeNextStaus();
            action.overAction(this);
            this.getStatus().getAction().initAction(this);

            if(getStatusData().isOver()){
                trigger();
            }
            return;
        }
        action.tick(this);
    }

    private void changeNextStaus(){
        if(curStatusIdex >= allStatus.length-1){
            curStatusIdex = 0;
        }else {
            curStatusIdex++;
        }

        this.setStatus(allStatus[curStatusIdex]);
    }

    public StepHistory getStepHistoryManager(){
        return stepHistory;
    }

    public TStatus getStatus() {
        return status;
    }

    public void setStatus(TStatus status) {
        this.status = status;
        Map<TStatus,BaseStatusData> tmpStatusDataMap = new HashMap<>(1);
        tmpStatusDataMap.put(status,status.createNew());
        statusDataMap = tmpStatusDataMap;
    }

    public Chair getChairByUid(int roleId) {
        return chairMap.get(roleId);
    }

    public int getCurChirCount(){
        return chairMap.size();
    }

    public void addAttribute(AttributeKey key,Object value){
        attributeMap.put(key,value);
    }

    public int getAttributeValue(AttributeKey key,int defaultValue){
        Object obj = attributeMap.get(key);
        if(obj == null){
            return defaultValue;
        }

        return (int)obj;
    }

    public <TV> TV getAttributeValue(AttributeKey key){
        Object obj = attributeMap.get(key);
        if(obj == null){
            return null;
        }
        return (TV) obj;
    }

    public synchronized int getChairCountByStatus(BaseChairStatus status) {
        int count = 0;
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null || chairs[i].getStatus().getVal() != status.getVal()){
                continue;
            }
            count++;
        }

        return count;
    }

    public void cleanTableCache(){
        step = 0;
        timeOutTime = 0;
        gameOverType = GameOverType.NULL;

        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null){
                continue;
            }
            chairs[i].clean();
        }
    }

    public void resetTableStatus(){
        status = allStatus[0];
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null){
                continue;
            }
            chairs[i].resetStatus();
        }
    }

    public boolean doAction(TStatus fromStatus, Response response, int roleId, NetGame.NetOprateData oprateData){
        if(fromStatus != null && fromStatus != this.getStatus()){
            return false;
        }

        this.getStatus().getAction().doAction(this,response,roleId,oprateData);

        if(this.getStatusData().isOver()){
            TableManager.getInstance().trigger(getId());
        }
        return true;
    }

    public <CardPoo extends ICardPoolEngine> CardPoo getCardPool(){
        return (CardPoo) cardPoolEngine;
    }

    public void leaveOffline(int roleId){
        messageQueue.get(roleId).setVistor(null);
        this.getChairByUid(roleId).setOnline(false);
        sendChairStatusMsgWithOutUid(roleId);
    }

    public boolean addRound(){
        curRount++;
        int all = this.getAttributeValue(AttributeKey.AllRount,0);
        if(all == 0){
            return true;
        }

        return all>= curRount;
    }

    public int nextFocusIndex(int focsIndex) {
        focsIndex = ++focsIndex == this.getChairs().length ? 0 : focsIndex;
        return focsIndex;
    }
    ///////////////////////////////////////////////////////////////////////
    public abstract int getGameResponseCmd();

    public abstract int getGameResponseModule();

    public void sendGameResponse(NetGame.NetOprateData hands,UserVistor vistor,int seq) {
        Response response = Response.defaultResponse(getGameResponseModule(),getGameResponseCmd(),seq);
        NetGame.NetResponse netResponse = getNetResposeOnly(hands);
        response.setObj(netResponse);
        vistor.sendMsg(response);
    }

    public void sendGameResponse(List<NetGame.NetOprateData> hands,UserVistor vistor,int seq) {
        Response response = Response.defaultResponse(getGameResponseModule(),getGameResponseCmd(),seq);
        NetGame.NetResponse netResponse = getNetRespose(hands);
        response.setObj(netResponse);
        vistor.sendMsg(response);
    }

    public void sendGameResponse(NetGame.NetOprateData hands,int roleId,int seq) {
        UserVistor v = OnlineManager.getIntance().getRoleId(roleId);
        if(v == null){
            return;
        }
        sendGameResponse(hands,v,seq);
    }

    public void sendGameResponse(List<NetGame.NetOprateData> hands,int roleId,int seq) {
        UserVistor v = OnlineManager.getIntance().getRoleId(roleId);
        if(v == null){
            return;
        }
        sendGameResponse(hands,v,seq);
    }


    public NetGame.NetResponse getNetRespose(List<NetGame.NetOprateData> operdata) {
        NetGame.NetResponse.Builder response = NetGame.NetResponse.newBuilder();
        if(operdata != null){
            response.addAllOperateDatas(operdata);
        }

        response.setRetStatus(ResponseCode.Error.succ.value());
        response.setStatus(status.getValue());
        response.setStep(step);
        return response.build();
    }

    public NetGame.NetResponse getNetResposeOnly(NetGame.NetOprateData operdata) {
        NetGame.NetResponse.Builder response = NetGame.NetResponse.newBuilder();
        if(operdata != null){
            response.addOperateDatas(operdata);
        }

        response.setRetStatus(ResponseCode.Error.succ.value());
        response.setStatus(status.getValue());
        response.setStep(step);
        return response.build();
    }

    /**
     *
     * @param roleid
     * @return
     */
    public NetGame.RQCreateRoom getEnterRoomMsg(int roleid){
        NetGame.RQCreateRoom.Builder rqCreateRoom = NetGame.RQCreateRoom.newBuilder();
        rqCreateRoom.setRoomId(this.getId());
        rqCreateRoom.setGameId(this.getGameId());
        rqCreateRoom.setCurRount(this.curRount);
        rqCreateRoom.setOwnerId(this.getOwnerId());
        rqCreateRoom.setGameStatus(this.status.getValue());
        rqCreateRoom.addAllType(typeList);
        NetGame.NetExtraData.Builder extra = this.getTableExtrData();
        if(extra != null){
            rqCreateRoom.setExtra(extra);
        }

        List<NetGame.NetUserData> netUserDatas = new LinkedList<>();
        NetGame.NetUserData me = null;
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null){
                continue;
            }
            if(roleid == chairs[i].getId()){
                me = getSelfNetUserData(chairs[i]);
                continue;
            }
            netUserDatas.add(getOtherNetUserData(chairs[i]));
        }
        netUserDatas.add(me);
        rqCreateRoom.addAllUsers(netUserDatas);
        return rqCreateRoom.build();
    }

    /**
     * 牌局额外数据
     * @return
     */
    protected abstract NetGame.NetExtraData.Builder getTableExtrData();

    public NetGame.NetUserData getOtherNetUserData(Chair chair){
        NetGame.NetUserData.Builder netUserData = getNetUserData(chair);
        NetGame.NetExtraData extra = getOtherNetExtraData(chair);
        if(extra != null){
            netUserData.setExtra(extra);
        }
        return netUserData.build();
    }

    public NetGame.NetUserData getSelfNetUserData(Chair chair){
        NetGame.NetUserData.Builder netUserData = getNetUserData(chair);
        NetGame.NetExtraData extra = getSelfNetExtraData(chair);
        if(extra != null){
            netUserData.setExtra(extra);
        }
        return netUserData.build();
    }

    protected abstract NetGame.NetExtraData getOtherNetExtraData(Chair chair);

    protected abstract NetGame.NetExtraData getSelfNetExtraData(Chair chair);

    private NetGame.NetUserData.Builder getNetUserData(Chair chair){
        NetGame.NetUserData.Builder netUserData = NetGame.NetUserData.newBuilder();
        netUserData.setUid(chair.getId());
        netUserData.setImage(chair.getImage());
        netUserData.setStatus(getChairStatusToClient(chair));
        netUserData.setIdex(chair.getIdx());
        return netUserData;
    }

    public int getChairStatusToClient(Chair chair){
        int stats = 0 | (chair.isOnline?1:0);
        stats = stats | (chair.isAuto?1>>1:0);

        boolean isReady = true;
        if(status.getValue() == 0 && !((BaseStatusData.DefaultStatusData)getStatusData()).contains(chair.getId())){
            isReady = false;
        }

        stats = stats | (isReady?1>>2:0);
        return stats;
    }

    /**
     * 设置房间设置信息
     * @param typeList:0:局数,1:最大番数,2:玩法位集合
     * @return
     */
    public ResponseCode.Error setSelected(List<Integer> typeList) {
        this.setTypeList(typeList);
        int roundMax = typeList.get(0);
        if(!tableProducer.getGen().getCardSetMap().containsKey(roundMax)){
            SystemLogger.error(this.getClass(),"config not have rount:"+roundMax);
            return ResponseCode.Error.parmter_error;
        }
        return ResponseCode.Error.succ;
    }

    protected final NetGame.NetOprateData getTurnData(){
        NetGame.NetOprateData.Builder netKvData = NetGame.NetOprateData.newBuilder();
        netKvData.setUid(focusIdex < 0 ? 0:this.chairs[this.focusIdex].getId());
        netKvData.setOtype(GameConst.ACTION_TYPE_TURN);
        netKvData.setDval(Math.max(0,(int) ((timeOutTime - TimeCacheManager.getInstance().getCurTime())/1000)));
        return netKvData.build();
    }


    public void addMsgQueue(int roleId, List<NetGame.NetOprateData> msgs,int seq){
        messageQueue.get(roleId).addMsgList(msgs,seq);
    }

    public void addMsgQueue(int roleId, NetGame.NetOprateData msg,int seq){
        messageQueue.get(roleId).addMsg(msg,seq);
    }

    public void addMsgQueueAll(List<NetGame.NetOprateData> msgs,int seq){
        for(int i = 0;i<chairs.length;i++) {
            if (chairs[i] == null) {
                continue;
            }
            messageQueue.get(chairs[i].getId()).addMsgList(msgs,seq);
        }
    }

    public void addMsgQueueAll(NetGame.NetOprateData msg,int seq){
        for(int i = 0;i<chairs.length;i++) {
            if (chairs[i] == null) {
                continue;
            }
            messageQueue.get(chairs[i].getId()).addMsg(msg,seq);
        }
    }


    /**
     * 投票解散，返回是否解散
     * @param vistor
     * @param isAgree
     * @return
     */
    public synchronized boolean vote(UserVistor vistor, boolean isAgree){
        Set<Integer> votes = getAttributeValue(BaseTableVo.AttributeKey.VoteData);
        if(votes != null){
            if(votes.contains(vistor.getRoleId())){
                return false;
            }

            if(!isAgree){//拒绝
                attributeMap.remove(BaseTableVo.AttributeKey.VoteData);

                NetGame.RQVote.Builder netVote = NetGame.RQVote.newBuilder();
                netVote.setIsagree(false);
                netVote.setUid(vistor.getRoleId());
                this.sendMsgWithOutUid(Response.defaultResponse(GameConst.MOUDLE_GameComm,GameCommCmd.VoteDestroy.getValue(),0,netVote.build()),0);
                return false;
            }
        }else {
            votes = new HashSet<>();
            addAttribute(BaseTableVo.AttributeKey.VoteData,votes);

            NetGame.RQVote.Builder netVote = NetGame.RQVote.newBuilder();//发起投票
            netVote.setIsagree(true);
            this.sendMsgWithOutUid(Response.defaultResponse(GameConst.MOUDLE_GameComm,GameCommCmd.VoteDestroy.getValue(),0,netVote.build()),vistor.getRoleId());
        }

        if(votes.size()-1 == this.getChairs().length){//解散
            return true;
        }
        votes.add(vistor.getRoleId());

        NetGame.RQVote.Builder netVote = NetGame.RQVote.newBuilder();
        netVote.setIsagree(true);
        netVote.setUid(vistor.getRoleId());
        vistor.sendMsg(Response.defaultResponse(GameConst.MOUDLE_GameComm,GameCommCmd.VoteDestroy.getValue(),0,netVote.build()));
        return false;
    }


    ////////////////////////发消息//////////////////////////////////////

    public void sendMsgWithOutUid(Response otherResponse, int roleId) {
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null || chairs[i].getId() == roleId){
                continue;
            }
            //发送
            UserVistor vistor = OnlineManager.getIntance().getRoleId(chairs[i].getId());
            if(vistor == null){
                continue;
            }

            vistor.sendMsg(otherResponse);
        }
    }

    public void sendMsgToUid(Response otherResponse, int roleId) {
        //发送
        UserVistor vistor = OnlineManager.getIntance().getRoleId(roleId);
        if(vistor == null){
            return;
        }
        vistor.sendMsg(otherResponse);
    }

    public void sendError(ResponseCode.Error error, int roleId) {
        //发送
        UserVistor vistor = OnlineManager.getIntance().getRoleId(roleId);
        if(vistor == null){
            return;
        }
        vistor.sendError(error);
    }

    public void sendMsgAll(Response otherResponse) {
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null){
                continue;
            }
            //发送
            UserVistor vistor = OnlineManager.getIntance().getRoleId(chairs[i].getId());
            if(vistor == null){
                continue;
            }

            vistor.sendMsg(otherResponse);
        }
    }

    /**
     * 发送玩家状态
     * @param roleId
     */
    public void sendChairStatusMsgWithOutUid(int roleId){
        NetGame.RQUserStatus.Builder rpEnterRoom = NetGame.RQUserStatus.newBuilder();
        rpEnterRoom.setStatus(getChairStatusToClient(getChairByUid(roleId)));

        //给其他人发送
        sendMsgWithOutUid(Response.defaultResponse(GameCommCmd.CREATE_TABLE.getModule(),GameCommCmd.UserStatus.getValue(),0,rpEnterRoom.build()),roleId);
    }

    /**
     * 发送结算信息
     */
    protected abstract void sendSettlementDetailMsg();

    /**
     * 发送结算信息
     */
    public void sendSettlementMsg(){
        if(getGameOverType() == GameOverType.NULL){
            return;
        }
        sendSettlementDetailMsg();
    }

    ////////////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public boolean isGoodId() {
        return isGoodId;
    }

    public void setGoodId(boolean goodId) {
        isGoodId = goodId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Chair[] getChairs() {
        return chairs;
    }

    public ReentrantLock getTableLock() {
        return tableLock;
    }

    public int getGameId() {
        return gameId;
    }

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public int getFocusIdex() {
        return focusIdex;
    }

    public GameOverType getGameOverType() {
        return gameOverType;
    }

    public void setGameOverType(GameOverType gameOverType) {
        this.gameOverType = gameOverType;
    }

    public void setFocusIdex(int focusIdex) {
        this.focusIdex = focusIdex;
    }

    public void setAllStatus(TStatus[] allStatus) {
        this.allStatus = allStatus;
        allStatusSet.clear();
        allStatusSet.addAll(Arrays.asList(allStatus));
    }

    public TableProducer getTableProducer() {
        return tableProducer;
    }

    public TStatus[] getAllStatus() {
        return allStatus;
    }

    public Set<TStatus> getAllStatusSet() {
        return allStatusSet;
    }

    public MessageQueue getMessageQueue(int roleId){
        return messageQueue.get(roleId);
    }

    public enum AttributeKey{
        /** 总局数 */
        AllRount,
        /**
         * 投票数据
         */
        VoteData
    }
}
