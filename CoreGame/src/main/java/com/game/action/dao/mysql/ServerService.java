package com.game.action.dao.mysql;

import com.game.action.dao.mysql.template.ServerTemplate;
import com.game.manager.ServerConnection;
import com.module.GameServer;
import com.module.ServerGroup;
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
        List<ServerConnection> ret = this.sqlPool.ExecuteQuery(new ServerConnection(), ServerTemplate.GET_SERVER_BY_GROUP,group);
        return ret;
    }

    public GameServer getServerById(int gameId){
        GameServer server = this.sqlPool.ExecuteQueryOne(new GameServer(), ServerTemplate.GET_SERVER_BY_ID,gameId);
        if(server == null){
            return null;
        }

        return server;
    }

    public ServerGroup getServerGroup(int groupNum){
        ServerGroup serverGroup = this.sqlPool.ExecuteQueryOne(new ServerGroup(), ServerTemplate.GET_SERVERGROUP,groupNum);
        if(serverGroup == null){
            return null;
        }
        return serverGroup;
    }
}
