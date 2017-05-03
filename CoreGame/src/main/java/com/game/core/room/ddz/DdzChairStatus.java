package com.game.core.room.ddz;

import com.game.core.room.BaseChairStatus;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public enum DdzChairStatus implements BaseChairStatus {
    Idle(0),
    ready(1),
    Game(2);
    private int value;
    DdzChairStatus(int value){
        this.value = value;
    }

    public int getVal() {
        return value;
    }

}
