package com.game.room.action;

import com.game.core.action.BaseAction;
import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.socket.module.UserVistor;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class FaPaiAction extends BaseAction<MjTable> {

    @Override
    public void initAction(MjTable table) {
        doAction(table,null,0,null);
    }

    @Override
    public int getActionType() {
        return GameConst.ACTION_TYPE_FAPAI;
    }

    @Override
    public void doAction(MjTable table, Response response, int roleId, NetGame.NetOprateData netOprateData) {
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        optPlugin.doOperation(table,response,0,netOprateData);
    }

    @Override
    public void overAction(MjTable table) {
        //发消息
        List<NetGame.NetKvData> handNumList = new LinkedList<>();

        for(int i = 0;i<table.getChairs().length;i++){
            MjChairInfo chairInfo = table.getChairs()[i];
            NetGame.NetKvData.Builder kvData = NetGame.NetKvData.newBuilder();
            kvData.setK(chairInfo.getId());
            kvData.setV(chairInfo.getHandsContainer().getHandCards().size());
            handNumList.add(kvData.build());

            playLog.info("   发牌:roleId:"+chairInfo.getId()+":size:"+chairInfo.getHandsContainer().getHandCards().size()+ Arrays.toString(chairInfo.getHandsContainer().getHandCards().toArray()));
        }


        for(int i = 0;i<table.getChairs().length;i++){
            MjChairInfo chairInfo = table.getChairs()[i];

            NetGame.NetOprateData.Builder hands = NetGame.NetOprateData.newBuilder();
            hands.setUid(chairInfo.getId());
            hands.addAllDlist(chairInfo.getHandsContainer().getHandCards());
            hands.addAllKvDatas(handNumList);

            table.addMsgQueue(chairInfo.getId(),hands.build(),0);
        }
    }

}
