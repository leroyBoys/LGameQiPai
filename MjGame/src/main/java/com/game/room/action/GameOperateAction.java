package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.room.action.basePlugins.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
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
public abstract class GameOperateAction<T extends MjTable> {
    public abstract int getActionType();

    protected void doAction(T table, Response response, int roleId, StepGameStatusData stepStatusData,NetGame.NetOprateData netOprateData){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            if(!optPlugins.get(i).doOperation(table,response,roleId,stepStatusData)){
                continue;
            }
        }
        table.addMsgQueueAll(netOprateData,response==null?0:response.getSeq());
    }

    public void check(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card,Object parems){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugins == null){
            return;
        }

        for(int i= 0;i<optPlugins.size();i++){
            if(optPlugins.get(i) instanceof IPluginCheckCanExecuteAction){
                if(((IPluginCheckCanExecuteAction)optPlugins.get(i)).checkExecute(stepGameStatusData,chairInfo,card,parems)){
                    return;
                }
            }
        }
    }

    public abstract int getWeight();
}
