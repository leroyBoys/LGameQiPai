package com.socket;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class SocketManager {
    private final static SocketManager socket = new SocketManager();
    private SocketManager(){}

    public static final SocketManager getInstance(){
        return socket;
    }




}
