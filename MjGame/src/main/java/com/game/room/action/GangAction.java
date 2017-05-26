package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.ArrayList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GangAction extends GameOperateAction {
    private final static GangAction instance = new GangAction();
    private GangAction(){}

    public static GangAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_GANG;
    }

    @Override
    public void check(MjChairInfo chairInfo, int card, Object parems){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getICheckOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugins == null){
            return;
        }

        for(int i= 0;i<optPlugins.size();i++){
            if(((IPluginCheckCanExecuteAction)optPlugins.get(i)).checkExecute(chairInfo,card,this)){
                if(card != 0){
                    return;
                }
            }
        }
    }

    @Override
    protected void doAction(MjTable table, Response response, int roleId, StepGameStatusData stepStatusData) {
        super.doAction(table, response, roleId, stepStatusData);
        table.getChairByUid(roleId).resetPassCard();
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.GANG;
    }
}
