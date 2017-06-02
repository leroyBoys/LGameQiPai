package com.game.room.action.plugins;

import com.game.room.MjTable;
import com.game.room.action.basePlugins.AbstractActionPlugin;
import com.game.room.action.basePlugins.PingHuPlugins;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/12.
 */
public class XXHuangZhuangPlugins extends AbstractActionPlugin<MjTable> {

    @Override
    public PingHuPlugins createNew() {
        return new PingHuPlugins();
    }

    @Override
    public boolean doOperation(MjTable table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        return false;
    }

}
