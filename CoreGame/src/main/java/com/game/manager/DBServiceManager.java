package com.game.manager;

import com.game.action.dao.mysql.ServerService;
import com.lgame.util.comm.StringTool;
import com.lgame.util.file.PropertiesTool;
import com.lsocket.core.ICommon;
import com.module.GameServer;
import com.module.ServerGroup;
import com.mysql.impl.SqlPool;

import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/14.
 */
public class DBServiceManager extends ICommon {
    private static DBServiceManager dbServiceManager = null;
    private Properties properties;
    private DBServiceManager(){
    }

    public synchronized static DBServiceManager getInstance(Properties properties){
        if(dbServiceManager != null){
            return dbServiceManager;
        }
        dbServiceManager = new DBServiceManager();
        dbServiceManager.properties = properties;
        return dbServiceManager;
    }

    private SqlPool commUserPool;//用户中心数据连接池
    private SqlPool commGamePool;//游戏数据连接池
    private GameServer gameServer;
    private ServerGroup serverGroup;
    private ServerService serverService;

    private Properties getProperties(SqlPool.DataSourceType sourceType){
        if(sourceType == SqlPool.DataSourceType.Druid){
            return PropertiesTool.loadProperty("druid_db.properties");
        }
        return PropertiesTool.loadProperty("hikari_db.properties");
    }

    private Properties resetProper(Properties dbProper) {
        dbProper.setProperty("username",serverGroup.getSqlUserName());
        dbProper.setProperty("password",serverGroup.getSqlPwd());
        if(StringTool.isNotNull(dbProper.getProperty("jdbcUrl"))){
            dbProper.setProperty("jdbcUrl",serverGroup.getSqlUrl());
        }else {
            dbProper.setProperty("url",serverGroup.getSqlUrl());
        }
        return dbProper;
    }

    private void loadConfig(Properties properties){
        SqlPool.DataSourceType sourceType = SqlPool.DataSourceType.valueOf(properties.getProperty("server.dbtype"));
        if(sourceType == null){
            throw new RuntimeException(properties.getProperty("server.dbtype")+" can not find in DataSourceType");
        }
        Properties dbProper = getProperties(sourceType);
        commUserPool = new SqlPool(sourceType,dbProper);

        serverService = new ServerService(commUserPool);
        gameServer = serverService.getServerById(Integer.valueOf(properties.getProperty("server.id")));
        if(gameServer == null){
            throw new RuntimeException(properties.getProperty("server.id")+" cant find from db");
        }

        if(gameServer.getServerType() != GameServer.ServerType.gate){
            throw new RuntimeException(properties.getProperty("server.id")+" serverType:"+gameServer.getServerType());
        }
        serverGroup  = serverService.getServerGroup(gameServer.getGroupNum());
        if(serverGroup == null){
            throw new RuntimeException("can not find goupNum:"+gameServer.getGroupNum() + " in serverGroup db");
        }

        dbProper = resetProper(dbProper);
        commGamePool = new SqlPool(sourceType,dbProper);
    }

    protected void initService(){
        loadConfig(properties);
        // RedisConnectionManager redisConnectionManager = new RedisConnectionManager(properties);
    }

    @Override
    protected void check() {
        List<ServerConnection> servers = serverService.getServerByGroup(serverGroup.getGroup());
        ServerManager.getIntance().init(servers);
    }


    public static DBServiceManager getDbServiceManager() {
        return dbServiceManager;
    }

    public SqlPool getCommUserPool() {
        return commUserPool;
    }

    public SqlPool getCommGamePool() {
        return commGamePool;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public ServerGroup getServerGroup() {
        return serverGroup;
    }

    public ServerService getServerService() {
        return serverService;
    }
}
