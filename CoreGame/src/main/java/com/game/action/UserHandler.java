package com.game.action;

import com.game.core.constant.GameConst;
import com.game.core.service.UserService;
import com.game.manager.DBServiceManager;
import com.game.socket.GameSocket;
import com.game.socket.module.UserVistor;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.module.core.ResponseCode;
import com.module.db.UserInfo;
import com.module.net.Com;
import com.module.net.DB;

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

        putInvoker(1, new GameCmdModule() {
            @Override
            public boolean isRequireOnline() {
                return false;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
            }

            @Override
            public Request getRequset(byte[] bytes, int cmd_m, int sq) throws Exception {
                return Request.valueOf(cmd_m,Com.NetLoginConfirm.parseFrom(bytes),sq);
            }
        });


    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }


}
