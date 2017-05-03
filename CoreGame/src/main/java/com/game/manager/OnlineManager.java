package com.game.manager;

import com.lsocket.module.Visitor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class OnlineManager{
    private final static OnlineManager serverManager = new OnlineManager();
    private Map<Integer,Visitor> onLines = new HashMap<>();

    private OnlineManager(){}
    public static OnlineManager getIntance(){
        return serverManager;
    }

    public Visitor getUserById(int uid){
        return onLines.get(uid);
    }

}
