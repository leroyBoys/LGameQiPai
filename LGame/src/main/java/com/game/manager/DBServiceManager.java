package com.game.manager;

import com.game.action.dao.mysql.ServerService;
import com.module.GameServer;
import com.module.ServerGroup;
import com.mysql.impl.SqlPool;
import com.redis.RedisConnectionManager;

import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/14.
 */
public class DBServiceManager {
    private final static DBServiceManager dbServiceManager = new DBServiceManager();
    private DBServiceManager(){
    }

    public static DBServiceManager getInstance(){
        return dbServiceManager;
    }

    public void init(Properties properties){
        SqlPool sqlPool = new SqlPool();
        ServerService serverService = new ServerService(sqlPool);
        ServerConnection serverConnection = serverService.getServerById(Integer.valueOf(properties.getProperty("server.id")));
        if(serverConnection == null){
            throw new RuntimeException(properties.getProperty("server.id")+" cant find from db");
        }

        if(serverConnection.getServerType() != GameServer.ServerType.gate){
            throw new RuntimeException(properties.getProperty("server.id")+" serverType:"+serverConnection.getServerType());
        }
        ServerGroup serverGroup  = null;

        List<ServerConnection> servers = serverService.getServerByGroup((Integer) properties.get("server.group"));
        ServerManager.getIntance().init(servers);

       // RedisConnectionManager redisConnectionManager = new RedisConnectionManager(properties);
    }
}
