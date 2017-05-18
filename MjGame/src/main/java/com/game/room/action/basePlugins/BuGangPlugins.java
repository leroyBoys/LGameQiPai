package com.game.room.action.basePlugins;

import com.game.core.config.AbstractActionPlugin;
import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class BuGangPlugins<T extends MjTable> extends AbstractActionPlugin<T>  implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public BuGangPlugins createNew() {
        return new BuGangPlugins();
    }

    @Override
    public Object doOperation(T table, Response response, NetGame.NetOprateData oprateData) {
        return null;
    }
}
