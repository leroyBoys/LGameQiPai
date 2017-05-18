package com.game.room;

import com.game.action.MjCmd;
import com.game.core.constant.GameConst;
import com.game.core.factory.TableProducer;
import com.game.core.room.BaseStepHistory;
import com.game.core.room.BaseTableVo;
import com.game.socket.module.UserVistor;
import com.lgame.util.comm.RandomTool;
import com.lsocket.message.Response;
import com.module.core.ResponseCode;
import com.module.net.NetGame;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MjTable extends BaseTableVo<MjStatus,MjChairInfo> {
    /** 庄家id */
    private int bankId;
    ////顺序东北西南->0-3

    private int nextBankerUid;

    private final int defaultMaxRate = 10;
    private int maxFan;
    private int type;

    public MjTable(int ownerId,int maxSize,int id,int gameId, TableProducer tableProducer) {
        super(ownerId,maxSize,id, MjStatus.Idle,gameId,tableProducer);
    }

    @Override
    protected void initStatus() {
        this.setAllStatus(new MjStatus[]{MjStatus.Idle, MjStatus.Pao, MjStatus.FaPai, MjStatus.Game});
    }

    @Override
    protected void initChair(int maxSize) {
        chairs = new MjChairInfo[maxSize];
    }

    @Override
    public <History extends BaseStepHistory> History getStepHistoryManager() {
        return null;
    }

    @Override
    public void cleanTableCache() {

    }

    @Override
    public int getGameResponseCmd() {
        return MjCmd.Game.getValue();
    }

    @Override
    public int getGameResponseModule() {
        return MjCmd.Game.getModule();
    }

    @Override
    protected NetGame.NetExtraData.Builder getTableExtrData() {
        NetGame.NetExtraData.Builder extra = NetGame.NetExtraData.newBuilder();
        extra.addList(this.getBankId());
        extra.addList(this.getCardPool().getRemainCount());
        extra.addList(this.getCardPool().getAllSize());

        extra.addOperates(this.getTurnData());
        return extra;
    }

    @Override
    protected NetGame.NetExtraData getOtherNetExtraData(MjChairInfo mjChairInfo) {
        NetGame.NetExtraData.Builder extra = getNetExtraData(mjChairInfo);
        extra.addKvDatas(getHandCards(mjChairInfo,false));
        return extra.build();
    }

    @Override
    protected NetGame.NetExtraData getSelfNetExtraData(MjChairInfo mjChairInfo) {
        NetGame.NetExtraData.Builder extra = getNetExtraData(mjChairInfo);
        extra.addKvDatas(getHandCards(mjChairInfo,true));
        return extra.build();
    }

    protected NetGame.NetExtraData.Builder getNetExtraData(MjChairInfo mjChairInfo) {
        NetGame.NetExtraData.Builder extra = NetGame.NetExtraData.newBuilder();
        extra.addKvDatas(getOutCard(mjChairInfo));
        extra.addKvDatas(getHu(mjChairInfo));

        List<GroupCard> groupCards = mjChairInfo.getHandsContainer().getChiGangList();
        if(!groupCards.isEmpty()){
            for(GroupCard groupCard:groupCards){
                extra.addKvDatas(getPengChiGangCardType(groupCard));
            }
        }

        groupCards = mjChairInfo.getHandsContainer().getPengList();
        if(!groupCards.isEmpty()){
            for(GroupCard groupCard:groupCards){
                extra.addKvDatas(getPengChiGangCardType(groupCard));
            }
        }

        extra.addList(mjChairInfo.getScore());
        return extra;
    }

    protected final NetGame.NetKvData getOutCard(MjChairInfo mjChairInfo){
        NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
        netKvData.setK(1);
        netKvData.addAllDlist(mjChairInfo.getHandsContainer().getOutCards());
        return netKvData.build();
    }

    protected final NetGame.NetKvData getHandCards(MjChairInfo mjChairInfo,boolean isMySelf){
        NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
        netKvData.setK(2);
        netKvData.setV(mjChairInfo.getHandsContainer().getHandCards().size());
        if(isMySelf){
            netKvData.addAllDlist(mjChairInfo.getHandsContainer().getHandCards());
        }
        return netKvData.build();
    }

    protected final NetGame.NetKvData getHu(MjChairInfo mjChairInfo){
        NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
        netKvData.setK(4);
        netKvData.addAllDlist(mjChairInfo.getHandsContainer().getHuCards());
        return netKvData.build();
    }

    protected final NetGame.NetKvData getPengChiGangCardType(GroupCard groupCard){
        NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
        netKvData.setK(groupCard.getType());
        netKvData.addAllDlist(groupCard.getCards());
        return netKvData.build();
    }

    @Override
    public MjChairInfo createChair(UserVistor visitor) {
        MjChairInfo chairInfo = new MjChairInfo(visitor.getRoleId(),this);
        chairInfo.setIp(visitor.getIp().getIp());
        return chairInfo;
    }

    public Map<Integer,Integer[]> randomChairPosition(int diceCount){

        List<Integer[]> playerScore = new ArrayList<>(chairs.length);
        Map<Integer,Integer[]> diceDetail = new HashMap<>(chairs.length);
        for(int i = 0;i<chairs.length;i++){
            int count = diceCount;
            Integer[] allCounts = new Integer[diceCount];
            int allCountVaule = 0;
            while (count-- > 0){
                allCounts[count] = RandomTool.Next(6)+1;
                allCountVaule+=allCounts[count];
            }

            playerScore.add(new Integer[]{chairs[i].getId(),allCountVaule});
            diceDetail.put(chairs[i].getId(),allCounts);
        }


        Collections.sort(playerScore, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o2[1] - o1[1] ;
            }
        });

        for(int i=0; i<chairs.length; i++){
            chairs[i] = getChairByUid(playerScore.get(i)[0]);
            chairs[i].setIdx(i);
        }

        return diceDetail;
    }


    @Override
    public ResponseCode.Error setSelected(List<Integer> typeList) {
        ResponseCode.Error error = super.setSelected(typeList);
        if(error != ResponseCode.Error.succ){
            return error;
        }

        int maxRate = Math.min(typeList.get(1),defaultMaxRate);
        int type = typeList.get(2);
        this.setMaxFan(maxRate);
        this.setType(type);


        return ResponseCode.Error.succ;
    }
    //////////////////////////////////////////////////////

    /**
     * 发送可以操作的集合
     * @param actions
     */
    public NetGame.NetOprateData getCanDoActionsResponse(List<NetGame.NetKvData> actions){
        NetGame.NetOprateData.Builder canDoActions = NetGame.NetOprateData.newBuilder();
        canDoActions.setOtype(GameConst.MJ.ACTION_TYPE_CanDoActions);
        canDoActions.addAllKvDatas(actions);

        return canDoActions.build();
    }

    /**
     * h获得压跑数据
     * @return
     */
    public NetGame.NetOprateData.Builder getYaPaoNetOprateData() {
        NetGame.NetOprateData.Builder yaPao = NetGame.NetOprateData.newBuilder();
        yaPao.setOtype(GameConst.MJ.ACTION_TYPE_YAPao);
        for(int i = 0;i<chairs.length;i++){
            if(chairs[i] == null || chairs[i].isCanYaPao()){
                continue;
            }
            NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
            netKvData.setK(chairs[i].getId());
            netKvData.setV(chairs[i].getYapaoNum());
            yaPao.addKvDatas(netKvData);
        }
        return yaPao;
    }
    /////////////////////////////////////////////////////////////
    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
        this.setFocusIdex(this.getChairByUid(bankId).getIdx());
    }

    public int getNextBankerUid() {
        return nextBankerUid;
    }

    public int getMaxFan() {
        return maxFan;
    }

    public void setMaxFan(int maxFan) {
        this.maxFan = maxFan;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setNextBankerUid(int nextBankerUid) {
        this.nextBankerUid = nextBankerUid;
    }


}
