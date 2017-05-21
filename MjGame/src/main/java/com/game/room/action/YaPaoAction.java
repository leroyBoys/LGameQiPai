package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.socket.module.UserVistor;
import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.lsocket.message.Response;
import com.game.core.action.BaseAction;
import com.game.room.MjTable;
import com.module.net.NetGame;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class YaPaoAction extends BaseAction<MjTable> {
    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_YAPao;
    }

    @Override
    public void initAction(MjTable table) {
        NetGame.NetKvData.Builder kvData = NetGame.NetKvData.newBuilder();
        kvData.setK(GameConst.MJ.ACTION_TYPE_YAPao);
        List<NetGame.NetKvData> actions = new LinkedList<>();
        actions.add(kvData.build());
        table.addMsgQueueAll(table.getCanDoActionsResponse(actions),0);
    }

    @Override
    public void doAction(MjTable table, Response response, int roleId, NetGame.NetOprateData netOprateData) {
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        if(!optPlugin.doOperation(table,response,roleId,netOprateData)){
            return;
        }
        //发送数据

        NetGame.NetOprateData.Builder yaPao = table.getYaPaoNetOprateData();
        yaPao.setUid(roleId);
        yaPao.setDval(netOprateData.getDval());
        table.addMsgQueueAll(yaPao.build(),response.getSeq());
    }
}
