package com.game.room;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.card.AutoCacheHandContainer;
import com.game.room.status.StepGameStatusData;
import com.module.net.NetGame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/24.
 */
public class MjAutoCacheHandContainer extends AutoCacheHandContainer {
    ////每个单牌最大数量
    private final static int EVERYMAXCOUNT = 4;

    ///不同数量下对应的牌的张数(2包含1,3包含1，2,；4包含1,2,3)
    protected int[] cardCounts = new int[EVERYMAXCOUNT+1];
    /**  cardNum - 数量 **/
    protected Map<Integer,Integer> cardNumMap = new HashMap<>();

    public void clear() {
        super.clear();
        cardCounts = new int[EVERYMAXCOUNT+1];
        cardNumMap.clear();
    }

    public void reLoad(List<Integer> hands){
        if(hands.isEmpty()){
            return;
        }

        for(int card:hands){
            addNewCard(card);
        }
    }

    private void refreshCardNumMap() {
        if(addCards.isEmpty()){
            return;
        }

        for(int card:addCards){
            addNewCard(card);
        }

        addCards.clear();
    }


    public void check(List<Integer> hands) {
    //    super.check(hands);
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

    @Override
    public void removeCard(int card, int num) {
        if(!addCards.isEmpty()){
            Iterator<Integer> ites = addCards.iterator();
            while (ites.hasNext()){
                if(ites.next()==card){
                    ites.remove();
                    if(--num == 0){
                        return;
                    }
                }
            }
        }

        Integer curNum = cardNumMap.get(card);
        if(curNum == null){
            return;
        }

        cardCounts[curNum]--;
        curNum = curNum-num;
        if(curNum > 0){
            cardCounts[curNum]++;
            cardNumMap.put(card,curNum);
        }else {
            cardNumMap.remove(card);
        }
    }

    protected void addNewCard(int card) {
        Integer curNum = cardNumMap.get(card);
        if(curNum == null){
            curNum = 1;
        }else {
            cardCounts[curNum]--;
            curNum = Math.min(++curNum,EVERYMAXCOUNT);
        }

        cardCounts[curNum]++;
        cardNumMap.put(card,curNum);
    }

    @Override
    public int containCardCount(int num) {
        num = Math.min(num,EVERYMAXCOUNT);
        if(addCards.isEmpty()){
            return cardCounts[num];
        }

        refreshCardNumMap();
        return cardCounts[num];
    }

    public Map<Integer, Integer> getCardNumMap() {
        refreshCardNumMap();
        return cardNumMap;
    }

    public boolean containCard(int cardNum){
        if(cardNumMap.containsKey(cardNum)){
            return true;
        }

        return addCards.contains(cardNum);
    }

    public int getCardCount(int cardNum){
        Integer num = cardNumMap.get(cardNum);
        num = (num == null?0:num);
        if(!addCards.isEmpty()){
            for(int card:addCards){
                if(card == cardNum){
                    num++;
                }
            }
        }
        return num;
    }

}
