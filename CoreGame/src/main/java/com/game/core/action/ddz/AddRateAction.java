package com.game.core.action.ddz;

import com.game.core.UserVistor;
import com.game.core.action.BaseAction;
import com.game.core.room.ddz.DDzTable;
import com.lsocket.message.Response;
import com.module.net.NetCommon;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class AddRateAction extends BaseAction<DDzTable> {
    @Override
    public boolean isChangeToNextStatus(DDzTable table) {
        return false;
    }

    @Override
    public void doAction(DDzTable table, Response response, UserVistor visitor, NetCommon.NetOprateData netOprateData) {

    }
}
