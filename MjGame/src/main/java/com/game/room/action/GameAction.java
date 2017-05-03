package com.game.room.action;

import com.game.socket.module.UserVistor;
import com.game.core.action.BaseAction;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetCommon;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GameAction extends BaseAction<MjTable> {
    @Override
    public boolean isChangeToNextStatus(MjTable table) {
        return false;
    }

    @Override
    public void initAction(MjTable table) {
        GameStatusData statusData = table.getStatusData();
        statusData.moPai(table,table.getBankId());
    }

    @Override
    public void doAction(MjTable table, Response response, UserVistor visitor, NetCommon.NetOprateData netOprateData) {
        GameStatusData statusData = (GameStatusData) table.getStatusData();
    }

    @Override
    public void overAction(MjTable table) {

    }
}
