package com.game.manager;

import com.game.config.SocketConfigRemote;
import com.game.util.TaskScheduler;
import com.lgame.util.json.JsonTool;
import com.lsocket.core.ICommon;

import java.net.URL;

/**
 * Created by Administrator on 2017/4/14.
 */
public class CoreServiceManager extends ICommon {
    private final static CoreServiceManager obj = new CoreServiceManager();
    private CoreServiceManager(){}
    public static CoreServiceManager getIntance(){
        return obj;
    }

    private TaskScheduler taskScheduler;

    @Override
    protected void initService() {
        this.taskScheduler = new TaskScheduler(1);
    }

    @Override
    protected void check() {

    }
}
