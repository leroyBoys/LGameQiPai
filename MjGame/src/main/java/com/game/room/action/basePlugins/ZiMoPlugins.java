package com.game.room.action.basePlugins;

import com.game.core.constant.GameConst;
import com.game.room.MjStepHistory;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * 自摸胡
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class ZiMoPlugins<T extends MjTable> extends AbstractActionPlugin<T>{
    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        MjStepHistory mjStepHistory = (com.game.room.MjStepHistory) table.getStepHistoryManager();
        int size = mjStepHistory.getSize()-2;

        if(size < 0 || mjStepHistory.getLastStep(size).getAction().getActionType() != GameConst.MJ.ACTION_TYPE_MOPAI){
            return false;
        }

        pay(table,stepGameStatusData);
        return true;
    }

    protected void pay(T table, StepGameStatusData stepGameStatusData){
        payment(table,stepGameStatusData);
    }
}
