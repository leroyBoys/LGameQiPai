package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.manager.TimeCacheManager;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.game.util.ProbuffTool;
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
        MjChairInfo chairInfo = table.getChairByUid(roleId);
        table.setFocusIdex(chairInfo.getIdx());
        chairInfo.resetPassCard();
        table.setTimeOutTime(GameConst.Time.MJ_WAIT_SECONDS+ TimeCacheManager.getInstance().getCurTime());

        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        optPlugin.doOperation(table,response,roleId,stepStatusData);

        NetGame.NetOprateData.Builder turnData = ProbuffTool.getTurnData(roleId,0,table.getTimeOutRemain());

        NetGame.NetOprateData other = turnData.build();
        for(int i = 0;i<table.getChairs().length;i++){
            chairInfo = table.getChairs()[i];
            if(chairInfo.getId() == roleId){
                continue;
            }
            table.addMsgQueue(chairInfo.getId(),other,0);
        }

        turnData.setDval(stepStatusData.getCards().get(0));
        table.addMsgQueue(roleId,turnData.build(),0);
    }

    @Override
    public int getWeight() {
        return 0;
    }

    public void check(MjChairInfo chairInfo, int card, Object parems){
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugin  == null){
            return;
        }

        ((IPluginCheckCanExecuteAction)optPlugin).checkExecute(chairInfo,card,parems);
    }

}
