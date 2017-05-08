package com.game.core.room;

import com.game.action.GameCommCmd;
import com.game.core.TableManager;
import com.game.core.action.BaseAction;
import com.game.core.constant.GameConst;
import com.game.core.factory.TableProducer;
import com.game.manager.DBServiceManager;
import com.game.manager.TimeCacheManager;
import com.game.socket.module.UserVistor;
import com.logger.log.SystemLogger;
import com.lsocket.message.Response;
import com.module.core.ResponseCode;
import com.module.net.NetCommon;
import com.module.net.NetGame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public abstract class BaseTableVo<TStatus extends BaseGameStatus,Chair extends BaseChairInfo> implements Runnable{
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
    protected TStatus[] allStatus;
    /**
     * 当前状态的索引
     */
    private short curStatusIdex = 0;
    private ICardPoolEngine cardPoolEngine;
    /** 当前局数 */
    private int curRount = 1;
    private TableProducer tableProducer;

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
       // lockKey = Tools.getCharacterAndNumber(6);
    }

    protected abstract void initStatus();

    protected abstract void initChair(int maxSize);

    public <T extends BaseStatusData> T getStatusData(){
        return (T) statusDataMap.get(status);
    }

    protected void initCardPoolEngine(){
        cardPoolEngine = new BaseCardPoolEngine(gameId);
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
            visitor.setRoomId(this.getId());
            DBServiceManager.getInstance().getUserService().updateRoleInfoRoomid(visitor.getRoleId(),this.getId());
            chairMap.put(chair.getId(),chair);
            chairs[chairMap.size()-1] = chair;
            chair.setIdx(chairMap.size()-1);
            return true;
        }catch (Exception e){}finally {
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

            chairs[chair.getIdx()] = chair;
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

    public abstract <History extends BaseStepHistory > History getStepHistoryManager();

    public TStatus getStatus() {
        return status;
    }

    public void setStatus(TStatus status) {
        this.status = status;
        Map<TStatus,BaseStatusData> tmpStatusDataMap = new HashMap<>(1);
        tmpStatusDataMap.put(status,status.createNew());
        statusDataMap = tmpStatusDataMap;
    }

    public Chair getChairByUid(Integer uid) {
        return chairMap.get(uid);
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
    }

    public void resetChairInfo(){
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null){
                continue;
            }
            chairs[i].clean();
        }
    }

    public boolean doAction(TStatus fromStatus, Response response, UserVistor visitor, NetCommon.NetOprateData oprateData){
        if(fromStatus != null && fromStatus != this.getStatus()){
            return false;
        }

        this.getStatus().getAction().doAction(this,response,visitor,oprateData);

        if(this.getStatusData().isOver()){
            TableManager.getInstance().trigger(getId());
        }
        return true;
    }

    public <CardPoo extends ICardPoolEngine> CardPoo getCardPool(){
        return (CardPoo) cardPoolEngine;
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

    public NetCommon.NetResponse getNetRespose(List<NetCommon.NetOprateData> operdata) {
        NetCommon.NetResponse.Builder response = NetCommon.NetResponse.newBuilder();
        if(operdata != null){
            response.addAllOperateDatas(operdata);
        }

        response.setRetStatus(1);
        response.setStatus(status.getValue());
        response.setStep(step);
        return response.build();
    }

    /**
     *
     * @param roleid
     * @return
     */
    public NetGame.RQCreateRoom sendEnterRoom(int roleid){
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

    public void Join(UserVistor vistor){
        addChair(vistor);
    }

    ////////////////////////发消息//////////////////////////////////////

    public void sendMsgWithOutUid(Response otherResponse, int uid) {
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null || chairs[i].getId() == uid){
                continue;
            }
            //发送

        }
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

    public void setFocusIdex(int focusIdex) {
        this.focusIdex = focusIdex;
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
