package com.game.core.action;

import com.game.socket.module.UserVistor;
import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.ArrayList;


/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public abstract class BaseAction<T extends BaseTableVo> {
    @Deprecated
    public abstract boolean isChangeToNextStatus(T table);
    public int getActionType(){
        return 1;
    }

    public void doAction(T table, Response response, UserVistor visitor, NetGame.NetOprateData netOprateData){
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            optPlugins.get(i).doOperation(table,response,netOprateData);
        }
    }

    /**
     * 切换状态时候初始化
     * @param table
     */
    public void initAction(T table){}

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
