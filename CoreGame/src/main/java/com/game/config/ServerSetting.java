package com.game.config;

import com.lgame.util.load.properties.Properties;
import com.lsocket.config.AutoConfig;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class ServerSetting extends AutoConfig {
    @Properties(defaultValue = "1", fileName = "server", keyName = "server.group")
    private int group;
    @Properties(defaultValue = "1", fileName = "server", keyName = "server.name")
    private String name;
    @Properties(defaultValue = "1", fileName = "server", keyName = "server.ip")
    private String ip;
    @Properties(defaultValue = "1", fileName = "server", keyName = "server.port")
    private int port;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
