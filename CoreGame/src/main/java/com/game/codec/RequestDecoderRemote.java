package com.game.codec;

import com.game.manager.OnlineKeyManager;
import com.game.manager.OnlineManager;
import com.game.socket.module.UserVistor;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.handler.CmdModule;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.util.ReceiveData;
import com.lsocket.util.SocketConstant;
import com.module.core.ResponseCode;
import com.module.net.NetParentOld;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.io.IOException;
/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class RequestDecoderRemote extends RequestDecoder {

    protected void sendHeard(IoSession session){

    }

    protected boolean doDecode(IoSession session, IoBuffer input, ProtocolDecoderOutput out) throws Exception {
        int remainSize = input.remaining();
        if (remainSize > 0) {
            input.mark();// 标记当前位置，以便reset
            byte header = input.get();
            if((header&1) == 1){
                if(socketServer.getHeartListen() != null && !socketServer.getHeartListen().checkHeart(session)){//是否合法
                    session.closeNow();
                    return false;//父类接收新数据
                }else {
                    sendHeard(session);
                }
                return input.hasRemaining();
            }

            input.reset();
            if (input.remaining() <= 4) {
                return true;
            }

            input.mark();// 标记当前位置，以便reset
            int size = input.getInt();// 读取前4字节

            if (size > input.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
                input.reset();
                return false;
            }

            byte[] b = new byte[size];
            input.get(b);
            NetParentOld.NetCommond commond = NetParentOld.NetCommond.parseFrom(b);
            int cmd_c_old = commond.getCmd();
            int mod = cmd_c_old / 1000;
            int cmd = cmd_c_old - mod * 1000;

            int cmd_c = CMDManager.getCmd_M(mod,cmd);
            CmdModule cmdModule = CMDManager.getIntance().getCmdModule(cmd_c);
            if(cmdModule == null){
                PrintTool.error("not find module:"+mod+"  cmd:"+cmd);
                return input.hasRemaining();
            }

            UserVistor vistor = (UserVistor) session.getAttribute(SocketConstant.SessionKey.vistorKey);
            if(!cmdModule.isRequireOnline()){
                out.write(cmdModule.getRequset(commond.getObj().toByteArray(),cmd_c,commond.getSeq()));
                return input.hasRemaining();
            }

            if(vistor.getUid() <= 0){
                PrintTool.error("not find module:"+mod+"  cmd:"+cmd);
                session.write(getError(ResponseCode.Error.login_timeout,commond.getSeq(),cmd_c_old));
                session.closeNow();
                return false;//父类接收新数据
            }
            byte[] data = commond.getObj().toByteArray();
            Request request = cmdModule.getRequset(data,cmd_c,commond.getSeq());

            //检验是否正确
            String key = OnlineKeyManager.getIntance().getUserById(vistor.getUid());
            if(key == null || !MD5Tool.GetMD5Code(Tools.getByteJoin(data, key.getBytes())).equals(commond.getSn())){
                PrintTool.error("can not find uid:"+vistor.getUid()+" 的 Key:"+key);
                session.write(getError(ResponseCode.Error.key_error,commond.getSeq(),cmd_c_old));
                session.closeNow();
                return false;//父类接收新数据
            }

            out.write(request);
            return input.hasRemaining();
        }
        return false;
    }

    @Override
    public void doRequeset(IoSession session, ProtocolDecoderOutput out,ReceiveData receiveData) throws IOException {
    }


    private Response getError(ResponseCode.Error error,int seq,int cmd_c_old){
        Response res = Response.defaultResponse(ResponseCode.getCodeValue(error));
        res.setSeq(seq);
        res.setM_cmd(cmd_c_old);
        return res;
    }
}
