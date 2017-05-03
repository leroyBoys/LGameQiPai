package com.game.action;

import com.game.core.constant.GameConst;
import com.game.manager.OnlineKeyManager;
import com.game.socket.GameSocket;
import com.game.socket.module.UserVistor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.CmdModule;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.util.SocketConstant;
import com.module.core.ResponseCode;
import com.module.db.UserInfo;
import com.module.net.Com;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class SystemHandler extends ModuleHandler {
    @Override
    public int getModule() {
        return GameConst.MOUDLE_System;
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
                Com.NetLoginConfirm obj = (Com.NetLoginConfirm) request.getObj();
                String sn = (String) vistor.getIoSession().getAttribute("sn");

                String key = "";//从redis取key
                if(!MD5Tool.GetMD5Code(Tools.getByteJoin("test!@0".getBytes(), key.getBytes())).equals(sn)){
                    vistor.getIoSession().closeNow();
                    return;
                }

                UserInfo userInfo = null;
                if(userInfo == null){
                    vistor.sendError(ResponseCode.Error.user_exit);
                    PrintTool.error("cant find uid:"+obj.getUid());
                    vistor.getIoSession().closeNow();
                    return;
                }

                vistor.setUid(userInfo.getId());
                OnlineKeyManager.getIntance().putKey(userInfo.getId(),key);

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
