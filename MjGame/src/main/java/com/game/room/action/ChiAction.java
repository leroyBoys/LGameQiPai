package com.game.room.action;

import com.game.core.constant.GameConst;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class ChiAction extends GameAction {
    private final static ChiAction instance = new ChiAction();
    private ChiAction(){}

    public static ChiAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_CHI;
    }
}
