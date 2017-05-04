package com.game.manager;

import com.module.net.DB;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class OnlineKeyManager {
    private final static OnlineKeyManager serverManager = new OnlineKeyManager();
    private Map<Integer, DB.UK> onLines = new ConcurrentHashMap<>();

    private OnlineKeyManager(){}
    public static OnlineKeyManager getIntance(){
        return serverManager;
    }

    public DB.UK getUserById(int uid){
        return onLines.get(uid);
    }

    public void putKey(int uid,DB.UK key){
        onLines.put(uid,key);
    }

    public void clearKey(int uid){
        onLines.remove(uid);
    }
}
