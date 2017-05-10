package com.game.action;

import com.game.codec.ResponseEncoderRemote;
import com.game.core.constant.GameConst;
import com.game.core.service.UserService;
import com.game.manager.DBServiceManager;
import com.game.manager.OnlineManager;
import com.game.manager.TimeCacheManager;
import com.game.socket.GameSocket;
import com.game.socket.module.GameRole;
import com.game.socket.module.UserVistor;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.module.Status;
import com.module.core.ResponseCode;
import com.module.db.RoleInfo;
import com.module.db.UserInfo;
import com.module.net.Com;
import com.module.net.DB;
import com.module.net.NetGame;
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
        putInvoker(new GameCmdModule() {

            @Override
            public ModuleCmd getModuleCmd() {
                return SystemCmd.heart;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                heart(vistor,request,response);
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws Exception {
                return Request.valueOf(module,cmd,null,sq);
            }
        });

        /** 第一个链接 */
        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return SystemCmd.firstConnection;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                firstConnect(vistor,request,response);
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws Exception {
                return Request.valueOf(module,cmd,Com.NetLoginConfirm.parseFrom(bytes),sq);
            }
        });

    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }


    private void heart(UserVistor vistor, Request request, Response response) {
        if(vistor.getHeartNum() >= 100 || vistor.getHeartTime() == 0){
            vistor.setHeartNum(0);
            vistor.setHeartTime(TimeCacheManager.getInstance().getCurTime());
            return;
        }
        vistor.setHeartNum(vistor.getHeartNum()+1);

        //低于8秒则直接关闭链接
        if(vistor.getHeartNum() >= 10 && TimeCacheManager.getInstance().getCurTime() - vistor.getHeartTime() <= (vistor.getHeartNum()>>13)){
            vistor.sendError(ResponseCode.Error.server_busy);
            PrintTool.log("heat is too fast !"+vistor.getRoleId());
            vistor.getIoSession().closeNow();
            return;
        }

        //发送心跳
        NetParentOld.NetCommond.Builder commond = NetParentOld.NetCommond.newBuilder();
        commond.setCmd(CMDManager.getCmd_M(request.getModule(),request.getCmd()));
        vistor.getIoSession().write(ResponseEncoderRemote.transformByteArray(commond.build().toByteArray()));
    }

    private void firstConnect(UserVistor vistor, Request request, Response response){
        Com.NetLoginConfirm obj = (Com.NetLoginConfirm) request.getObj();
        String sn = (String) request.getAttribute("sn");

        UserService userService = DBServiceManager.getDbServiceManager().getUserService();

        DB.UK key = userService.getUserKey(obj.getUid());//从redis取key
        if(!MD5Tool.GetMD5Code(Tools.getByteJoin(obj.toByteArray(), key.getKey().getBytes())).equals(sn)){
            vistor.getIoSession().closeNow();
            return;
        }

        vistor.setUk(key);
        UserInfo userInfo = userService.getUserInfo(obj.getUid());
        if(userInfo == null){
            vistor.sendError(ResponseCode.Error.user_exit);
            PrintTool.error("cant find uid:"+obj.getUid());
            vistor.getIoSession().closeNow();
            return;
        }

        connectNow(request,userInfo,vistor,response);
    }

    private void connectNow(Request request, UserInfo userInfo, UserVistor vistor, Response response) {
        if (userInfo.getUserStatus() - Status.UserStatus.freeze.getValue() == 0) {
            if (userInfo.getStatusEndTime() == null || System.currentTimeMillis() - userInfo.getStatusEndTime().getTime() < 0) {
                vistor.sendError(ResponseCode.Error.free_now);
                return;
            }
        }

        UserService userService = DBServiceManager.getDbServiceManager().getUserService();
        //发送连接成功
        RoleInfo info = userService.getRoleInfoByUid(userInfo.getId());
        if (info == null) {
            //如果多角色则返回选择页面
            //如果不是自动初始化
            info = initRole(userInfo.getId());

            ///初始化创建角色奖励
            initLoginReward(info);
        }

        vistor.setRoleInfo(info);
        OnlineManager.getIntance().putOnlineList(userInfo.getId(), info.getId(), vistor);

        GameRole gameRole = DBServiceManager.getDbServiceManager().getGameRedis().getGameRole(info.getId());
        vistor.setGameRole(gameRole);
        userService.updateUserInfoLoginStatus(userInfo.getId(), true, new Date());
        //发送登陆成功消息

        NetGame.RQConnect.Builder connect = NetGame.RQConnect.newBuilder();
        connect.setRoomId(gameRole.getRoomId());
        response.setObj(connect.build());
        vistor.sendMsg(response);
    }

    private RoleInfo initRole(int uid) {
        int sex = -1;
        RoleInfo info = new RoleInfo(uid, "", "", sex, "");
        int id = DBServiceManager.getDbServiceManager().getUserService().createRoleInfo(uid, info.getUserAlise(), info.getHeadImage(), info.getUserHead(), sex, info.getUserLv(), (int) info.getUserExp(), info.getVipLevel());
        info.setId(id);
        return info;
    }

    private void initLoginReward(RoleInfo info) {

    }
}
