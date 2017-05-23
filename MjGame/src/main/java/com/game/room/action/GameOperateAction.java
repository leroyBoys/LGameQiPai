package com.game.room.action;

import com.game.core.action.BaseAction;
import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
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
public abstract class GameOperateAction extends BaseAction<MjTable> {

    @Override
    public void initAction(MjTable table) {
    }

    @Override
    public final void doAction(MjTable table, Response response, int roleId, NetGame.NetOprateData netOprateData){
        NetGame.NetOprateData.Builder retOperaData = NetGame.NetOprateData.newBuilder(netOprateData);
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            IOptPlugin optPlugin = optPlugins.get(i);
            if(!optPlugin.doOperation(table,response,roleId,netOprateData)){
                continue;
            }
            retOperaData.addDlist(optPlugin.getPlugin().getSubType());
        }

        table.addMsgQueueAll(retOperaData.build(),response==null?0:response.getSeq());
    }


    @Override
    public void overAction(MjTable table) {
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

    public abstract int getWeight();
}
