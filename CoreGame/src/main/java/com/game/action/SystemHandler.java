package com.game.action;

import com.game.codec.ResponseEncoderRemote;
import com.game.core.constant.GameConst;
import com.game.core.service.UserService;
import com.game.manager.DBServiceManager;
import com.game.manager.TimeCacheManager;
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
import com.module.net.NetParentOld;

import java.util.Date;

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
        /** 心跳 */
        putInvoker(0, new GameCmdModule() {
            @Override
            public boolean isRequireOnline() {
                return true;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                heart(vistor,request);
            }

            @Override
            public Request getRequset(byte[] bytes, int cmd_m, int sq) throws Exception {
                return Request.valueOf(cmd_m,null,sq);
            }
        });

        /** 第一个链接 */
        putInvoker(1, new GameCmdModule() {
            @Override
            public boolean isRequireOnline() {
                return false;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                firstConnect(vistor,request,response);
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


    private void heart(UserVistor vistor, Request request) {
        if(vistor.getHeartNum() >= 100 || vistor.getHeartTime() == 0){
            vistor.setHeartNum(0);
            vistor.setHeartTime(TimeCacheManager.getInstance().getCurTime());
            return;
        }
        vistor.setHeartNum(vistor.getHeartNum()+1);

        //低于8秒则直接关闭链接
        if(vistor.getHeartNum() >= 10 && TimeCacheManager.getInstance().getCurTime() - vistor.getHeartTime() <= (vistor.getHeartNum()>>13)){
            vistor.sendError(ResponseCode.Error.server_busy);
            PrintTool.log("heat is too fast !"+vistor.getUid());
            vistor.getIoSession().closeNow();
            return;
        }

        //发送心跳
        NetParentOld.NetCommond.Builder commond = NetParentOld.NetCommond.newBuilder();
        commond.setCmd(request.getM_cmd());
        vistor.getIoSession().write(ResponseEncoderRemote.transformByteArray(commond.build().toByteArray()));
    }

    private void firstConnect(UserVistor vistor, Request request, Response response){
        Com.NetLoginConfirm obj = (Com.NetLoginConfirm) request.getObj();
        String sn = (String) request.getAttribute("sn");

        UserService userService = DBServiceManager.getDbServiceManager().getUserService();

        DB.UK key = userService.getUserKey(vistor.getUid(),true);//从redis取key
        if(!MD5Tool.GetMD5Code(Tools.getByteJoin(obj.toByteArray(), key.toByteArray())).equals(sn)){
            vistor.getIoSession().closeNow();
            return;
        }

        UserInfo userInfo = userService.getUserInfo(vistor.getUid());
        if(userInfo == null){
            vistor.sendError(ResponseCode.Error.user_exit);
            PrintTool.error("cant find uid:"+obj.getUid());
            vistor.getIoSession().closeNow();
            return;
        }

        vistor.setUid(userInfo.getId());

        //发送登陆成功消息
        vistor.sendMsg(Response.defaultResponse(request.getM_cmd(),request.getSeq()));
    }

}
