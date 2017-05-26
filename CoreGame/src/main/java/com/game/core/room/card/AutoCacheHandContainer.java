package com.game.core.room.card;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/24.
 */
public abstract class AutoCacheHandContainer {
    protected volatile boolean isChange = true;
    protected List<Integer> addCards = new LinkedList<>();

    public void clear() {
        isChange = true;
    }

    public void reLoad(List<Integer> hands){
        isChange = false;
    }

    public void check(List<Integer> hands) {
        if(isChange){
            reLoad(hands);
        }
    }

    public void addCard(int card){
        addCards.add(card);
    }

    public void addCard(List<Integer> cards){
        addCards.addAll(cards);
    }

    public abstract void removeCard(int card,int num);

    /**
     * 含有数量为num的card的数量
     * @param num
     * @return
     */
    public abstract int containCardCount(int num);

    /**
     * 不推荐
     * @param cards
     */
    public final void removeCards(List<Integer> cards){
        Map<Integer,Integer> map = new HashMap<>();
        for(int card:cards){
            Integer tmp = map.get(card);
            if(tmp == null){
                tmp = 1;
            }else {
                tmp++;
            }
            map.put(card,tmp);
        }

        for(Map.Entry<Integer,Integer> cardEntry:map.entrySet()){
            removeCard(cardEntry.getKey(),cardEntry.getValue());
        }
    }
}
