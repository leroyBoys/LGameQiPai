package com.game.manager;

import com.game.util.TaskScheduler;
import com.lgame.util.EmptyMontior;
import com.lgame.util.MethodCacheMontior;
import com.lgame.util.StatisticsMonitor;
import com.lsocket.core.ICommon;

/**
 * Created by Administrator on 2017/4/14.
 */
public class CoreServiceManager extends ICommon {
    private final static CoreServiceManager obj = new CoreServiceManager();
    private CoreServiceManager(){}
    public static CoreServiceManager getIntance(){
        return obj;
    }
    public StatisticsMonitor monitor;

    private TaskScheduler taskScheduler;

    @Override
    protected void initService() {
        monitor = new MethodCacheMontior();
        //monitor = new EmptyMontior();
        this.taskScheduler = new TaskScheduler(1);
    }

    @Override
    protected void check() {

    }
}
