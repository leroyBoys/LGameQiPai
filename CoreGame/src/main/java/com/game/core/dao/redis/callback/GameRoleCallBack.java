package com.game.core.dao.redis.callback;

import com.game.core.dao.redis.RedisFromSqlCall;
import com.game.manager.DBServiceManager;
import com.game.manager.OnlineManager;
import com.game.socket.module.GameRole;
import com.game.socket.module.UserVistor;
import com.module.db.RoleInfo;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/10.
 */
public class GameRoleCallBack implements RedisFromSqlCall<GameRole,Integer> {
    private static final GameRoleCallBack instance = new GameRoleCallBack();
    private GameRoleCallBack(){}

    public static GameRoleCallBack getInstance(){
        return instance;
    }

    @Override
    public GameRole callBack(Integer roleId) {
        UserVistor vistor =  OnlineManager.getIntance().getUserById(roleId);
        if(vistor != null && vistor.getRoleInfo() != null){
            DBServiceManager.getInstance().getGameRedis().setCard(roleId,vistor.getRoleInfo().getCard());
            return new GameRole(roleId,0,vistor.getRoleInfo().getCard());
        }

        RoleInfo roleInfo = DBServiceManager.getInstance().getUserService().getRoleInfoById(roleId);
        if(roleInfo == null){
            return null;
        }

        DBServiceManager.getInstance().getGameRedis().setCard(roleId,roleInfo.getCard());
        return new GameRole(roleId,0,roleInfo.getCard());
    }
}
