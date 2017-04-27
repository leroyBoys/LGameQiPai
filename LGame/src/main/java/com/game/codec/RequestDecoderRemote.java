package com.game.codec;

import com.game.manager.ServerManager;
import com.lgame.util.encry.ZipTool;
import com.logger.log.SystemLogger;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.handler.CmdModule;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Request;
import com.lsocket.module.Visitor;
import com.lsocket.util.DefaultSocketPackage;
import com.lsocket.util.ReceiveData;
import com.lsocket.util.SocketConstant;
import com.net.NetParent;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.io.IOException;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class RequestDecoderRemote extends RequestDecoder {

    @Override
    public void doRequeset(IoSession session, ProtocolDecoderOutput out,ReceiveData receiveData) throws IOException {
        if(receiveData.getCmd() == 0 || receiveData.getModule() == 0){
            session.closeNow();
            SystemLogger.error(this.getClass(),"cmd :"+receiveData.getCmd()+" module:"+receiveData.getModule());
            return;
        }

        int cmd_m = CMDManager.getCmd_M(receiveData.getModule(),receiveData.getCmd());
        CmdModule cmdModule = CMDManager.getIntance().getCmdModule(cmd_m);

        Visitor visitor = (Visitor) session.getAttribute(SocketConstant.SessionKey.vistorKey);
        if(visitor == null){
            session.closeNow();
            SystemLogger.error(this.getClass(),"visitor is Null :");
            return;
        }


        int compressType = receiveData.getCompreType();
        if(cmdModule == null){//必须是登录过的，否则非法
            if(visitor.getStatus() != Visitor.Status.Logined){
                SystemLogger.error(this.getClass(),"cmd :"+receiveData.getCmd()+" module:"+receiveData.getModule()+"visitor status :"+visitor.getStatus());
                return;
            }

            IoBuffer ioBuffer = DefaultSocketPackage.transformByteArray(receiveData.getData(),compressType,(byte) receiveData.getModule(),(byte)receiveData.getCmd());
            ServerManager.getIntance().getServerConnection(visitor.getServerId()).send(ioBuffer);
            return;
        }
        //

        NetParent.NetCommond netCommond = NetParent.NetCommond.parseFrom(receiveData.getData());
        if(receiveData.getCompreType() != 0){
            out.write(cmdModule.getRequset(ZipTool.uncompressBytes(receiveData.getData()),cmd_m,netCommond.getSeq()));
        }else {
            out.write(cmdModule.getRequset(receiveData.getData(),cmd_m,netCommond.getSeq()));
        }
    }
}
