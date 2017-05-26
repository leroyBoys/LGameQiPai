package com.game.core.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.ArrayList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class GameOverAction<T extends BaseTableVo> extends BaseAction <T>  {
    private final static GameOverAction instance = new GameOverAction();
    private GameOverAction(){}

    public static GameOverAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.ACTION_TYPE_GAMEOVER;
    }


    @Override
    public void doAction(T table, Response response, int roleId, NetGame.NetOprateData netOprateData) {
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),this.getActionType());
        for(int i= 0;i<optPlugins.size();i++){
            optPlugins.get(i).doOperation(table,null,0,null);
        }
    }
}
