package com.game.action.dao.mysql;

import com.game.action.dao.mysql.template.ServerTemplate;
import com.game.manager.ServerConnection;
import com.mysql.SqlDataSource;
import com.mysql.impl.SqlPool;

import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */
public class ServerService {
    private SqlPool sqlPool;

    public ServerService(SqlPool sqlPool){
        this.sqlPool = sqlPool;
    }

    public List<ServerConnection> getServerByGroup(int group){
        List<ServerConnection> ret = this.sqlPool.ExecuteQuery(new ServerConnection(),ServerTemplate.GET_SERVER_BY_GROUP,group);
        return ret;
    }

    public ServerConnection getServerById(int gameId){
        List<ServerConnection> servers = this.sqlPool.ExecuteQuery(new ServerConnection(),ServerTemplate.GET_SERVER_BY_ID,gameId);
        if(servers == null || servers.isEmpty()){
            return null;
        }

        return servers.get(0);
    }
}
