package com.game.core.room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
public class BaseCardPoolEngine<C> implements ICardPoolEngine<C> {
    /** 牌池，未使用的牌 */
    protected List<C> cardPool = new LinkedList<>();
    private final int allSize;

    private final int gameId;

    public BaseCardPoolEngine(int gameId){
        this.gameId = gameId;
        this.allSize = 0;
    }

    @Override
    public void init() {
        cardPool.clear();
    }

    @Override
    public void shuffle() {
    }

    @Override
    public int getRemainCount() {
        return cardPool.size();
    }

    @Override
    public List<C> getRemainCards() {
        return cardPool;
    }

    @Override
    public int getAllSize() {
        return allSize;
    }
}
