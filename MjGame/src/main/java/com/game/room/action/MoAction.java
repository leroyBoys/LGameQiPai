package com.game.room.action;

import com.game.core.constant.GameConst;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MoAction extends GameAction {
    private final static MoAction instance = new MoAction();
    private MoAction(){}

    public static MoAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_MOPAI;
    }

}
