package com.game.room.action;

import com.game.core.action.IdleAction;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MjIdleAction extends IdleAction<MjTable> {
    @Override
    public void doAction(MjTable table, Response response, int roleId, NetGame.NetOprateData netOprateData) {
        if(table.getCurRount() > table.getAttributeValue(BaseTableVo.AttributeKey.AllRount,0)){
            return;
        }

        super.doAction(table, response, roleId, netOprateData);
    }
}
