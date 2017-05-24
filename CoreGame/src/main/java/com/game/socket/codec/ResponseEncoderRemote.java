package com.game.socket.codec;

import com.game.manager.TimeCacheManager;
import com.game.socket.module.UserVistor;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.logger.log.SystemLogger;
import com.lsocket.codec.ResponseEncoder;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Response;
import com.lsocket.util.SocketConstant;
import com.module.net.DB;
import com.module.net.NetParentOld;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

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
            DB.UK key = vistor.getUk();
            int uid = 0;
            String keyValue = "test";
            if(key != null){
                uid = key.getUid();
                keyValue = key.getKey();
            }

            byte[] t = getCommondMes((Response) message,uid,keyValue, session);
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
    //    SystemLogger.info(ResponseEncoderRemote.class,"=========22=>sendssssss"+ Arrays.toString(bytes));
        int messageLength = bytes.length;
        IoBuffer buffer = IoBuffer.allocate(messageLength + 4);
        buffer.setAutoExpand(true);

        buffer.putInt(messageLength);
        buffer.put(bytes);
        buffer.flip();
        buffer.free();
        return buffer;
    }

    public static byte[] getCommondMes(Response response, int uid,String key, IoSession session) throws Exception {
        NetParentOld.NetCommond.Builder com = NetParentOld.NetCommond.newBuilder();

        int cmdc = CMDManager.getCmd_M(response.getModule(),response.getCmd());
        com.setCmd(cmdc);
        com.setTime((int) (TimeCacheManager.getInstance().getCurTime() / 1000));
        com.setStatus(response.getStatus());
        int seq = response.getSeq();
        com.setSeq(seq);

        Message obj = response.getObj();

        byte[] datas = response.getValue();
        if(datas == null && obj != null){
            datas = obj.toByteArray();
            SystemLogger.info(ResponseEncoderRemote.class,uid+"---Send-1--cmd:"+ CMDManager.getCmd(com.getCmd())+"  module:"+CMDManager.getModule(com.getCmd())+"  "+obj.toString());
        }else {
            SystemLogger.info(ResponseEncoderRemote.class,uid+"---Send-2--cmd:"+ CMDManager.getCmd(com.getCmd())+"  module:"+CMDManager.getModule(com.getCmd())+com.build().toString());
        }

        if(datas != null){
           // byte[] data = ZipTool.compressBytes(datas);//压缩
            com.setObj(ByteString.copyFrom(datas));

          /*  String d = MD5Tool.GetMD5Code(Tools.getByteJoin(data, key.getBytes()));//加密
            com.setSn(d);*/
        }

        return com.build().toByteArray();
    }

}
