package com.game.handler;

import com.game.socket.GameSocket;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleHandler;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class LoginGateModuleHandler extends ModuleHandler {

    @Override
    public int getModule() {
        return 0;
    }

    @Override
    protected void inititialize() {

    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }
}
