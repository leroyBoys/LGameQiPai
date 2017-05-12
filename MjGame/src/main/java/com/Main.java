package com;

import com.game.manager.DBServiceManager;
import com.game.socket.GameSocket;
import com.game.socket.UdpGameSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    //使用spring或者其他启动类
    public static void startSocket() {
        String[] args = {"1"};
        main(args);
    }

    //使用java命令 运行main方法启动
    public static void main(String[] args) {
        try {
            startTCP();
            startUDP();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void startTCP() throws Exception {
        GameSocket.getIntance().start(DBServiceManager.getInstance().getGameServer().getPort());
    }

    public static void startUDP() throws Exception {
        UdpGameSocket.getIntance().start(DBServiceManager.getInstance().getGameServer().getUdpPort());
    }
}
