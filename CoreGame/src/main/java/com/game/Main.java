package com.game;

import com.game.socket.GameSocket;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class Main {
    public static void main(String[] args){
        try {
            GameSocket.getIntance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
