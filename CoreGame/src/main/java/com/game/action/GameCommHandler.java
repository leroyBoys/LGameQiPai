package com.game.action;

import com.game.core.TableFactory;
import com.game.core.TableManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseGameStatus;
import com.game.core.room.BaseTableVo;
import com.game.manager.OnlineManager;
import com.game.socket.GameSocket;
import com.game.socket.module.UserVistor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.lgame.util.comm.StringTool;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.*;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.module.HttpRequestType;
import com.module.core.ResponseCode;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class GameCommHandler extends ModuleHandler {
    @Override
    public int getModule() {
        return GameConst.MOUDLE_GameComm;
    }

    @Override
    protected void inititialize() {
        /** 创建房间 */
        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.CREATE_TABLE;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                NetGame.RPCreateRoom rpCreateRoom = (NetGame.RPCreateRoom) request.getObj();
                BaseTableVo baseTableVo = TableFactory.getInstance().createTable(vistor.getUid(),rpCreateRoom.getGameId());
                ResponseCode.Error code = baseTableVo.setSelected(rpCreateRoom.getTypeList());
                if(code != ResponseCode.Error.succ){
                    vistor.sendError(code);
                    return;
                }

                if(baseTableVo.addChair(vistor)){
                    return;
                }

                response.setObj(baseTableVo.sendEnterRoom(vistor.getRoleId()));
                vistor.sendMsg(response);
            }
            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,NetGame.RPCreateRoom.parseFrom(bytes),sq);
            }
        });

        /** 加入房间 */
        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.ENTER_GAME;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                NetGame.RPEnterRoom rpEnterRoom = (NetGame.RPEnterRoom) request.getObj();

                BaseTableVo tableVo = TableManager.getInstance().getTable(rpEnterRoom.getRoomId());
                if(tableVo == null){
                    vistor.sendError(ResponseCode.Error.room_not_exit);
                    return;
                }else if(tableVo.getChairByUid(vistor.getRoleId()) != null){
                    //中途加入
                    middleJoin(tableVo,vistor,request,response);
                    return;
                }

                joinTable(tableVo,vistor,request,response);
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,NetGame.RPEnterRoom.parseFrom(bytes),sq);
            }

        });

        /**
         * 退出房间
         */
        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.EXIT_GAME;
            }
            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
               if(vistor.getRoomId() == 0){
                   return;
               }

               exitTable(vistor,request,response);
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,null,sq);
            }
        });

        /**
         * 托管
         */
        putInvoker(new GameCmdModule() {
            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {

            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return null;
            }

            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.AUTO_PLAY;
            }
        });

        /**
         * 准备
         */
        putInvoker(new GameCmdModule() {
            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {

            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return null;
            }

            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.READY_NOW;
            }
        });

        putInvoker(new GameCmdModule() {
            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {


            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,null,sq);
            }

            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.VoteDestroy;
            }
        });

    }

    private void exitTable(UserVistor vistor, Request request, Response response) {
        BaseTableVo table = TableManager.getInstance().getTable(vistor.getRoomId());

        if(table == null){
            return;
        }

        if(table.getStatus().getValue() != 0){//需要投票
            return;
        }


    }

    private void middleJoin(BaseTableVo baseTableVo, UserVistor vistor, Request request, Response response) {
        baseTableVo.getChairByUid(vistor.getRoleId()).setOnline(true);

        NetGame.RQUserStatus.Builder rpEnterRoom = NetGame.RQUserStatus.newBuilder();
        rpEnterRoom.setStatus(baseTableVo.getChairStatusToClient(baseTableVo.getChairByUid(vistor.getRoleId())));

        for(int i = 0;i<baseTableVo.getChairs().length;i++){
            if(baseTableVo.getChairs()[i] == null || vistor.getRoleId() == baseTableVo.getChairs()[i].getId()){
                continue;
            }
            //给其他人发送
            OnlineManager.getIntance().getUserById(baseTableVo.getChairs()[i].getId()).sendMsg(Response.defaultResponse(GameCommCmd.CREATE_TABLE.getModule(),GameCommCmd.CREATE_TABLE.getValue(),0,rpEnterRoom.build()));
        }

        response.setObj(baseTableVo.sendEnterRoom(vistor.getRoleId()));
        vistor.sendMsg(response);
    }

    private void joinTable(BaseTableVo baseTableVo, UserVistor vistor, Request request, Response response) {
        if(baseTableVo.addChair(vistor)){
            return;
        }

        NetGame.NetUserData netUserData = baseTableVo.getOtherNetUserData(baseTableVo.getChairByUid(vistor.getRoleId()));
        NetGame.RQEnterRoom.Builder rpEnterRoom = NetGame.RQEnterRoom.newBuilder();
        rpEnterRoom.setUser(netUserData);

        for(int i = 0;i<baseTableVo.getChairs().length;i++){
            if(baseTableVo.getChairs()[i] == null || vistor.getRoleId() == baseTableVo.getChairs()[i].getId()){
                continue;
            }
            //给其他人发送
            OnlineManager.getIntance().getUserById(baseTableVo.getChairs()[i].getId()).sendMsg(Response.defaultResponse(this.getModule(),GameCommCmd.ENTER_GAME.getValue(),0,rpEnterRoom.build()));
        }

        response.setModule(this.getModule());
        response.setCmd(GameCommCmd.CREATE_TABLE.getValue());
        response.setObj(baseTableVo.sendEnterRoom(vistor.getRoleId()));
        vistor.sendMsg(response);
    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }

}
