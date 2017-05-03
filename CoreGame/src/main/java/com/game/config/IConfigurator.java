package com.game.config;


/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public interface IConfigurator {
    void loadConfiguration() throws Exception;
    public SocketConfigRemote getSocketConfig();
    public ServerSetting getServerSetting();
}
