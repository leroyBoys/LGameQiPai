package com.game.socket;

import com.game.codec.RequestDecoderRemote;
import com.game.codec.ResponseEncoderRemote;
import com.game.control.CoreDispatcherRmote;
import com.game.listen.GameHeartLinster;
import com.game.manager.CoreServiceManager;
import com.game.manager.DBServiceManager;
import com.game.socket.module.UserVistor;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.codec.ResponseEncoder;
import com.lsocket.config.SocketConfig;
import com.lsocket.core.SocketServer;
import com.lsocket.core.UdpServer;
import com.lsocket.listen.HeartListen;
import com.lsocket.module.HttpRequestType;
import com.lsocket.module.ModuleDispaterInstance;
import org.apache.mina.core.session.IoSession;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class UdpGameSocket extends UdpServer<UserVistor> {
    public final static UdpGameSocket gameSocket = new UdpGameSocket();

    private UdpGameSocket(){
        super(new CoreDispatcherRmote());
    }

    public static UdpGameSocket getIntance(){
        return gameSocket;
    }

    @Override
    public UserVistor createVistor(IoSession session, long timeOutTime) {
        return new UserVistor(this,session,timeOutTime);
    }

    @Override
    public ResponseEncoder initResponseEncoder() {
        return new ResponseEncoderRemote();
    }

    @Override
    public RequestDecoder initRequestDecoder() {
        return new RequestDecoderRemote(HttpRequestType.udp);
    }

    @Override
    protected void initModuleHanderConfig() {
    }

    @Override
    public HeartListen initHeartListen() {
        return new GameHeartLinster();
    }

    @Override
    public SocketConfig initConfig() {
        return SocketConfig.getInstance();
    }

    @Override
    public ModuleDispaterInstance getInnerModuleDispaterConfig() {
        return null;
    }

}
