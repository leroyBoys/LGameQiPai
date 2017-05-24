package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GuoAction extends GameOperateAction {
    private final static GuoAction instance = new GuoAction();
    private GuoAction(){}

    public static GuoAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_GUO;
    }

    public void check(MjChairInfo chairInfo, int card, Object parems){
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    protected void doAction(MjTable table, Response response, int roleId,StepGameStatusData stepStatusData){
     //   table.addMsgQueue(roleId,netOprateData,response==null?0:response.getSeq());
    }
}
