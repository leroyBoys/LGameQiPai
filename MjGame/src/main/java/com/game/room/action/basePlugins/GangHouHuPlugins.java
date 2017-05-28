package com.game.room.action.basePlugins;

import com.game.core.constant.GameConst;
import com.game.room.MjStepHistory;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * 杠后胡 杠后点炮
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class GangHouHuPlugins<T extends MjTable> extends AbstractActionPlugin<T>{
    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {

        MjStepHistory mjStepHistory = (MjStepHistory) table.getStepHistoryManager();
        int size = table.getStepHistoryManager().getSize()-1;

        while (mjStepHistory.getLastStep(size).getAction().getActionType() == GameConst.MJ.ACTION_TYPE_HU){//胡
            size--;
        }

        if (size < 0 || mjStepHistory.getLastStep(size--).getAction().getActionType() != GameConst.MJ.ACTION_TYPE_DA){//打
            return false;
        }

        if (size ==0 || mjStepHistory.getLastStep(--size).getAction().getActionType() != GameConst.MJ.ACTION_TYPE_GANG){//杠
            return false;
        }

        payment(table,stepGameStatusData);
        return true;
    }
}
