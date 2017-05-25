package com.game.room;

import com.game.core.config.RoomSetting;
import com.game.core.config.TablePluginManager;
import com.game.core.room.card.BaseCardPoolEngine;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
public class MjCardPoolEngine extends BaseCardPoolEngine<Integer> {
    protected Map<Integer,LinkedList<Integer>> outPool = new HashMap<>();
    private int lastPlayUid = 0;

    public MjCardPoolEngine(int gameId,List<Integer> userSetStaticCardPool){
        super(gameId,userSetStaticCardPool);
    }

    @Override
    public void init() {
        outPool.clear();
        lastPlayUid = 0;
        super.init();
    }

    @Override
    public void shuffle() {
        init();
        RoomSetting roomSetting = TablePluginManager.getInstance().getRoomSetting(gameId);
        cardPool.addAll(roomSetting.getCardNumPools());
        cardPool.addAll(roomSetting.getCardNumPools());
        cardPool.addAll(roomSetting.getCardNumPools());
        cardPool.addAll(roomSetting.getCardNumPools());

        if(userSetStaticCardPool != null && !userSetStaticCardPool.isEmpty()){
            cardPool.addAll(userSetStaticCardPool);
        }

        Collections.shuffle(cardPool);
    }

    @Override
    public int getRemainCount() {
        return cardPool.size();
    }

    @Override
    public List<Integer> getRemainCards() {
        return cardPool;
    }

    public int removeLastCard(){
        return outPool.get(lastPlayUid).removeLast();
    }

    public void playOutCard(int uid,int card){
        lastPlayUid = uid;

        LinkedList<Integer>  cards = outPool.get(uid);
        if(cards == null){
            cards = new LinkedList<>();
            outPool.put(uid,cards);
        }

        cards.add(card);
    }

    public List<Integer> getOutPool(int uid) {
        return outPool.get(uid);
    }
}
