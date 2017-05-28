package com.game.room.action.basePlugins;

import com.game.core.constant.GameConst;
import com.game.core.room.calculator.PayDetail;
import com.game.room.MjTable;
import com.game.room.calculator.MjCalculator;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * 明杠后自摸胡
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class GangShangHuaPlugins<T extends MjTable> extends AbstractActionPlugin<T>{
    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {

        int size = table.getStepHistoryManager().getSize()-1;//胡
        size--;//摸
        StepGameStatusData lastStep = (StepGameStatusData) table.getStepHistoryManager().getLastStep(size);
        if(stepGameStatusData.getUid() != lastStep.getUid() || lastStep.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_MOPAI){
            return false;
        }
        size--;//杠
        if(size < 0){
            return false;
        }
        lastStep = (StepGameStatusData) table.getStepHistoryManager().getLastStep(size);
        if(lastStep.getiOptPlugin().getPlugin().getSubType() != GameConst.MJ.ACTION_TYPE_GANG_MingGang){
            return false;
        }

        payment(table,stepGameStatusData);
        return true;
    }
}
