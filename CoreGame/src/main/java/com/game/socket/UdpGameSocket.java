package com.game.socket;

import com.game.socket.codec.RequestDecoderRemote;
import com.game.socket.codec.ResponseEncoderRemote;
import com.game.socket.control.CoreDispatcherRmote;
import com.game.socket.listen.GameHeartLinster;
import com.game.socket.module.UserVistor;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.codec.ResponseEncoder;
import com.lsocket.config.SocketConfig;
import com.lsocket.core.UdpServer;
import com.lsocket.listen.HeartListen;
import com.lsocket.module.HttpRequestType;
import com.lsocket.module.ModuleDispaterInstance;
import org.apache.mina.core.session.IoSession;

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
