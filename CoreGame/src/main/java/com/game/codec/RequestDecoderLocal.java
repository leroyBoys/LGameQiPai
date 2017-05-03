package com.game.codec;

import com.game.manager.OnlineManager;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.handler.CmdModule;
import com.lsocket.manager.CMDManager;
import com.lsocket.module.Visitor;
import com.lsocket.util.DefaultSocketPackage;
import com.lsocket.util.ReceiveData;
import com.net.NetParent;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class RequestDecoderLocal extends RequestDecoder {

    @Override
    public void doRequeset(IoSession session, ProtocolDecoderOutput out,ReceiveData receiveData) throws InvalidProtocolBufferException {
        NetParent.NetServerCommond netServerCommond = NetParent.NetServerCommond.parseFrom(receiveData.getData());
        int uid = netServerCommond.getUid();

        NetParent.NetCommond netCommond = NetParent.NetCommond.parseFrom(netServerCommond.getObj());

        int cmd_c = CMDManager.getCmd_M(receiveData.getModule(),receiveData.getCmd());
        CmdModule cmdModule = CMDManager.getIntance().getCmdModule(cmd_c);
        out.write(cmdModule.getRequset(netCommond.getObj().toByteArray(),cmd_c,netCommond.getSeq()));
    }
}
