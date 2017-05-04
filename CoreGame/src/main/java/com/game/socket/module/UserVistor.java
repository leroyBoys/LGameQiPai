package com.game.socket.module;

import com.game.socket.GameSocket;
import com.lsocket.core.SocketServer;
import com.lsocket.message.ErrorCode;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.module.SocketSystemCode;
import com.lsocket.module.Visitor;
import com.module.core.ResponseCode;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
public class UserVistor extends Visitor<Request,Response,ResponseCode.Error> {
    private int roomId;
    private int module;
    public UserVistor(GameSocket socketServer, org.apache.mina.core.session.IoSession ioSession, long timeOutTime) {
        super(socketServer, ioSession, timeOutTime);
    }

    public UserVistor() {
        super(null, null, 0);
    }

    @Override
    public void sendError(SocketSystemCode socketSystemCode) {

    }

    @Override
    public void sendMsg(Response sendMsg) {
        this.getIoSession().write(sendMsg);
    }

    @Override
    public void sendError(ResponseCode.Error code) {
        this.getIoSession().write(code.getMsg());
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }
}
