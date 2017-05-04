package com.game.codec;

import com.game.core.service.UserService;
import com.game.manager.DBServiceManager;
import com.game.manager.OnlineKeyManager;
import com.game.manager.OnlineManager;
import com.game.socket.module.UserVistor;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.lgame.util.encry.ZipTool;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.handler.CmdModule;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.util.DefaultSocketPackage;
import com.lsocket.util.ReceiveData;
import com.lsocket.util.SocketConstant;
import com.module.core.ResponseCode;
import com.module.net.DB;
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

    protected boolean doDecode(IoSession session, IoBuffer input, ProtocolDecoderOutput out) throws Exception {
        int remainSize = input.remaining();
        if (remainSize > 0) {
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
            int cmd_c = commond.getCmd();

            CmdModule cmdModule = CMDManager.getIntance().getCmdModule(cmd_c);
            if(cmdModule == null){
                PrintTool.error("not find module:"+CMDManager.getModule(cmd_c)+"  cmd:"+CMDManager.getCmd(cmd_c));
                return input.hasRemaining();
            }

            UserVistor vistor = (UserVistor) session.getAttribute(SocketConstant.SessionKey.vistorKey);
            if(!cmdModule.isRequireOnline()){
                Request request = cmdModule.getRequset(commond.getObj().toByteArray(),cmd_c,commond.getSeq());
                request.addAttribute("sn",commond.getSn());
                out.write(request);
                return input.hasRemaining();
            }

            if(vistor.getUid() <= 0){
                session.write(getError(ResponseCode.Error.login_timeout,commond.getSeq(),cmd_c));
                session.closeNow();
                return false;//父类接收新数据
            }

            //检验是否正确
            UserService userService = DBServiceManager.getDbServiceManager().getUserService();
            DB.UK key = userService.getUserKey(vistor.getUid(),false);

            byte[] data = null;
            if(commond.getObj() != null){//秘钥验证

                data = commond.getObj().toByteArray();
                if(key == null || !MD5Tool.GetMD5Code(Tools.getByteJoin(data, key.toByteArray())).equals(commond.getSn())){
                    PrintTool.error("can not find uid:"+vistor.getUid()+" 的 Key:"+(key==null?"null":key.toString()));
                    session.write(getError(ResponseCode.Error.key_error,commond.getSeq(),cmd_c));
                    session.closeNow();
                    return false;//父类接收新数据
                }

                data = ZipTool.uncompressBytes(data);//解压缩
            }

            vistor.setModule(CMDManager.getModule(cmd_c));
            Request request = cmdModule.getRequset(data,cmd_c,commond.getSeq());
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
