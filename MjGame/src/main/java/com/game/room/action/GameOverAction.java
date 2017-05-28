package com.game.room.action;

import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GameOverAction extends GameOperateAction {
    private final static GameOverAction instance = new GameOverAction();
    private GameOverAction(){}

    public static GameOverAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return 0;
    }

    @Override
    protected void doAction(MjTable table, Response response, int roleId,StepGameStatusData stepStatusData,NetGame.NetOprateData netOprateData) {
        com.game.core.action.GameOverAction.getInstance().doAction(table,response,roleId,null);
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public void check(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card, Object parems){
    }

}
