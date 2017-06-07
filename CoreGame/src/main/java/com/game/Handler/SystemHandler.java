package com.game.Handler;

import com.game.core.TableManager;
import com.game.socket.codec.ResponseEncoderRemote;
import com.game.core.constant.GameConst;
import com.game.core.service.UserService;
import com.game.manager.DBServiceManager;
import com.game.manager.OnlineManager;
import com.game.manager.TimeCacheManager;
import com.game.socket.GameSocket;
import com.game.socket.module.GameRole;
import com.game.socket.module.UserVistor;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.StringTool;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.logger.log.SystemLogger;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.util.SocketConstant;
import com.module.FromType;
import com.module.Status;
import com.module.core.ResponseCode;
import com.module.db.RoleInfo;
import com.module.db.UserInfo;
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
                return Request.valueOf(module,cmd, NetGame.NetLoginConfirm.parseFrom(bytes),sq);
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

        if(vistor.getGameRole().getRoomId() != 0){
            TableManager.getInstance().trigger(vistor.getGameRole().getRoomId());
        }
    }

    private void firstConnect(UserVistor vistor, Request request, Response response){
        NetGame.NetLoginConfirm obj = (NetGame.NetLoginConfirm) request.getObj();
        String sn = (String) request.getAttribute("sn");

        UserService userService = DBServiceManager.getInstance().getUserService();

        DB.UK key = userService.getUserKey(obj.getUid());//从redis取key
        byte[] data = (byte[]) request.getAttribute("bytes");
        if(key == null){
            SystemLogger.error(this.getClass(),"cant find key from uid:"+obj.getUid());
            vistor.getIoSession().closeNow();
            return;
        }else if(!MD5Tool.GetMD5Code(Tools.getByteJoin(data, key.getKey().getBytes())).equals(sn)){
            vistor.getIoSession().closeNow();
            SystemLogger.error(this.getClass(),"key not same from uid:"+obj.getUid());
            return;
        }else if(StringTool.isNotNull(key.getIpPort()) && !key.getIpPort().equals(SocketConstant.getLocalIp().getAll())){
            vistor.getIoSession().closeNow();
            SystemLogger.error(this.getClass(),"you should load:"+key.getIpPort());
            return;
        }

        //OnlineManager.getIntance().getRoleId()
        UserInfo userInfo;
        if(!userService.loginConfim(obj.getUid())){
            userInfo = userService.getUserInfo(obj.getUid());

            if(userInfo == null || userInfo.getOnLineType() == UserInfo.OnLineType.login){
                SystemLogger.error(this.getClass(),"you login failed :uid:"+obj.getUid());
                return;
            }else {
                //断线重连
            }
        }else {
            userInfo = userService.getUserInfo(obj.getUid());
        }

        if(userInfo == null){
            vistor.sendError(ResponseCode.Error.user_exit);
            PrintTool.error("cant find uid:"+obj.getUid());
            vistor.getIoSession().closeNow();
            return;
        }

        key.toBuilder().setKey(SocketConstant.getLocalIp().getAll());
        userService.setUserKey(key.getUid(),key.getIpPort(),key.getKey());

        vistor.setUk(key);
        connectNow(request,userInfo,vistor,response);
    }

    private void connectNow(Request request, UserInfo userInfo, UserVistor vistor, Response response) {
        if (userInfo.getUserStatus() - Status.UserStatus.freeze.getValue() == 0) {
            if (userInfo.getStatusEndTime() == null || System.currentTimeMillis() - userInfo.getStatusEndTime().getTime() < 0) {
                vistor.sendError(ResponseCode.Error.free_now);
                return;
            }
        }

        UserService userService = DBServiceManager.getInstance().getUserService();
        //发送连接成功
        RoleInfo info = userService.getRoleInfoByUid(userInfo.getId());
        GameRole gameRole = null;
        boolean isNew = info == null;
        if (isNew) {
            //如果多角色则返回选择页面
            //如果不是自动初始化
            info = initRole(userInfo);
            gameRole = new GameRole(info.getId(),0,0);
        }else {
            gameRole = DBServiceManager.getInstance().getGameRedis().getGameRole(info.getId());
        }

        UserVistor lastUser = OnlineManager.getIntance().getRoleId(info.getId());
        if(lastUser != null && lastUser.getIoSession().getId() != vistor.getIoSession().getId()){//如果有在线的，则提示踢掉对方、不能登录
            lastUser.setSelfOffLine(false);
            lastUser.sendError(ResponseCode.Error.other_login);
            lastUser.getIoSession().closeNow();
        }

        vistor.setGameRole(gameRole);

        vistor.setRoleInfo(info);
        OnlineManager.getIntance().putOnlineList(info.getId(),userInfo.getId(),  vistor);
        userService.updateUserInfoLoginStatus(userInfo.getId(), true, new Date());
        //发送登陆成功消息
        if(isNew){
            ///初始化创建角色奖励
            initLoginReward(gameRole);
        }

        NetGame.RQConnect.Builder connect = NetGame.RQConnect.newBuilder();
        connect.setRoomId(gameRole.getRoomId());
        response.setObj(connect.build());
        vistor.sendMsg(response);
    }

    private RoleInfo initRole(UserInfo userInfo) {
        int sex = -1;
        RoleInfo info = new RoleInfo(userInfo.getId(), "", "", sex);
        if(userInfo.getUserFromType() == FromType.tx.val()){//获取数据

        }

        int id = DBServiceManager.getInstance().getUserService().createRoleInfo(userInfo.getId(), info.getUserAlise(), info.getHeadImage(), sex, info.getUserLv(), (int) info.getUserExp(), info.getVipLevel());
        info.setId(id);
        return info;
    }

    private void initLoginReward(GameRole roleInfo) {

        roleInfo.setCard(10);
    }
}
