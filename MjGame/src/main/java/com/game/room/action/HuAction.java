package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.room.action.basePlugins.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.ArrayList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class HuAction extends GameOperateAction {
    private final static HuAction instance = new HuAction();
    private HuAction(){}

    public static HuAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_HU;
    }

    @Override
    protected void doAction(MjTable table, Response response, int roleId, StepGameStatusData stepStatusData,NetGame.NetOprateData netOprateData){
        NetGame.NetOprateData.Builder retOperaData = NetGame.NetOprateData.newBuilder();
        retOperaData.setOtype(this.getActionType());
        retOperaData.setUid(roleId);

        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            IOptPlugin optPlugin = optPlugins.get(i);
            if(!optPlugin.doOperation(table,response,roleId,stepStatusData)){
                continue;
            }
            retOperaData.addDlist(optPlugin.getPlugin().getSubType());
        }

        table.addMsgQueueAll(retOperaData.build(),response==null?0:response.getSeq());

    }

    @Override
    public void check(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card, Object parems){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugins == null){
            return;
        }

        final CheckHuType checkHuType = (CheckHuType) parems;

        for(int i= 0;i<optPlugins.size();i++){
            if(optPlugins.get(i) instanceof IPluginCheckCanExecuteAction){

                if(((IPluginCheckCanExecuteAction)optPlugins.get(i)).checkExecute(stepGameStatusData,chairInfo,card,parems)){
                    if(checkHuType == CheckHuType.Hu){
                        return;
                    }
                }
            }

        }
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.HU_TING;
    }

    public enum CheckHuType{
        NULL,Hu,Ting
    }
}
