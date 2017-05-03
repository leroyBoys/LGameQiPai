package com.game.config;

import com.lsocket.config.SocketConfig;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class SocketConfigRemote extends SocketConfig {
    public int schedulerThreadPoolSize = 1;


    public int getSchedulerThreadPoolSize() {
        return schedulerThreadPoolSize;
    }
}
