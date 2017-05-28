package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class ChiAction extends GameOperateAction {
    private final static ChiAction instance = new ChiAction();
    private ChiAction(){}

    public static ChiAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_CHI;
    }

    @Override
    protected void doAction(MjTable table, Response response, int roleId, StepGameStatusData stepStatusData,NetGame.NetOprateData netOprateData) {
        super.doAction(table, response, roleId, stepStatusData,netOprateData);
        table.getChairByUid(roleId).resetPassCard();
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.CHI;
    }
}
