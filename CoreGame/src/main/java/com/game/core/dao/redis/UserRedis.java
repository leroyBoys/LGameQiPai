package com.game.core.dao.redis;


import com.google.protobuf.InvalidProtocolBufferException;
import com.lgame.util.comm.FormatDataTool;
import com.lgame.util.json.JsonTool;
import com.lgame.util.json.JsonUtil;
import com.lgame.util.time.DateTimeTool;
import com.module.CreateRoleRewardData;
import com.module.db.RoleInfo;
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
        byte[] redisKey = FormatDataTool.intToByteArray(uid);
        redisConnectionManager.getMaster().expire(redisKey, DateTimeTool.M_ONE_DAY);
        redisConnectionManager.getMaster().set(redisKey,uk.build().toByteArray());
    }

    public RoleInfo getDefaultRoleInfo(int uid) {
        String key = "GameRole"+uid;
        String obj = redisConnectionManager.getRandomSlave().get(key);
        if(obj == null){
            return null;
        }

        redisConnectionManager.getRandomSlave().del(key);
        return (RoleInfo) JsonUtil.getBeanFromJson(obj,RoleInfo.class);
    }

    public CreateRoleRewardData getCreateRoleRewardData() {
        String key = "createRoleReward";
        String obj = redisConnectionManager.getRandomSlave().get(key);
        if(obj == null){
            redisConnectionManager.getRandomSlave().set(key,JsonUtil.getJsonFromBean(new CreateRoleRewardData()));
            return null;
        }

        return (CreateRoleRewardData) JsonUtil.getBeanFromJson(obj,CreateRoleRewardData.class);
    }

   /* public RedisConnectionManager getRedisConnectionManager() {
        return redisConnectionManager;
    }*/
}
