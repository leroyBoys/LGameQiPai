package com.game.manager;

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
    }

    private Set<Integer> updateDataRoleIds = new HashSet<>();

    public synchronized void addUpdataData(int uid){
        updateDataRoleIds.add(uid);
    }


    @Override
    public void run() {

    }
}
