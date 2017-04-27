package com.game.codec;

import com.game.manager.OnlineManager;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.message.Request;
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
        Visitor visitor = OnlineManager.getIntance().getUserById(uid);
        if(visitor == null){
            return;
        }

        IoBuffer buffer;
        if(receiveData.getCmd() == 0 && receiveData.getModule() == 0){
            buffer = DefaultSocketPackage.transformErrorMsg(receiveData.getData(),receiveData.getCompreType());
        }else {
            buffer = DefaultSocketPackage.transformByteArray(receiveData.getData(),receiveData.getCompreType(),receiveData.getModule(),receiveData.getCmd());
        }
        session.write(buffer);
    }
}
