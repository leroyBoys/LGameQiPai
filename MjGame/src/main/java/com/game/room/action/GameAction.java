package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.room.MjChairInfo;
import com.game.room.SuperGameStatusData;
import com.game.socket.module.UserVistor;
import com.game.core.action.BaseAction;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.ArrayList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GameAction extends BaseAction<MjTable> {
    @Override
    public boolean isChangeToNextStatus(MjTable table) {
        return false;
    }

    @Override
    public void initAction(MjTable table) {
        SuperGameStatusData statusData = table.getStatusData();
        MjChairInfo chairInfo = table.getChairByUid(table.getBankId());
        statusData.checkGang(chairInfo,0);
        statusData.checkChi(chairInfo,0);
        statusData.checkPeng(chairInfo,0);
        statusData.checkHu(chairInfo,0);
    }

    @Override
    public void doAction(MjTable table, Response response, UserVistor visitor, NetGame.NetOprateData netOprateData){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            optPlugins.get(i).doOperation(table,response,visitor.getRoleId(),netOprateData);
        }
    }

    @Override
    public void overAction(MjTable table) {
        table.addRound(); //局数加一
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
