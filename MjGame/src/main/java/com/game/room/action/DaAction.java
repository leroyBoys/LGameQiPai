package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.status.StepGameStatusData;
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

    protected void doAction(MjTable table, Response response, int roleId, StepGameStatusData stepStatusData){
        NetGame.NetOprateData.Builder retOperaData = NetGame.NetOprateData.newBuilder();
        retOperaData.setOtype(this.getActionType());
        retOperaData.setUid(roleId);
        retOperaData.setDval(stepStatusData.getCards().get(0));
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(table.getGameId(),this.getActionType());
        optPlugin.doOperation(table,response,roleId,stepStatusData);

        table.addMsgQueueAll(retOperaData.build(),response==null?0:response.getSeq());
    }

    public void check(MjChairInfo chairInfo, int card, Object parems){
        IOptPlugin optPlugin = TablePluginManager.getInstance().getOneOptPlugin(chairInfo.getTableVo().getGameId(),this.getActionType());
        if(optPlugin  == null){
            return;
        }

        ((IPluginCheckCanExecuteAction)optPlugin).checkExecute(chairInfo,card,parems);
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
