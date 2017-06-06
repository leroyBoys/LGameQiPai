package com.game.manager;

import com.game.core.dao.mysql.ServerService;
import com.game.core.dao.mysql.UserDao;
import com.game.core.dao.redis.GameRedis;
import com.game.core.dao.redis.UserRedis;
import com.game.core.service.UserService;
import com.game.core.service.impl.UserServiceImpl;
import com.lgame.util.comm.StringTool;
import com.lgame.util.file.PropertiesTool;
import com.lgame.util.json.JsonUtil;
import com.logger.log.SystemLogger;
import com.lsocket.config.SocketConfig;
import com.lsocket.core.ICommon;
import com.lsocket.module.IP;
import com.lsocket.util.SocketConstant;
import com.module.GameServer;
import com.module.ServerGroup;
import com.mysql.impl.SqlPool;
import com.redis.impl.RedisConnectionManager;

import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/14.
 */
public class DBServiceManager extends ICommon {
    private static DBServiceManager dbServiceManager = null;
    private DBServiceManager(){
    }

    public synchronized static DBServiceManager getInstance(){
        if(dbServiceManager != null){
            return dbServiceManager;
        }
        dbServiceManager = new DBServiceManager();
        return dbServiceManager;
    }

    private SqlPool commUserPool;//用户中心数据连接池
    private SqlPool commGamePool;//游戏数据连接池
    private GameServer gameServer;
    private ServerGroup serverGroup;
    private ServerService serverService;

    private UserRedis userRedis;
    private GameRedis gameRedis;
    private UserService userService;
  //  private UserDao userDao;

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

    private void loadConfig(){
        SocketConfig socketConfig = SocketConfig.getInstance();
        SqlPool.DataSourceType sourceType = SqlPool.DataSourceType.valueOf(socketConfig.getDbType());
        if(sourceType == null){
            throw new RuntimeException(socketConfig.getDbType()+" can not find in DataSourceType");
        }
        Properties dbProper = getProperties(sourceType);
        commUserPool = new SqlPool(sourceType,dbProper);

        serverService = new ServerService(commUserPool);
        gameServer = serverService.getServerById(socketConfig.getServerId());
        if(gameServer == null){
            throw new RuntimeException(socketConfig.getServerId()+" cant find from db");
        }else {
            SystemLogger.info(this.getClass(),"localhost:"+gameServer.getIp()+" serverType:"+gameServer.getServerType());
        }

       /* if(gameServer.getServerType() != GameServer.ServerType.gate){
            throw new RuntimeException(socketConfig.getServerId()+" serverType:"+gameServer.getServerType());
        }*/
        serverGroup  = serverService.getServerGroup(gameServer.getGroupNum());
        if(serverGroup == null){
            throw new RuntimeException("can not find goupNum:"+gameServer.getGroupNum() + " in serverGroup db");
        }

        dbProper = resetProper(dbProper);
        commGamePool = new SqlPool(sourceType,dbProper);

        SocketConstant.init(new IP(gameServer.getIp(),gameServer.getPort()),socketConfig.getMaxSocketLength(),socketConfig.getMaxQuqueVistor(),socketConfig.getSameIpMaxConnections());
    }

    protected void initService(){
        loadConfig();

        Properties redisProperties = PropertiesTool.loadProperty("redis.properties");

        RedisConnectionManager redisUserConnectionManager = new RedisConnectionManager(redisProperties);
        userRedis = new UserRedis(redisUserConnectionManager);
        this.userService = new UserServiceImpl(new UserDao(commUserPool,commGamePool),userRedis);

        Properties masterProperties = new Properties(redisProperties);
        masterProperties.setProperty("url",serverGroup.getRedisUrl()+"/"+(StringTool.isEmpty(serverGroup.getRedisPwd())?"":serverGroup.getRedisPwd()));
        RedisConnectionManager gameRedisConnectionManager = new RedisConnectionManager(masterProperties);
        gameRedis = new GameRedis(gameRedisConnectionManager);
    }

    @Override
    protected void check() {
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

    public UserService getUserService() {
        return userService;
    }

    public UserRedis getUserRedis() {
        return userRedis;
    }

    public GameRedis getGameRedis() {
        return gameRedis;
    }
}
