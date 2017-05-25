package com.game.room.action;

import com.game.core.action.BaseAction;
import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.GameOverType;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjChairInfo;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.core.ResponseCode;
import com.module.net.NetGame;

import java.util.ArrayList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GameAction extends BaseAction<MjTable> {

    @Override
    public void initAction(MjTable table) {
        SuperGameStatusData statusData = table.getStatusData();
        statusData.checkMo(table);
        this.doAction(table,null,table.getBankId(),null);
    }

    @Override
    public void doAction(MjTable table, Response response, int roleId, NetGame.NetOprateData netOprateData){
        SuperGameStatusData statusData = table.getStatusData();
        boolean isMatch = statusData.checkMatch(table,roleId,netOprateData,response);
        if(!isMatch){
            return;
        }

        if(statusData.getFirst().isAuto()){
           this.doAction(table,null,statusData.getFirst().getUid(),null);
            return;
        }

        if(table.getGameOverType() != GameOverType.NULL){
            table.sendSettlementMsg(roleId);
        }else {
            NetGame.NetOprateData.Builder netOprateDataBuild = table.getStatusData().getCanDoDatas(table,0);

            playLog.info("send candoactions to roleId:"+roleId+"   "+netOprateDataBuild.toString());
            if(netOprateData != null){
                NetGame.NetKvData.Builder kvData = NetGame.NetKvData.newBuilder();
                kvData.setK(table.getStatus().getAction().getActionType());
                netOprateDataBuild.addKvDatas(kvData);
                table.addMsgQueue(roleId,netOprateDataBuild.build(),0);//可操作集合
            }
        }
        table.flushMsgQueue();
    }

    @Override
    public void overAction(MjTable table) {
        table.addRound(); //局数加一
    }

    @Override
    public void tick(MjTable table){
        SuperGameStatusData statusData = table.getStatusData();
        if(statusData.isEmpty() || statusData.getFirst().isAuto()){
            return;
        }

        BaseChairInfo info = table.getChairByUid(statusData.getFirst().getUid());
        if(!info.isAuto() && !info.isRobot()){
            return;
        }

        NetGame.NetOprateData netOprateData = ((MjAutoCacheHandContainer)info.getHandsContainer().getAutoCacheHands()).getNetOprateData(info,statusData.getFirst());
        this.doAction(table,null,info.getId(),netOprateData);
    }
}
