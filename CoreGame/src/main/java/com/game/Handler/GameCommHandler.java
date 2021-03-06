package com.game.Handler;

import com.game.core.TableFactory;
import com.game.core.TableManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseTableVo;
import com.game.core.room.GameOverType;
import com.game.manager.OnlineManager;
import com.game.socket.GameSocket;
import com.game.socket.module.UserVistor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.logger.log.SystemLogger;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.*;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.module.Visitor;
import com.module.core.ResponseCode;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class GameCommHandler extends ModuleHandler<UserVistor,Request,Response> {
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
                if(vistor.getGameRole().getRoomId() != 0 && TableManager.getInstance().getTable(vistor.getGameRole().getRoomId()) != null){
                    SystemLogger.error(this.getClass(),vistor.getGameRole().getRoomId() +"is exit,cant create new room");
                    vistor.sendError(ResponseCode.Error.parmter_error);
                    return;
                }

                BaseTableVo baseTableVo = TableFactory.getInstance().createTable(vistor.getRoleId(),rpCreateRoom.getGameId());
                if(baseTableVo == null){
                    vistor.sendError(ResponseCode.Error.parmter_error);
                    return;
                }

                ResponseCode.Error code = baseTableVo.setSelected(rpCreateRoom.getTypeList());
                if(code != ResponseCode.Error.succ){
                    vistor.sendError(code);
                    return;
                }

                boolean isGoodsTable = false;
                if(isGoodsTable){
                    TableFactory.getInstance().produceGoodTableId(baseTableVo);
                }else {
                    TableFactory.getInstance().produceNewTableId(baseTableVo);
                }
                if(!baseTableVo.addChair(vistor)){
                    return;
                }

                response.setObj(baseTableVo.getEnterRoomMsg(vistor.getRoleId()));
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
               if(vistor.getGameRole().getRoomId() == 0){
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
                return Request.valueOf(module,cmd,null,sq);
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
                ready(vistor,request,response);
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,null,sq);
            }

            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.READY_NOW;
            }
        });

        putInvoker(new GameCmdModule() {
            @Override
            public void invoke(UserVistor vistor, Request request, Response response) {
                BaseTableVo table = TableManager.getInstance().getTable(vistor.getGameRole().getRoomId());
                if(table == null){
                    return;
                }
                NetGame.RPVote rpVote = (NetGame.RPVote) request.getObj();
                if(table.vote(vistor,rpVote.getIsagree())){//解散
                    table.dissolution();
                }
            }

            @Override
            public Request getRequset(byte[] bytes, int module,int cmd, int sq) throws InvalidProtocolBufferException {
                return Request.valueOf(module,cmd,NetGame.RPVote.parseFrom(bytes),sq);
            }

            @Override
            public ModuleCmd getModuleCmd() {
                return GameCommCmd.VoteDestroy;
            }
        });

    }

    private void exitTable(UserVistor vistor, Request request, Response response) {
        BaseTableVo table = TableManager.getInstance().getTable(vistor.getGameRole().getRoomId());

        if(table == null){
            return;
        }

        if(table.getGameOverType() == GameOverType.AllOver){//个人退出
            exitSelf(vistor,table,response);
            return;
        }else if(table.getCurChirCount() == table.getChairs().length){//投票
            if(table.vote(vistor,true)){//解散
                table.dissolution();
            }
            return;
        }

        if(vistor.getRoleId() == table.getOwnerId()){//解散
            table.dissolution();
            return;
        }

        exitSelf(vistor,table,response);
    }

    private void exitSelf(UserVistor vistor, BaseTableVo tableVo, Response respons){
        System.out.println("==exitSelf:"+vistor.getRoleId());
        tableVo.removeChair(vistor.getRoleId());

        //发送消息
        NetGame.RQExit.Builder rqExit = NetGame.RQExit.newBuilder();
        rqExit.setUid(vistor.getRoleId());

        NetGame.RQExit rqExit1 = rqExit.build();
        if(respons != null){
            respons.setObj(rqExit1);
            vistor.sendMsg(respons);
        }

        if(tableVo.getCurChirCount() == 0){
            return;
        }
        tableVo.sendMsgWithOutUid(Response.defaultResponse(this.getModule(),GameCommCmd.EXIT_GAME.getValue(),0,rqExit1),vistor.getRoleId());
    }


    private void middleJoin(BaseTableVo baseTableVo, UserVistor vistor, Request request, Response response) {
        baseTableVo.getChairByUid(vistor.getRoleId()).setOnline(true);

        baseTableVo.getMessageQueue(vistor.getRoleId()).setVistor(vistor);
        baseTableVo.sendChairStatusMsgWithOutUid(vistor.getRoleId());

        response.setCmd(GameCommCmd.CREATE_TABLE.getValue());
        response.setObj(baseTableVo.getEnterRoomMsg(vistor.getRoleId()));
        vistor.sendMsg(response);

        baseTableVo.sendSettlementMsg(vistor.getRoleId());
    }

    private void joinTable(BaseTableVo baseTableVo, UserVistor vistor, Request request, Response response) {

        System.out.println("==joinTable:"+vistor.getRoleId());
        if(!baseTableVo.addChair(vistor)){
            System.out.println("===>join fial");
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
            baseTableVo.sendMsgToUid(Response.defaultResponse(this.getModule(),GameCommCmd.ENTER_GAME.getValue(),0,rpEnterRoom.build()),baseTableVo.getChairs()[i].getId());
        }

        response.setModule(this.getModule());
        response.setCmd(GameCommCmd.CREATE_TABLE.getValue());
        response.setObj(baseTableVo.getEnterRoomMsg(vistor.getRoleId()));
        vistor.sendMsg(response);
    }

    private void ready(UserVistor vistor, Request request, Response response) {
        BaseTableVo table = TableManager.getInstance().getTable(vistor.getGameRole().getRoomId());

        if(table == null || table.getStatus().getValue() != 0){
          //  vistor.sendError(ResponseCode.Error.parmter_error);
            SystemLogger.error(this.getClass(),"roomId:"+vistor.getGameRole().getRoomId()+(table==null?"空":("status:"+table.getStatus().getValue())));
            return;
        }

        table.doAction(table.getStatus(),response,vistor.getRoleId(),null);
    }

    @Override
    public CoreDispatcher getDispatcher() {
        return GameSocket.getIntance().getCoreDispatcher();
    }

    @Override
    public void session_closed(UserVistor vister) {
        if(vister.getGameRole() == null){
            return;
        }

        BaseTableVo table = TableManager.getInstance().getTable(vister.getGameRole().getRoomId());

        if(table == null){
            return;
        }

        if(table.getChairs().length <= 1){
            exitSelf(vister,table,null);
            return;
        }

        table.leaveOffline(vister.getRoleId());

    }
}
