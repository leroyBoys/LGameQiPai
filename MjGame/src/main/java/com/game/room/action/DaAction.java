package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.room.action.basePlugins.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.status.StepGameStatusData;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DaAction extends GameOperateAction{
    private final static DaAction intance = new DaAction();

    private DaAction(){}

    public static final DaAction getIntance(){
        return intance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_DA;
    }

    protected void doAction(MjTable table, Response response, int roleId, StepGameStatusData stepStatusData,NetGame.NetOprateData netOprateData){
        stepStatusData.setCards(netOprateData.getDlistList());
        super.doAction(table,response,roleId,stepStatusData,netOprateData);
    }

    @Override
    public void check(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card, Object parems){
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugin  == null){
            return;
        }

        ((IPluginCheckCanExecuteAction)optPlugin).checkExecute(stepGameStatusData,chairInfo,card,parems);
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
