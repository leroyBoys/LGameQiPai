package com.game.core.room.ddz;

import com.game.core.action.BaseAction;
import com.game.core.action.ddz.DdzIdleAction;
import com.game.core.action.ddz.FaPaiAction;
import com.game.core.room.BaseGameStatus;
import com.game.core.room.BaseStatusData;

import static com.game.core.room.SuperCreateNew.CreateNewCache.create;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public enum DdzStatus implements BaseGameStatus {
    Idle(0,new DdzIdleAction(),create(BaseStatusData.class)),
    FaPai(1,new FaPaiAction(),create(BaseStatusData.class)),
    Score(2,new DdzIdleAction(),create(BaseStatusData.class)),
    AddRate(3,new DdzIdleAction(),create(BaseStatusData.class)),
    Game(4,new DdzIdleAction(),create(BaseStatusData.class));
    private int value;
    private BaseAction baseAction;
    private BaseStatusData baseStatusData;
    DdzStatus(int value, BaseAction baseAction,BaseStatusData baseStatusData){
        this.value = value;
        this.baseAction =  baseAction;
        this.baseStatusData = baseStatusData;
    }

    public int getValue() {
        return value;
    }

    public BaseAction getAction() {
        return baseAction;
    }

    @Override
    public BaseStatusData createNew() {
        return baseStatusData.createNew();
    }
}
