package com.game.action;

import com.game.core.constant.GameConst;
import com.game.socket.GameSocket;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.CmdModule;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.module.HttpRequestType;
import com.lsocket.module.Visitor;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class MjModuleHandler extends ModuleHandler {
    @Override
    public int getModule() {
        return GameConst.MOUDLE_Mj;
    }

    @Override
    protected void inititialize() {
    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }
}
