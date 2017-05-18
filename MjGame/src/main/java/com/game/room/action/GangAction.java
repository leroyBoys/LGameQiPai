package com.game.room.action;

import com.game.core.constant.GameConst;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GangAction extends GameAction {
    private final static GangAction instance = new GangAction();
    private GangAction(){}

    public static GangAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_HU;
    }

}
