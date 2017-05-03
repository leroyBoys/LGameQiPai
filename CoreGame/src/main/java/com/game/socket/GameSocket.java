package com.game.socket;

import com.game.codec.RequestDecoderRemote;
import com.game.codec.ResponseEncoderRemote;
import com.game.control.CoreDispatcherRmote;
import com.game.listen.GameHeartLinste;
import com.game.util.GateVisitor;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.codec.ResponseEncoder;
import com.lsocket.config.SocketConfig;
import com.lsocket.core.SocketServer;
import com.lsocket.listen.HeartListen;
import com.lsocket.module.ModuleDispaterInstance;
import com.lsocket.module.Visitor;
import org.apache.mina.core.session.IoSession;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class GameSocket extends SocketServer<Visitor>{
    public final static GameSocket gameSocket = new GameSocket();

    private GameSocket(){
        super(new CoreDispatcherRmote());
       // DBServiceManager.getInstance(PropertiesTool.loadProperty("server.properties")).load();
        //CoreServiceManager.getIntance().load();
    }

    public static GameSocket getIntance(){
        return gameSocket;
    }

    @Override
    public Visitor createVistor(IoSession session, SocketServer socketServer, long timeOutTime) {
        return new GateVisitor(socketServer,session,timeOutTime);
    }

    @Override
    public ResponseEncoder initResponseEncoder() {
        return new ResponseEncoderRemote();
    }

    @Override
    public RequestDecoder initRequestDecoder() {
        return new RequestDecoderRemote();
    }

    @Override
    public HeartListen initHeartListen() {
        return new GameHeartLinste();
    }

    @Override
    public SocketConfig initConfig() {
        return SocketConfig.getInstance();
    }

    @Override
    public ModuleDispaterInstance getInnerModuleDispaterConfig() {
        ModuleDispaterInstance ins = new ModuleDispaterInstance();
        List<ModuleDispaterInstance.Obj> objs = new LinkedList<>();
        objs.add(new ModuleDispaterInstance.Obj("com.game.action.handler"));
        ins.setObjList(objs);
        return ins;
    }

}
