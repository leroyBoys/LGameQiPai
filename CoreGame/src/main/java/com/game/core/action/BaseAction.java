package com.game.core.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseTableVo;
import com.logger.type.LogType;
import com.lsocket.message.Response;
import com.module.net.NetGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public abstract class BaseAction<T extends BaseTableVo> {
    protected Logger playLog = LoggerFactory.getLogger(LogType.Play.getLogName());
    public int getActionType(){
        return 0;
    }

    public void doAction(T table, Response response, int roleId, NetGame.NetOprateData netOprateData){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            optPlugins.get(i).doOperation(table,response,roleId,netOprateData);
        }
    }

   /* public void systemDoAction(T table,int roleId, Object paramter){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            optPlugins.get(i).doOperation(table,null,roleId,paramter);
        }
    }
*/
    /**
     * 切换状态时候初始化
     * @param table
     */
    public void initAction(T table){
        NetGame.NetOprateData.Builder netOperate = table.getStatusData().getCanDoDatas(table,0);
        if(netOperate != null){
            NetGame.NetKvData.Builder kvData = NetGame.NetKvData.newBuilder();
            kvData.setK(this.getActionType());
            netOperate.addKvDatas(kvData);
            table.addMsgQueueAll(netOperate.build(),0);

            table.flushMsgQueue();
        }

    }

    /**
     * 切换状态之前对当前状态的收尾处理
     * @param table
     */
    public void overAction(T table){}

    /**
     *  守护线程
     * @param table
     */
    public void tick(T table){}
}
