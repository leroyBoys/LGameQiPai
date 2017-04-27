package com.game.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class ServerManager implements Runnable {
    private final static ServerManager serverManager = new ServerManager();
    private volatile boolean isRun = false;
    private Map<Integer,ServerConnection> serverPool = new HashMap<>();

    private ServerManager(){}
    public static ServerManager getIntance(){
        return serverManager;
    }

    public ServerConnection getServerConnection(int serverId){
        return serverPool.get(serverId);
    }

    public void init(List<ServerConnection> servers){
        serverPool.clear();
        for(ServerConnection serverConnection:servers){
            serverPool.put(serverConnection.getId(),serverConnection);
            serverConnection.check(0);
        }
    }


    public void run() {
        if(isRun){
            return;
        }
        isRun = true;
        try{
            tick();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            isRun = false;
        }
    }

    private void tick(){

    }
}
