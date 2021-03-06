package com.game.room.action.basePlugins;

import com.game.core.constant.GameConst;
import com.game.core.room.calculator.PayDetail;
import com.game.room.MjStepHistory;
import com.game.room.MjTable;
import com.game.room.calculator.MjCalculator;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class QiangGangHuPlugins<T extends MjTable> extends AbstractActionPlugin<T>{
    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        MjStepHistory mjStepHistory = (com.game.room.MjStepHistory) table.getStepHistoryManager();
        int size = table.getStepHistoryManager().getSize()-2;

        if(size < 0 || mjStepHistory.getLastStep(size).getAction().getActionType() != GameConst.MJ.ACTION_TYPE_GANG ){
            return false;
        }

        PayDetail pay = ((MjCalculator)table.getCalculator()).getLastBuGang();
        pay.setValid(false);
        payment(table,stepGameStatusData);

        return true;
    }
}
