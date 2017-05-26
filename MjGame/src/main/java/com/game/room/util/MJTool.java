package com.game.room.util;

import com.game.room.GroupCard;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/25.
 */
public class MJTool {
    /** 四风 */
    public static final List<Integer> SIFENGPOOL = getList(41,41,41,41,42,42,42,42,43,43,43,43,44,44,44,44);
    /** 中发白 */
    public static final List<Integer> ZHONGFABAI = getList(45,45,45,45,46,46,46,46,47,47,47,47);

    private static final List<Integer> getList(Integer... cards){
        List<Integer> list = new LinkedList<>();
        Collections.addAll(list,cards);
        return list;
    }

    /***
     * 清一色（包含玩条筒，东西南北和中发白）
     * @param handCards
     * @param groupList
     * @return
     */
    public static boolean oneCorlor(List<Integer> handCards, List<GroupCard> groupList) {
        int temp = 0;
        for (Integer c : handCards) {
            if(c > 40){
                continue;
            }

            if (temp == 0) {
                temp = c / 10;
                continue;
            }
            if (c / 10 != temp)
                return false;
        }

        if(groupList == null || groupList.isEmpty()){
            return true;
        }

        for (GroupCard cg : groupList) {
            int card = 0;
            if(cg.getCards() != null && !cg.getCards().isEmpty()){
                card = cg.getCards().get(0);
            }

            if(card == 0 || card > 40){
                continue;
            }

            if (temp != card / 10)
                return false;
        }
        return true;
    }

    /**
     * 清一色（只有万条筒）
     * @param handCards
     * @param groupList
     * @return
     */
    public static boolean oneCorlorSimple(List<Integer> handCards, List<GroupCard> groupList) {
        int temp = 0;
        for (Integer c : handCards) {
            if (temp == 0) {
                temp = c / 10;
                continue;
            }
            if (c / 10 != temp)
                return false;
        }

        if(groupList == null || groupList.isEmpty()){
            return true;
        }

        for (GroupCard cg : groupList) {
            if (temp != cg.getCards().get(0) / 10)
                return false;
        }
        return true;
    }

    public static int[] toCardArray(List<Integer> handCards,int extraLength){
        int lengthSize = handCards.size()+extraLength;

        int[] cardsTemp = new int[lengthSize];
        int i = 0;
        for (int card:handCards) {
            cardsTemp[i++] = card;
        }
        return cardsTemp;
    }

    /**
     * 普通的胡牌（没有任何附加规则限制）
     * @param cardsTemp
     * @return
     */
    public static boolean isSimpleHu(int[] cardsTemp,Map<Integer, Integer> map){
        boolean res = false;
        //int[] cardsTemp = new int[]{11,11,21,21,12,12,14,14,15,15,15,16,17,18};
        if(map == null){
            map = ArrayHandsCardCount(cardsTemp);
        }

        for (Integer cNum : map.keySet()) {
            if (map.get(cNum) > 1) {
                int[] rescards = MJTool.ArrayRemove(cardsTemp, cNum, 2);
                res = MJTool.isSentence(rescards);
                if (res) {
                    break;
                }
            }
        }

        return res;
    }

    /**
     * 胡牌判断，每次移除对子后还需要判断是否有坎子
     * 12333这种牌型不能算坎子
     * @param cardsTemp
     * @param groupList
     * @return
     */
    public static boolean isHu(int[] cardsTemp, List<GroupCard> groupList) {
        boolean openCardHasKan = openCardsHasKan(groupList);
        boolean res = false;
        if (cardsTemp == null || cardsTemp.length == 0) {
            return res;
        }

        if ((cardsTemp.length - 2) % 3 != 0) {
            return res;
        }

        if(openCardHasKan){ //明牌有坎子,直接判断
            return isSimpleHu(cardsTemp,null);
        }

        //把手牌里的坎子移除，再判断是否胡牌，防止123334455这种牌型
        HashMap<Integer, Integer> map = ArrayHandsCardCount(cardsTemp);
        for(Map.Entry<Integer,Integer> count : map.entrySet()){
            if( count.getValue() >=3 && count.getKey() <= 44){
                //移除坎子，再进行比较
                int[] rescards = ArrayRemove(cardsTemp, count.getKey(), 3);
                if(isSimpleHu(rescards,null)){
                    return true;
                }
            }else if(count.getValue() >1 && count.getKey() > 44){ //有中发白的对子
                if(isSimpleHu(cardsTemp,null)){
                    return true;
                }
            }
        }

        return false;
    }

    //明牌是否有坎子
    public static boolean openCardsHasKan(List<GroupCard> groupList) {
        for (GroupCard group : groupList) {
            List<Integer> list = group.getCards();

            int firNum = list.get(0);
            if(firNum > 40){ //三剑客或东南西北都可以
                return true;
            }

            int subCount = 0;
            for (Integer mj : list) {
                if (mj == firNum) {
                    subCount++;
                }
            }
            if (subCount > 2) {
                return true;
            }
        }
        return false;
    }


    // 所有手牌牌计数
    public static HashMap<Integer, Integer> ArrayHandsCardCount(int[] cardsTemp) {
        // 计数
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < cardsTemp.length; i++) {
            if (!map.containsKey(cardsTemp[i])) {
                map.put(cardsTemp[i], 0);
            }
            int count = map.get(cardsTemp[i]) + 1;
            map.put(cardsTemp[i], count);
        }
        return map;
    }

    // 一句牌（顺子）
    public static boolean isSentence(int[] handCards) {
        if (handCards != null && handCards.length > 0) {
            handCards = ArraySort(handCards);
            while (handCards.length > 0) {
                int[] temp = ArrayRemove(handCards, handCards[0], 3);
                if (temp == null) {
                    // 移除碰失败，移除顺子
                    int cardTemp = handCards[0];
                    if (cardTemp > 40)
                        return false;
                    for (int i = 0; i < 3; i++) {
                        temp = ArrayRemove(handCards, cardTemp + i, 1);
                        if (temp == null)
                            return false;
                        handCards = temp;
                    }
                }
                handCards = temp;
            }
        }
        return true;
    }


    // 排序
    public static int[] ArraySort(int[] cards) {
        if (cards != null) {
            for (int i = 0; i < cards.length; i++) {
                Integer tmp = cards[i];
                for (int j = i + 1; j < cards.length; j++) {
                    if (tmp.intValue() > cards[j]) {
                        tmp = cards[j];
                        cards[j] = cards[i];
                        cards[i] = tmp;
                    }
                }
            }
        }
        return cards;
    }

    /** 移除对应个数牌，失败返回null */
    public static int[] ArrayRemove(int[] cards, int ocard, int countLimit) {
        int count = 0;
        int[] reCards = new int[cards.length - countLimit];
        int index = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == ocard && count < countLimit) {
                count++;
                continue;
            }
            if (index < reCards.length)
                reCards[index++] = cards[i];
            else {
                return null;
            }
        }
        if (count != countLimit)
            return null;
        return reCards;
    }

}
