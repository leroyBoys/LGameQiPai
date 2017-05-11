package com.game.room.action;

import com.game.core.action.BaseAction;
import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.room.MjTable;
import com.game.socket.module.UserVistor;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class FaPaiAction extends BaseAction<MjTable> {

    @Override
    public void initAction(MjTable table) {
        doAction(table,null,null,null);
    }

    @Override
    public void doAction(MjTable table, Response response, UserVistor visitor, NetGame.NetOprateData netOprateData) {
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        optPlugin.doOperation(table,response,netOprateData);

        //发消息


    }

    @Override
    public void overAction(MjTable table) {
    }

    @Override
    public boolean isChangeToNextStatus(MjTable table) {
        return false;
    }
}
