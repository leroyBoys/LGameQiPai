package com.game.core.config;

import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.List;

/**
 * 阶段插件（叫分，发牌等）
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class AbstractStagePlugin<A extends BaseTableVo> implements IOptPlugin<A,NetGame.NetOprateData > {
    private PluginGen pluginGen;

    @Override
    public IOptPlugin createNew() {
        return new AbstractStagePlugin();
    }

    @Override
    public void setPlugin(PluginGen plugin) {
        this.pluginGen = plugin;
    }

    @Override
    public PluginGen getPlugin() {
        return pluginGen;
    }

    @Override
    public boolean doOperation(A table, Response response,int roleId, NetGame.NetOprateData o) {
        return false;
    }

}
