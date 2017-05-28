package com.game.core.config;

import com.lgame.util.load.properties.Properties;
import com.lsocket.config.AutoConfig;

/**
 * Created by Administrator on 2017/5/28.
 */
public class SwithConfig extends AutoConfig {
    @Properties(defaultValue = "0:0", fileName = "server", keyName = "server.switch")
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private static final SwithConfig config = new SwithConfig();
    public static SwithConfig getInstance(){
        return config;
    }
}
