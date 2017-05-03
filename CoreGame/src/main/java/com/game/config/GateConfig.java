package com.game.config;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class GateConfig implements IConfigurator {
    private SocketConfigRemote socketConfig;
    private ServerSetting serverSetting;

    public void loadConfiguration() throws Exception {
        serverSetting = new ServerSetting();
    }

    public SocketConfigRemote getSocketConfig() {
        return socketConfig;
    }

    @Override
    public ServerSetting getServerSetting() {
        return serverSetting;
    }
}
