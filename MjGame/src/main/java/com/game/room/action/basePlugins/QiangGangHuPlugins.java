package com.game.room.action.basePlugins;

import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class QiangGangHuPlugins<T extends MjTable> extends AbstractActionPlugin<T>{
    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {

        table.getStepHistoryManager().getLastStep(-2);


        return true;
    }
}
