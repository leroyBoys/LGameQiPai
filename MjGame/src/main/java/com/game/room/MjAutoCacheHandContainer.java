package com.game.room;

import com.game.core.room.card.AutoCacheHandContainer;

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

    public Map<Integer, List<Integer>> getCardCountMap() {
        return cardCountMap;
    }

    public Map<Integer, Integer> getCardNumMap() {
        return cardNumMap;
    }
}
