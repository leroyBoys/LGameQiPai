package com.game.room.action;

import com.game.core.constant.GameConst;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class PengAction extends GameOperateAction {
    private final static PengAction instance = new PengAction();
    private PengAction(){}

    protected static PengAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_PENG;
    }

    @Override
    public boolean checkRight(NetGame.NetOprateData netOprateData,StepGameStatusData gameStatusData) {
        return true;
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.PENG;
    }
}
