package com.game.room.action;

import com.game.core.action.BaseAction;
import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.core.room.GameOverType;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.lgame.util.comm.KVData;
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
      //  table.getStepHistoryManager().getActionTypeSteps().add(new StepGameStatusData());

        SuperGameStatusData statusData = table.getStatusData();
        MjChairInfo chairInfo = table.getChairByUid(table.getBankId());
        statusData.checkGang(chairInfo,0);
        statusData.checkChi(chairInfo,0);
        statusData.checkPeng(chairInfo,0);
        statusData.checkHu(chairInfo,0);
        statusData.checkDa(chairInfo,0);
    }

    @Override
    public void doAction(MjTable table, Response response, int roleId, NetGame.NetOprateData netOprateData){
        if(netOprateData.getOtype() == GameConst.MJ.ACTION_TYPE_GUO){
            GuoAction.getInstance().doAction(table,response,roleId,netOprateData);
            return;
        }

        SuperGameStatusData statusData = table.getStatusData();
        StepGameStatusData firstStep = statusData.getFirstCanDoAction();
        if(firstStep.getAction().getActionType() != netOprateData.getOtype() || firstStep.getUid() != roleId){
            table.sendError(ResponseCode.Error.not_your_turn,roleId);
            return;
        }else if(!firstStep.getAction().checkRight(netOprateData,firstStep)){
            table.sendError(ResponseCode.Error.parmter_error,roleId);
            return;
        }

        table.addStep();
        table.getStepHistoryManager().add(firstStep);
        firstStep.getAction().doAction(table,response,roleId,netOprateData);

    }

    private void sendMsg(MjTable table){
        table.sendCanDoActionMsg(0);
    }

 /*   @Override
    public void systemDoAction(MjTable table,int roleId, Object paramter){
        table.getStepHistoryManager().add(this.getActionType(),roleId);

        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            optPlugins.get(i).doOperation(table,null,roleId,paramter);
        }
    }
    */

    @Override
    public void overAction(MjTable table) {
        table.addRound(); //局数加一
    }

    public boolean checkRight(NetGame.NetOprateData netOprateData,StepGameStatusData gameStatusData){
        return true;
    }

    public void check(MjChairInfo chairInfo, int card,Object parems){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugins == null){
            return;
        }

        for(int i= 0;i<optPlugins.size();i++){
           if(((IPluginCheckCanExecuteAction)optPlugins.get(i)).checkExecute(chairInfo,card,parems)){
               return;
           }
        }
    }
}
