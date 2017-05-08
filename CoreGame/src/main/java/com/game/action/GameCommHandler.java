package com.game.action;

import com.game.core.TableFactory;
import com.game.core.TableManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseTableVo;
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

                if(!baseTableVo.addChair(vistor)){
                    vistor.sendError(ResponseCode.Error.room_is_full);
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
                }

             /*   NetGame.RQCreateRoom rqCreateRoom = tableVo.enterRoom(vistor.getRoleId());
                vistor.sendMsg(Response.defaultResponse(request.getM_cmd(),request.getSeq(),rqCreateRoom));*/
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return null;
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

            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return null;
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

    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }

}
