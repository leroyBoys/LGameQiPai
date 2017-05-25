package com.game.room;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.card.AutoCacheHandContainer;
import com.game.room.status.StepGameStatusData;
import com.module.net.NetGame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/24.
 */
public class MjAutoCacheHandContainer extends AutoCacheHandContainer {
    ////超过1个的数量映射表:数量-具体牌值
    protected Map<Integer,List<Integer>> cardCountMap = new HashMap<>();

    /**  cardNum - 数量 **/
    protected Map<Integer,Integer> cardNumMap = new HashMap<>();

    public void clear() {
        super.clear();
        cardCountMap.clear();
    }

    public synchronized void reLoad(List<Integer> hands){
        if(!isChange){
            return;
        }

        cardNumMap.clear();
        for (Integer card : hands) {
            Integer count = cardNumMap.get(card);
            if(count == null){
                count = 0;
            }
            count++;
            cardNumMap.put(card, count);

            if(count == 1 || count > 4){
                continue;
            }

            List<Integer> cards = cardCountMap.get(count);
            if(cards == null){
                cards = new LinkedList<>();
                cardCountMap.put(count,cards);
            }
            cards.add(card);
        }



        super.reLoad(hands);
    }

    public void check(List<Integer> hands) {
        super.check(hands);
    }

    public NetGame.NetOprateData getNetOprateData(BaseChairInfo info, StepGameStatusData stepStatus) {
        NetGame.NetOprateData netOprateData = null;

        switch (stepStatus.getAction().getActionType()){
            case GameConst.MJ.ACTION_TYPE_DA:
                netOprateData= Da(info,stepStatus);
                break;
            case GameConst.MJ.ACTION_TYPE_CHI:
                netOprateData= Chi(info,stepStatus);
                break;
            case GameConst.MJ.ACTION_TYPE_PENG:
                netOprateData= Peng(info,stepStatus);
                break;
            case GameConst.MJ.ACTION_TYPE_GANG:
                netOprateData= Gang(info,stepStatus);
                break;
        }

        System.out.println("----------------->robot:auto:actionType:"+netOprateData.getOtype()+" uid:"+info.getId());
        System.out.println(netOprateData.toString());
        return netOprateData;
    }

    public NetGame.NetOprateData Da( BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        retData.addDlist(info.getHandsContainer().getHandCards().get(0));
        retData.setOtype(stepStatus.getAction().getActionType());
        return retData.build();
    }

    public NetGame.NetOprateData Chi( BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        retData.addAllDlist(stepStatus.getCards());
        retData.setOtype(stepStatus.getAction().getActionType());
        return retData.build();
    }

    public NetGame.NetOprateData Peng( BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        retData.setOtype(stepStatus.getAction().getActionType());
        return retData.build();
    }

    public NetGame.NetOprateData Gang( BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        if(stepStatus.getCards() != null && !stepStatus.getCards().isEmpty()){
            retData.addAllDlist(stepStatus.getCards());
        }
        retData.setOtype(stepStatus.getAction().getActionType());
        retData.setDval(stepStatus.getiOptPlugin().getPlugin().getSubType());
        return retData.build();
    }

    public Map<Integer, List<Integer>> getCardCountMap() {
        return cardCountMap;
    }

    public Map<Integer, Integer> getCardNumMap() {
        return cardNumMap;
    }

}
