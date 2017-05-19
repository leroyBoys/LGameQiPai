package com.game.socket.module;

import com.lsocket.message.Response;
import com.lsocket.module.SocketSystemCode;
import com.module.core.ResponseCode;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
public class RobotVistor extends UserVistor {


    public RobotVistor(int roleId) {
        super(null, null, 0);
        this.setRoleId(roleId);
    }


    @Override
    public void sendError(SocketSystemCode socketSystemCode) {
    }

    @Override
    public void sendMsg(Response sendMsg) {
    }

    @Override
    public void sendError(ResponseCode.Error code) {
    }

}
