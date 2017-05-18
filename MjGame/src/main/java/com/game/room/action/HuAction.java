package com.game.room.action;

import com.game.core.constant.GameConst;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class HuAction extends GameAction {
    private final static HuAction instance = new HuAction();
    private HuAction(){}

    public static HuAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_GANG;
    }

    public enum CheckHuType{
        NULL,Hu,Ting
    }
}
