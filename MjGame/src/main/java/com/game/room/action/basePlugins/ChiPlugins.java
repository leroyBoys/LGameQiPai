package com.game.room.action.basePlugins;

import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class ChiPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public ChiPlugins createNew() {
        return new ChiPlugins();
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.CHI;
    }

}
