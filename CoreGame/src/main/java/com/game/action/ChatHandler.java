package com.game.action;

import com.game.core.constant.GameConst;
import com.game.core.chat.ChatChannelManager;
import com.game.socket.GameSocket;
import com.game.socket.module.UserVistor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.module.ChannelType;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class ChatHandler extends ModuleHandler {
    @Override
    public int getModule() {
        return GameConst.MOUDLE_Chat;
    }

    @Override
    protected void inititialize() {

        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return ChatCmd.Chat;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                NetGame.NetChat chat = (NetGame.NetChat) request.getObj();
                ChatChannelManager.getInstance().sendMsg(ChannelType.valueOf(chat.getChannel()), vistor, chat.getReceiveId(), chat.getContent(),chat.getIsaction());
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
