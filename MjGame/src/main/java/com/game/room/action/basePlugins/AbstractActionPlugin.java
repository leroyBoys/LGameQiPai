package com.game.room.action.basePlugins;

import com.game.core.config.IOptPlugin;
import com.game.core.room.BaseTableVo;
import com.game.room.action.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * 游戏中行为插件（吃碰杠胡）
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public abstract class AbstractActionPlugin<A extends BaseTableVo> implements IOptPlugin<A,StepGameStatusData> {

    @Override
    public IOptPlugin createNew() {
        return null;
    }

    @Override
    public void setPluginId(int pluginId) {
    }

    @Override
    public Object doOperation(A table, Response response,int roleId, StepGameStatusData stepGameStatusData) {
        return null;
    }
}
