package com.game.core;

import com.lsocket.message.ErrorCode;
import com.lsocket.message.Response;
import com.lsocket.module.SocketSystemCode;
import com.lsocket.module.Visitor;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
public class UserVistor extends Visitor {
    private int roomId;
   /* public UserVistor(SocketServer socketServer, org.apache.mina.core.session.IoSession ioSession, long timeOutTime) {
        super(socketServer, ioSession, timeOutTime);
    }*/

    public UserVistor() {
        super(null, null, 0);
    }

    @Override
    public void sendError(SocketSystemCode socketSystemCode) {

    }

    @Override
    public void sendMsg(Response sendMsg) {
        //getIoSession()
    }

    @Override
    public void sendError(ErrorCode code) {

    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }
}
