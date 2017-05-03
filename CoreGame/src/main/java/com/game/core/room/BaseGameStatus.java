package com.game.core.room;


import com.game.core.action.BaseAction;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public interface BaseGameStatus extends SuperCreateNew{
    public BaseStatusData createNew();
    public BaseAction getAction();
    public int getValue();
}
