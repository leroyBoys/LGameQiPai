package com.game.core.dao.redis;


import com.game.core.dao.redis.callback.GameRoleCallBack;
import com.game.socket.module.GameRole;
import com.lgame.util.comm.StringTool;
import com.redis.impl.RedisConnectionManager;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/15.
 */
public class GameRedis {
    protected final RedisConnectionManager redisConnectionManager;

    public GameRedis(RedisConnectionManager redisConnectionManager){
        this.redisConnectionManager = redisConnectionManager;
    }

    public RedisConnectionManager getRedisConnectionManager(){
        return redisConnectionManager;
    }

    public GameRole getGameRole(int roleId){
        Map<String,String> rolesMap = redisConnectionManager.getRandomSlave().hgetall(String.valueOf(roleId));
        if(rolesMap == null || rolesMap.isEmpty()){
            return GameRoleCallBack.getInstance().callBack(roleId);
        }

        String roomId = rolesMap.get("roomId");
        if(StringTool.isEmpty(roomId)){
            roomId = "0";
        }

        String card = rolesMap.get("card");
        if(StringTool.isEmpty(roomId)){
            card = "0";
        }
        return new GameRole(roleId,Integer.valueOf(roomId),Integer.valueOf(card));
    }

    public void setCard(int roleId, int card) {
        redisConnectionManager.getRandomSlave().hset(String.valueOf(roleId),"card",String.valueOf(card));
    }

    public void setRoomId(int roleId, int roomId) {
        redisConnectionManager.getRandomSlave().hset(String.valueOf(roleId),"roomId",String.valueOf(roomId));
    }
}
