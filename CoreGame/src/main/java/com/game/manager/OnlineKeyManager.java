package com.game.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class OnlineKeyManager {
    private final static OnlineKeyManager serverManager = new OnlineKeyManager();
    private Map<Integer,String> onLines = new ConcurrentHashMap<>();

    private OnlineKeyManager(){}
    public static OnlineKeyManager getIntance(){
        return serverManager;
    }

    public String getUserById(int uid){
        return onLines.get(uid);
    }

    public void putKey(int uid,String key){
        onLines.put(uid,key);
    }
}
