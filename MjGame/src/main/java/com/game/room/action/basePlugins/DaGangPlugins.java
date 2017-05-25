package com.game.room.action.basePlugins;

import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class DaGangPlugins<T extends MjTable> extends AbstractActionPlugin<T>{

    @Override
    public DaGangPlugins createNew() {
        return new DaGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response,int roleId, StepGameStatusData stepGameStatusData) {
        return false;
    }
}
