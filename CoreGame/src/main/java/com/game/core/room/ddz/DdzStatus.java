package com.game.core.room.ddz;

import com.game.core.action.BaseAction;
import com.game.core.action.ddz.DdzIdleAction;
import com.game.core.action.ddz.FaPaiAction;
import com.game.core.room.interfaces.BaseGameState;
import com.game.core.room.BaseGameStateData;

import static com.game.core.room.SuperCreateNew.CreateNewCache.create;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public enum DdzStatus implements BaseGameState {
    Idle(0,new DdzIdleAction(),create(BaseGameStateData.class)),
    FaPai(1,new FaPaiAction(),create(BaseGameStateData.class)),
    Score(2,new DdzIdleAction(),create(BaseGameStateData.class)),
    AddRate(3,new DdzIdleAction(),create(BaseGameStateData.class)),
    Game(4,new DdzIdleAction(),create(BaseGameStateData.class));
    private int value;
    private BaseAction baseAction;
    private BaseGameStateData baseStatusData;
    DdzStatus(int value, BaseAction baseAction,BaseGameStateData baseStatusData){
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
    public BaseGameStateData createNew() {
        return baseStatusData.createNew();
    }
}
