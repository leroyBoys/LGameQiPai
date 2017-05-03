package com.game.socket.module;

import com.lsocket.core.SocketServer;
import com.lsocket.message.ErrorCode;
import com.lsocket.message.Response;
import com.lsocket.module.SocketSystemCode;
import com.lsocket.module.Visitor;
import org.apache.mina.core.session.IoSession;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class GateVistor extends Visitor {
    public GateVistor(SocketServer socketServer, IoSession ioSession, long timeOutTime) {
        super(socketServer, ioSession, timeOutTime);
    }

    @Override
    public void sendError(SocketSystemCode code) {

    }

    @Override
    public void sendMsg(Response sendMsg) {

    }

    @Override
    public void sendError(ErrorCode code) {

    }
}
