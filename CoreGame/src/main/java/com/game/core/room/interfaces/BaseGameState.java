package com.game.core.room.interfaces;


import com.game.core.action.BaseAction;
import com.game.core.room.BaseGameStateData;
import com.game.core.room.SuperCreateNew;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public interface BaseGameState extends SuperCreateNew {
    public BaseGameStateData createNew();
    public BaseAction getAction();
    public int getValue();
}
