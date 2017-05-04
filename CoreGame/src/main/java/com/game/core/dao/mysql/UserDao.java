package com.game.core.dao.mysql;

import com.mysql.impl.SqlPool;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/4.
 */
public class UserDao {
    private SqlPool userPool;
    private SqlPool gamePool;
    public UserDao(SqlPool userPool,SqlPool gamePool){
        this.userPool = userPool;
        this.gamePool = gamePool;
    }
}
