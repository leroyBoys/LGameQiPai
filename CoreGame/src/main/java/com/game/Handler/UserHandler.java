package com.game.Handler;

import com.game.core.constant.GameConst;
import com.game.socket.GameSocket;
import com.game.socket.module.UserVistor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class UserHandler extends ModuleHandler {
    @Override
    public int getModule() {
        return GameConst.MOUDLE_User;
    }

    @Override
    protected void inititialize() {

        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return UserCmd.NULL;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd, NetGame.NetLoginConfirm.parseFrom(bytes),sq);
            }
        });


    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }


}
