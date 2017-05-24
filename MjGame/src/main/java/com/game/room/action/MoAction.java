package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MoAction extends GameOperateAction {
    private final static MoAction instance = new MoAction();
    private MoAction(){}

    public static MoAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_MOPAI;
    }

    @Override
    protected void doAction(MjTable table, Response response, int roleId,StepGameStatusData stepStatusData) {
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        optPlugin.doOperation(table,response,roleId,stepStatusData);

        NetGame.NetOprateData.Builder retOperaData = NetGame.NetOprateData.newBuilder();
        retOperaData.setOtype(this.getActionType());
        retOperaData.setUid(roleId);
        retOperaData.setDval(table.getChairByUid(roleId).getHandsContainer().getHandCards().size());
        NetGame.NetOprateData other = retOperaData.build();
        for(int i = 0;i<table.getChairs().length;i++){
            MjChairInfo chairInfo = table.getChairs()[i];
            if(chairInfo.getId() == roleId){
                continue;
            }
            table.addMsgQueue(chairInfo.getId(),other,0);
        }

        retOperaData.setDval(stepStatusData.getCards().get(0));
        table.addMsgQueue(roleId,retOperaData.build(),0);
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
