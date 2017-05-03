package com.game.core.action.mj;

import com.game.core.UserVistor;
import com.game.core.action.BaseAction;
import com.game.core.room.mj.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetCommon;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DaAction extends BaseAction<MjTable> {
    @Override
    public boolean isChangeToNextStatus(MjTable table) {
        return false;
    }

    @Override
    public void doAction(MjTable table, Response response, UserVistor visitor, NetCommon.NetOprateData netOprateData) {

    }

    @Override
    public void overAction(MjTable table) {

    }
}
