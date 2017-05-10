package com.game.core.dao.redis;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/10.
 */
public interface RedisFromSqlCall<T,P> {
    public T callBack(P p);
}
