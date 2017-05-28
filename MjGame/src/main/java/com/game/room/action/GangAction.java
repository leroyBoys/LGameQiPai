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
public class GangAction extends PengAction {
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
    public void check(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card, Object parems){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugins == null){
            return;
        }

        for(int i= 0;i<optPlugins.size();i++){
            if(optPlugins.get(i) instanceof IPluginCheckCanExecuteAction){

                if(((IPluginCheckCanExecuteAction)optPlugins.get(i)).checkExecute(stepGameStatusData,chairInfo,card,this)){
                    if(card != 0){
                        return;
                    }
                }
            }
        }
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.GANG;
    }
}
