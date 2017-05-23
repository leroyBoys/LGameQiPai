package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.status.StepGameStatusData;
import com.module.net.NetGame;

import java.util.ArrayList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class HuAction extends GameOperateAction {
    private final static HuAction instance = new HuAction();
    private HuAction(){}

    protected static HuAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_HU;
    }

    @Override
    public boolean checkRight(NetGame.NetOprateData netOprateData,StepGameStatusData gameStatusData) {
        return true;
    }

    public void check(MjChairInfo chairInfo, int card, Object parems){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugins == null){
            return;
        }

        final CheckHuType checkHuType = (CheckHuType) parems;

        for(int i= 0;i<optPlugins.size();i++){
            if(((IPluginCheckCanExecuteAction)optPlugins.get(i)).checkExecute(chairInfo,card,parems)){
                if(checkHuType == CheckHuType.Hu){
                    return;
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
