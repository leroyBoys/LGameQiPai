package com.game.core.room.card;

import com.game.core.config.RoomSetting;
import com.game.core.config.TablePluginManager;
import com.game.core.room.card.ICardPoolEngine;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
public class BaseCardPoolEngine<C> implements ICardPoolEngine<C> {
    /** 玩家选择增加的额外牌 */
    protected List<C> userSetStaticCardPool = new LinkedList<>();

    /** 牌池，未使用的牌 */
    protected List<C> cardPool = new LinkedList<>();
    private final int allSize;

    protected final int gameId;

    public BaseCardPoolEngine(int gameId,List<C> userSetStaticCardPool){
        this.gameId = gameId;
        RoomSetting roomSetting = TablePluginManager.getInstance().getRoomSetting(gameId);
        this.userSetStaticCardPool = userSetStaticCardPool;
        this.allSize = roomSetting.getCardNumPools().size()+(userSetStaticCardPool == null?0:userSetStaticCardPool.size());
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
