package com.game.manager;

import com.game.socket.module.UserVistor;

import java.util.HashSet;
import java.util.Set;
/**
 * Created by leroy:656515489@qq.com
 * 2017/5/10.
 */
public class UpdateDataManager implements Runnable{
    private static UpdateDataManager ourInstance = new UpdateDataManager();

    public static UpdateDataManager getInstance() {
        return ourInstance;
    }

    private UpdateDataManager() {
        new Thread(this).start();
    }

    private Set<Integer> updateDataRoleIds = new HashSet<>();

    public synchronized void addUpdataData(int uid){
        updateDataRoleIds.add(uid);
    }

    private synchronized Set<Integer> resetUpdate(){
        Set<Integer> tmp = new HashSet<>();
        Set<Integer> ret = updateDataRoleIds;
        updateDataRoleIds = tmp;
        return ret;
    }

    @Override
    public void run() {
        while (true){
            Set<Integer> ret = resetUpdate();
            if(ret.isEmpty()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            UserVistor vistor;
            for(Integer roleId:ret){
                vistor =  OnlineManager.getIntance().getRoleId(roleId);
                if(vistor == null ||  vistor.getGameRole() == null){
                    continue;
                }
                vistor.getGameRole().update();
            }
        }
    }
}
