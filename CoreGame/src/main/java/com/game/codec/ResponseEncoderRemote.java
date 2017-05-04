package com.game.codec;

import com.game.core.service.UserService;
import com.game.manager.DBServiceManager;
import com.game.manager.TimeCacheManager;
import com.game.socket.module.UserVistor;
import com.google.protobuf.ByteString;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.lgame.util.encry.ZipTool;
import com.lsocket.codec.ResponseEncoder;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Response;
import com.lsocket.util.SocketConstant;
import com.module.net.DB;
import com.module.net.NetParentOld;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.util.Date;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class ResponseEncoderRemote extends ResponseEncoder {
    public void encode(IoSession session, Object message, ProtocolEncoderOutput encoderOutput) throws Exception {
        if (message == null) {
            return;
        }

        if ((message instanceof IoBuffer)) {
            encoderOutput.write(message);
            encoderOutput.flush();
            return;
        }

        try {
            UserVistor vistor = (UserVistor) session.getAttribute(SocketConstant.SessionKey.vistorKey);
            UserService userService = DBServiceManager.getDbServiceManager().getUserService();
            DB.UK key = userService.getUserKey(vistor.getUid(),false);
            
            byte[] t = getCommondMes((Response) message, key, session);
            // 定义一个发送消息协议格式：|--header:4 byte--|--content:10MB--|
            IoBuffer buf = transformByteArray(t);

            if (buf != null) {
                encoderOutput.write(buf);
                encoderOutput.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static IoBuffer transformByteArray(byte[] bytes) {
        if ((bytes == null) || (bytes.length == 0)) {
            return null;
        }
        int messageLength = bytes.length;
        IoBuffer buffer = IoBuffer.allocate(messageLength + 4);
        buffer.setAutoExpand(true);

        buffer.putInt(messageLength);
        buffer.put(bytes);
        buffer.flip();
        buffer.free();
        return buffer;
    }

    public static byte[] getCommondMes(Response response, DB.UK key, IoSession session) throws Exception {
        NetParentOld.NetCommond.Builder com = NetParentOld.NetCommond.newBuilder();

        com.setCmd(response.getM_cmd());
        com.setTime((int) (TimeCacheManager.getInstance().getCurTime() / 1000));
        com.setStatus(response.getStatus());
        int seq = response.getSeq();
        com.setSeq(seq);

        PrintTool.info(key.getUid()+"---Send---cmd:"+ CMDManager.getCmd(com.getCmd())+"  module:"+CMDManager.getModule(com.getCmd()));

        if(response.getValue() != null){
            byte[] data = ZipTool.compressBytes(response.getValue());//压缩
            com.setObj(ByteString.copyFrom(data));

            String d = MD5Tool.GetMD5Code(Tools.getByteJoin(data, key.toByteArray()));//加密
            com.setSn(d);
        }

        return com.build().toByteArray();
    }

}
