package com.game.room;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.card.AutoCacheHandContainer;
import com.game.room.status.StepGameStatusData;
import com.lgame.util.comm.RandomTool;
import com.lgame.util.json.JsonTool;
import com.logger.type.LogType;
import com.module.net.NetGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
            case GameConst.MJ.ACTION_TYPE_HU:
                netOprateData= Hu(info,stepStatus);
                break;
        }

        System.out.println("----------------->robot:auto:actionType:"+netOprateData.getOtype()+" uid:"+info.getId());
        System.out.println(netOprateData.toString());
        return netOprateData;
    }

    private NetGame.NetOprateData Hu(BaseChairInfo info, StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        retData.setOtype(stepStatus.getAction().getActionType());
        retData.setUid(info.getId());
        return retData.build();
    }

    public NetGame.NetOprateData Da( BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        retData.addDlist(getAutoDaCard(info.getHandsContainer().getHandCards()));
        retData.setOtype(stepStatus.getAction().getActionType());
        retData.setUid(info.getId());
        return retData.build();
    }

    public NetGame.NetOprateData Chi( BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        retData.addAllDlist(stepStatus.getCards());
        retData.setUid(info.getId());
        retData.setOtype(stepStatus.getAction().getActionType());
        return retData.build();
    }

    public NetGame.NetOprateData Peng(BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        retData.setUid(info.getId());
        if(RandomTool.Next(10)<7){
            retData.setOtype(GameConst.MJ.ACTION_TYPE_GUO);
        }else {
            retData.setOtype(stepStatus.getAction().getActionType());
        }
        return retData.build();
    }

    public NetGame.NetOprateData Gang( BaseChairInfo info,StepGameStatusData stepStatus) {
        NetGame.NetOprateData.Builder retData = NetGame.NetOprateData.newBuilder();
        if(stepStatus.getCards() != null && !stepStatus.getCards().isEmpty()){
            retData.addAllDlist(stepStatus.getCards());
        }
        retData.setUid(info.getId());
        retData.setOtype(stepStatus.getAction().getActionType());
        retData.setDval(stepStatus.getiOptPlugin().getPlugin().getSubType());
        return retData.build();
    }

    @Override
    public void removeCard(int card, int num) {
     //   playLog.info("remove:card:"+card+"  num:"+num);

        if(!addCards.isEmpty()){
            Iterator<Integer> ites = addCards.iterator();
            while (ites.hasNext()){
                if(ites.next()==card){
                    ites.remove();
                    if(--num == 0){
                        playLog.info("return:"+num);
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

   /*     String str = "=====>cache:"+ Arrays.toString(getCardCounts())+" cards-num:"+ JsonTool.getJsonFromBean(getCardNumMap());
        playLog.info(str);*/
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


    public int[] getCardCounts() {
        return cardCounts;
    }

  /*  public String toJson(){
        String str = "cache:"+" cards-num:"+ JsonTool.getJsonFromBean(getCardNumMap()+ "  "+Arrays.toString(getCardCounts()));
        playLog.info(str);
        return str;
    }*/

    public int getAutoDaCard(List<Integer> hands){
        Map<Integer,Integer> cardNumMap = new HashMap<>(5);
        ArrayList<Integer> cardNumLinks = new ArrayList<>(10);
        for (Integer c : hands) {
            Integer cardNum = cardNumMap.get(c);
            if(cardNum == null){
                cardNum = 0;
                cardNumLinks.add(c);
            }
            cardNumMap.put(c,cardNum+1);
        }

        Collections.sort(cardNumLinks);


        Set<Integer> moreThree = new HashSet<>();
        ArrayList<Integer> tempLinks = new ArrayList<>(3);
        List<ArrayList<Integer>> templisks = new ArrayList<>();

        for(Integer cardNum:cardNumLinks){//去单
            if(cardNumMap.get(cardNum) > 2){
                moreThree.add(cardNum);
                continue;
            }

            if(tempLinks.size() == 0){
            }else if(Math.abs(tempLinks.get(tempLinks.size()-1) - cardNum) == 1){
            }else if(tempLinks.size() == 1){
                if(cardNumMap.get(tempLinks.get(0)) != 2){
                    return tempLinks.get(0);
                }
                tempLinks.clear();
            }else if(tempLinks.size() == 2){

                for(Integer c:tempLinks){
                    if(cardNumMap.get(c) != 2){
                        return c;
                    }
                }

                templisks.add(tempLinks);
                tempLinks= new ArrayList<>(3);
            }else {
                if(!tempLinks.isEmpty()){
                    templisks.add(tempLinks);
                    tempLinks = new ArrayList<>(3);
                }
            }
            tempLinks.add(cardNum);
        }

        if(tempLinks.size() == 1){
            if(cardNumMap.get(tempLinks.get(0)) != 2){
                return tempLinks.get(0);
            }
        }else if(tempLinks.size() == 2){
            for(Integer c:tempLinks){
                if(cardNumMap.get(c) != 2){
                    return c;
                }
            }
            templisks.add(tempLinks);
        }

        if(!templisks.isEmpty()){
            return templisks.get(RandomTool.Next(templisks.size())).get(0);
        }

        for(Integer cardNum:cardNumLinks){
            if(moreThree.contains(cardNum)){
                continue;
            }
            return cardNum;
        }

        return hands.get(0);
    }
}
