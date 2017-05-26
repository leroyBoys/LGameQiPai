package com.game.room;

import com.game.core.action.BaseAction;
import com.game.core.room.interfaces.BaseGameState;
import com.game.core.room.BaseGameStateData;
import com.game.room.action.*;
import com.game.room.status.XXGameStatusData;

import static com.game.core.room.SuperCreateNew.CreateNewCache.create;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public enum MjStatus implements BaseGameState {
    Idle(0,new MjIdleAction(), create(BaseGameStateData.DefaultStatusData.class)),
    Pao(1,new YaPaoAction(), create(BaseGameStateData.DefaultStatusData.class)),
    DingZhuang(2,new DingZhuangAction(), create(BaseGameStateData.SystemStatusData.class)),
    FaPai(3,new FaPaiAction(), create(BaseGameStateData.SystemStatusData.class)),
    Game(4,new GameAction(), create(XXGameStatusData.class)),
    ;
    private int value;
    private BaseAction baseAction;
    private BaseGameStateData baseStatusData;
    MjStatus(int value, BaseAction baseAction,BaseGameStateData baseStatusData){
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
