package com.game.core.dao.redis;

import com.redis.RedisConnectionManager;

/**
 * Created by Administrator on 2017/4/15.
 */
public class RedisService {
    protected RedisConnectionManager redisConnectionManager;

    public RedisService(RedisConnectionManager redisConnectionManager){
        this.redisConnectionManager = redisConnectionManager;
    }
}
