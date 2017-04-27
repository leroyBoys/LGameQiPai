package com.game.util;

import com.lsocket.core.SocketServer;
import com.lsocket.message.ErrorCode;
import com.lsocket.message.Response;
import com.lsocket.module.SocketSystemCode;
import com.lsocket.module.Visitor;
import org.apache.mina.core.session.IoSession;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class GateVisitor extends Visitor {
    public GateVisitor(SocketServer socketServer, IoSession ioSession, long timeOutTime) {
        super(socketServer, ioSession, timeOutTime);
    }

    public void sendError(SocketSystemCode code) {

    }

    public void sendMsg(Response sendMsg) {

    }

    public void sendError(ErrorCode code) {

    }
}
