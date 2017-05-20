package com.game.room.action;

import com.game.core.constant.GameConst;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class ChiAction extends GameAction {
    private final static ChiAction instance = new ChiAction();
    private ChiAction(){}

    protected static ChiAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_CHI;
    }

    @Override
    public boolean checkRight(NetGame.NetOprateData netOprateData,StepGameStatusData gameStatusData) {
        return gameStatusData.isRight(netOprateData.getDval(),netOprateData.getDlistList());
    }
}
