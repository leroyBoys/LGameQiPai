package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseGameStateData;
import com.game.room.MjChairInfo;
import com.game.socket.module.UserVistor;
import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.lsocket.message.Response;
import com.game.core.action.BaseAction;
import com.game.room.MjTable;
import com.module.net.NetGame;

import java.util.Arrays;
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
        if(table.getBankId() == table.getLastBankUid()){
            table.getStatusData().addDoneUid(table.getBankId());
        }
        NetGame.NetOprateData netOperate = table.getStatusData().getCanDoDatas(table,0).build();
        for(int i = 0;i<table.getChairs().length;i++){
            if(table.getChairs()[i].getId() == table.getBankId()){
                continue;
            }
            table.addMsgQueue(table.getChairs()[i].getId(),netOperate,0);
        }
        table.flushMsgQueue();
    }

    @Override
    public void doAction(MjTable table, Response response, int roleId, NetGame.NetOprateData netOprateData) {
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        if(!optPlugin.doOperation(table,response,roleId,netOprateData)){
            return;
        }
        //发送数据
        NetGame.NetOprateData.Builder yaPao = table.getStatusData().getStatusDetail(table);
        yaPao.setUid(roleId);
        yaPao.setDval(netOprateData.getDval());
        table.addMsgQueueAll(yaPao.build(),response == null?0:response.getSeq());
    }

    @Override
    public void tick(MjTable table){
        NetGame.NetOprateData netOprateData = null;

        for(int i = 0;i<table.getChairs().length;i++){
            if(table.getChairs()[i] == null || (!table.getChairs()[i].isRobot() && !table.getChairs()[i].isAuto())){
                continue;
            }
            if(netOprateData == null){
                NetGame.NetOprateData.Builder netOprateData2 = NetGame.NetOprateData.newBuilder();
                netOprateData2.setUid(table.getChairs()[i].getId());
                netOprateData2.setDval(1);
                netOprateData = netOprateData2.build();
            }

            this.doAction(table,null,table.getChairs()[i].getId(),netOprateData);
        }
    }
}
