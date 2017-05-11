package com.game.room.action;

import com.game.socket.module.UserVistor;
import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.lsocket.message.Response;
import com.game.core.action.BaseAction;
import com.game.room.MjTable;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class YaPaoAction extends BaseAction<MjTable> {
    @Override
    public boolean isChangeToNextStatus(MjTable table) {
        return false;
    }

    @Override
    public void initAction(MjTable table) {
     //   NetCommon.NetResponse
    }

    @Override
    public void doAction(MjTable table, Response response, UserVistor visitor, NetGame.NetOprateData netOprateData) {
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        optPlugin.doOperation(table,response,netOprateData);
        //发送数据
    }
}
