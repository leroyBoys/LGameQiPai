package com.game.room.action.basePlugins;

import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;
import com.game.room.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class MoPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public MoPlugins createNew() {
        return new MoPlugins();
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public Object doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        return super.doOperation(table, response, roleId, stepGameStatusData);
    }
}
