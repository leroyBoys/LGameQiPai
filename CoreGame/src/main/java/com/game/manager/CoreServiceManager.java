package com.game.manager;

import com.game.core.TableFactory;
import com.game.core.config.TablePluginManager;
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
    private ServiceThreadPool tableThreadPool = new ServiceThreads();

    @Override
    protected void initService() {
        monitor = new MethodCacheMontior();
        //monitor = new EmptyMontior();
        TablePluginManager.getInstance().refresh("RoomSetting.xls","PluginGen.xls");
        TableFactory.getInstance().initGoodNum("");
        this.taskScheduler = new TaskScheduler(1);
    }

    public ServiceThreadPool getTableThreadPool() {
        return tableThreadPool;
    }

    @Override
    protected void check() {

    }
}
