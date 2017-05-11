package com.game.core.config;

import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * 阶段插件（叫分，发牌等）
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class AbstractStagePlugin<A extends BaseTableVo> implements IOptPlugin<A> {
    @Override
    public IOptPlugin createNew() {
        return new AbstractStagePlugin();
    }

    @Override
    public void setPluginId(int pluginId) {
    }

    @Override
    public Object doOperation(A table, Response response, NetGame.NetOprateData oprateData) {
        return null;
    }
}
