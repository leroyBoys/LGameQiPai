package com.game.room.action.basePlugins;

import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;
import com.game.room.action.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class GangPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public GangPlugins createNew() {
        return new GangPlugins();
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.GANG;
    }

    @Override
    public Object doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        return super.doOperation(table, response, roleId, stepGameStatusData);
    }
}
