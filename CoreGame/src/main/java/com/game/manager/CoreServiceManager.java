package com.game.manager;

import com.game.admin.AdminCmdFilter;
import com.game.core.TableFactory;
import com.game.core.TableManager;
import com.game.core.chat.ChatChannelManager;
import com.game.core.config.GameSwitchManager;
import com.game.core.config.TablePluginManager;
import com.game.core.room.BaseTableVo;
import com.game.util.TaskScheduler;
import com.lgame.util.EmptyMontior;
import com.lgame.util.MethodCacheMontior;
import com.lgame.util.StatisticsMonitor;
import com.logger.type.LogType;
import com.lsocket.core.ICommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/14.
 */
public class CoreServiceManager extends ICommon {
    protected Logger playLog = LoggerFactory.getLogger(LogType.Play.getLogName());

    private final static CoreServiceManager obj = new CoreServiceManager();
    private CoreServiceManager(){}
    public static CoreServiceManager getIntance(){
        return obj;
    }
    public StatisticsMonitor monitor;

    private TaskScheduler taskScheduler;
    private ServiceThreadPool tableThreadPool;

    @Override
    protected void initService() {
        monitor = new MethodCacheMontior();
        //monitor = new EmptyMontior();
        TablePluginManager.getInstance().refresh("RoomSetting.xls","PluginGen.xls");
        TableFactory.getInstance().initGoodNum("");
        this.taskScheduler = new TaskScheduler(1);
        GameSwitchManager.getInstance().load(null);

        tableThreadPool = new ServiceThreads(4,10);
    }

    public ServiceThreadPool getTableThreadPool() {
        return tableThreadPool;
    }

    @Override
    protected void check() {
        /*taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for(BaseTableVo tableVo:TableManager.getInstance().getTableMap().values()){
                    if(tableVo.getChairs().length == tableVo.getCurChirCount()){
                        TableManager.getInstance().trigger(tableVo.getId());
                        continue;
                    }

                    AdminCmdFilter.getInstance().addRobot(4,tableVo);
                }


            }
        },10,2, TimeUnit.SECONDS);*/
    }
}
