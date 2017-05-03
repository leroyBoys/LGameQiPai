package com.game.core.action.mj;

import com.game.core.UserVistor;
import com.game.core.action.BaseAction;
import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.lsocket.message.Response;
import com.module.net.NetCommon;
import com.game.core.room.mj.MjTable;
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
    public void doAction(MjTable table, Response response, UserVistor visitor, NetCommon.NetOprateData netOprateData) {
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        optPlugin.doOperation(table,response,netOprateData);
        //发送数据
    }
}
