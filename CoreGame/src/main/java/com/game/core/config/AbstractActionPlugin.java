package com.game.core.config;

import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * 游戏中行为插件（吃碰杠胡）
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public abstract class AbstractActionPlugin<A extends BaseTableVo> implements IOptPlugin<A> {

    @Override
    public IOptPlugin createNew() {
        return null;
    }

    @Override
    public void setPluginId(int pluginId) {
    }

    @Override
    public Object doOperation(A table, Response response, NetGame.NetOprateData oprateData) {
        return null;
    }
}
