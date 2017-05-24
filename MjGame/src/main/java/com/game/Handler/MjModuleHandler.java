package com.game.Handler;

import com.game.core.TableManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseTableVo;
import com.game.room.MjStatus;
import com.game.socket.GameSocket;
import com.game.socket.module.UserVistor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.module.core.ResponseCode;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class MjModuleHandler extends ModuleHandler {
    @Override
    public int getModule() {
        return GameConst.MOUDLE_Mj;
    }

    @Override
    protected void inititialize() {
        /**  游戏 */
        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return MjCmd.Game;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                doGame(vistor,request,response,MjStatus.Game);
            }
            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,NetGame.NetOprateData.parseFrom(bytes),sq);
            }
        });


        /**  游戏 */
        putInvoker(new GameCmdModule() {
            @Override
            public ModuleCmd getModuleCmd() {
                return MjCmd.YaPao;
            }

            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                doGame(vistor,request,response,MjStatus.Pao);
            }
            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,NetGame.NetOprateData.parseFrom(bytes),sq);
            }
        });
    }

    private void doGame(UserVistor vistor, Request request, Response response, MjStatus stats) {
        BaseTableVo tableVo = TableManager.getInstance().getTable(vistor.getGameRole().getRoomId());
        if(tableVo == null){
            vistor.sendError(ResponseCode.Error.room_not_exit);
            return;
        }
        tableVo.doAction(stats,response,vistor.getRoleId(),(NetGame.NetOprateData) request.getObj());
    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }
}
