package com.game.core.config;

import com.game.core.room.BaseTableVo;
import com.logger.type.LogType;
import com.lsocket.message.Response;
import com.module.net.NetGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 阶段插件（叫分，发牌等）
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class AbstractStagePlugin<A extends BaseTableVo> implements IOptPlugin<A,NetGame.NetOprateData> {
    protected Logger playLog = LoggerFactory.getLogger(LogType.Play.getLogName());
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
