package com.game.core.dao.redis;


import com.google.protobuf.InvalidProtocolBufferException;
import com.lgame.util.comm.FormatDataTool;
import com.module.net.DB;
import com.redis.impl.RedisConnectionManager;

/**
 * Created by Administrator on 2017/4/15.
 */
public class UserRedis {
    protected RedisConnectionManager redisConnectionManager;

    public UserRedis(RedisConnectionManager redisConnectionManager){
        this.redisConnectionManager = redisConnectionManager;
    }

    public DB.UK getUserKey(int uid){
        byte[] key = redisConnectionManager.getRandomSlave().get(FormatDataTool.intToByteArray(uid));
        if(key == null || key.length == 0){
            return null;
        }
        try {
            return DB.UK.parseFrom(key);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setUserKey(int uid,String ipPort,String key){
        DB.UK.Builder uk = DB.UK.newBuilder();
        uk.setUid(uid);
        uk.setIpPort(ipPort);
        uk.setKey(key);

        redisConnectionManager.getMaster().set(FormatDataTool.intToByteArray(uid),uk.build().toByteArray());
    }
}
