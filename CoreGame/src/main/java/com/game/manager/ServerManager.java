package com.game.manager;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class ServerManager implements Runnable {
    private final static ServerManager serverManager = new ServerManager();
    private volatile boolean isRun = false;
    private Map<Integer,ServerConnection> serverPool = new HashMap<>();
    private LinkedList<ServerConnection> canUseServer = new LinkedList<>();
    private Set<ServerConnection> canUseServerSet = new HashSet<>();

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

    public ServerConnection getCanUseServer(){
        while (!canUseServer.isEmpty()){
            if(canUseServer.getFirst().getRunStatus() == ServerConnection.ServerStatus.notFull){
                return canUseServer.getFirst();
            }

            synchronized (canUseServer){
                if(canUseServer.getFirst().getRunStatus() == ServerConnection.ServerStatus.notFull){
                    return canUseServer.getFirst();
                }
                canUseServer.removeFirst();
            }
        }
        return null;
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
        for(ServerConnection serverConnection:serverPool.values()){
            serverConnection.check(0);

            if(serverConnection.getRunStatus() == ServerConnection.ServerStatus.notFull){
                if(canUseServerSet.contains(serverConnection)){
                   continue;
                }
                synchronized (canUseServer){
                    if(canUseServerSet.contains(serverConnection)){
                        continue;
                    }
                    canUseServer.addLast(serverConnection);
                    canUseServerSet.add(serverConnection);
                }
            }else {
                if(!canUseServerSet.contains(serverConnection)){
                    continue;
                }

                synchronized (canUseServer){
                    if(!canUseServerSet.contains(serverConnection)){
                        continue;
                    }
                    Iterator<ServerConnection> iterators = canUseServer.iterator();
                    while (iterators.hasNext()){
                        if(iterators.next().getId() == serverConnection.getId()){
                            iterators.remove();
                            break;
                        }
                    }
                    canUseServerSet.remove(serverConnection);
                }
            }
        }
    }
}
