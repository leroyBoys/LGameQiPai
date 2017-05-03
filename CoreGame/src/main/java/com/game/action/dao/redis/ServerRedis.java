package com.game.action.dao.redis;

import com.redis.RedisConnectionManager;

/**
 * Created by Administrator on 2017/4/15.
 */
public class ServerRedis{
    protected RedisConnectionManager redisConnectionManager;

    public ServerRedis(RedisConnectionManager redisConnectionManager){
        this.redisConnectionManager = redisConnectionManager;
    }


}
