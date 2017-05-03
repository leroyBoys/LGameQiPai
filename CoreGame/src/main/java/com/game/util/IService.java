package com.game.util;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public interface IService {
    void init(Object var1);

    void destroy(Object var1);

    void handleMessage(Object var1);

    String getName();

    void setName(String var1);
}
