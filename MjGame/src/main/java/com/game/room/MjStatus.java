package com.game.room;

import com.game.core.action.BaseAction;
import com.game.core.room.BaseGameStatus;
import com.game.core.room.BaseStatusData;
import com.game.room.action.*;

import static com.game.core.room.SuperCreateNew.CreateNewCache.create;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public enum MjStatus implements BaseGameStatus {
    Idle(0,new MjIdleAction(), create(BaseStatusData.DefaultStatusData.class)),
    Pao(1,new YaPaoAction(), create(BaseStatusData.DefaultStatusData.class)),
    DingZhuang(2,new DingZhuangAction(), create(BaseStatusData.class)),
    FaPai(3,new FaPaiAction(), create(BaseStatusData.class)),
    Game(4,new GameAction(), create(XXGameStatusData.class)),
    ;
    private int value;
    private BaseAction baseAction;
    private BaseStatusData baseStatusData;
    MjStatus(int value, BaseAction baseAction,BaseStatusData baseStatusData){
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
