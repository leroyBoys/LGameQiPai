package com.game;

import com.game.core.config.TablePluginManager;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class Main {
    public static void main(String[] args){
        try {

            TablePluginManager.getInstance().refresh("xml/RoomSetting.xls", "xml/pluginGen.xls");

          //  GameSocket.getIntance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
