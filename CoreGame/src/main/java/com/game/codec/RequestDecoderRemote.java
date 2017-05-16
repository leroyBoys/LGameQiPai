package com.game.codec;

import com.game.manager.TimeCacheManager;
import com.game.socket.module.UserVistor;
import com.lgame.util.comm.Tools;
import com.lgame.util.encry.MD5Tool;
import com.lgame.util.encry.ZipTool;
import com.logger.log.SystemLogger;
import com.lsocket.codec.RequestDecoder;
import com.lsocket.handler.CmdModule;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.lsocket.module.HttpRequestType;
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
    private HttpRequestType httpRequestType = HttpRequestType.tcp;
    public RequestDecoderRemote(){
    }

    public RequestDecoderRemote(HttpRequestType httpRequestType){
        this.httpRequestType = httpRequestType;
    }
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
            int module = CMDManager.getModule(cmd_c);
            int cmd = CMDManager.getCmd(cmd_c);

            CmdModule cmdModule = CMDManager.getIntance().getCmdModule(cmd_c);
            if(cmdModule == null){
                SystemLogger.info(RequestDecoderRemote.class,"not find module:"+module+"  cmd:"+cmd);
                return input.hasRemaining();
            }else if(httpRequestType != cmdModule.getModuleCmd().getRequetType()){
                SystemLogger.info(RequestDecoderRemote.class,"module:"+module+"  cmd:"+cmd+" shold be "+cmdModule.getModuleCmd().getRequetType());
                return input.hasRemaining();
            }
            SystemLogger.info(RequestDecoderRemote.class,"============================receiver:"+commond.toString());

            TimeCacheManager.getInstance().setCurTime(System.currentTimeMillis());
            UserVistor vistor = (UserVistor) session.getAttribute(SocketConstant.SessionKey.vistorKey);
            if(!cmdModule.getModuleCmd().isRequireOnline()){
                Request request = cmdModule.getRequset(getData(commond),module,cmd,commond.getSeq());
                request.addAttribute("sn",commond.getSn());
                request.addAttribute("bytes",commond.getObj().toByteArray());
                out.write(request);
                return input.hasRemaining();
            }

            if(vistor.getUid() <= 0){
                session.write(getError(ResponseCode.Error.login_timeout,commond.getSeq(),module,cmd));
                session.closeNow();
                return false;//父类接收新数据
            }

            //检验是否正确
            DB.UK key = vistor.getUk();

            byte[] data = null;
            if(commond.getObj() != null){//秘钥验证

                data = commond.getObj().toByteArray();
                if(key == null || !MD5Tool.GetMD5Code(Tools.getByteJoin(data, key.getKey().getBytes())).equals(commond.getSn())){
                    SystemLogger.info(RequestDecoderRemote.class,"can not find uid:"+vistor.getUid()+" 的 Key:"+(key==null?"null":key.toString()));
                    session.write(getError(ResponseCode.Error.key_error,commond.getSeq(),module,cmd));
                    session.closeNow();
                    return false;//父类接收新数据
                }

                data = getData(commond);//解压缩
            }

            vistor.setModule(CMDManager.getModule(cmd_c));
            Request request = cmdModule.getRequset(data,module,cmd,commond.getSeq());

            if(request.getObj() != null){
                SystemLogger.info(RequestDecoderRemote.class,"====rece222iver:"+request.getObj().toString());
            }
            out.write(request);
            return input.hasRemaining();
        }
        return false;
    }

    private byte[] getData(NetParentOld.NetCommond commond) throws IOException {
        byte[] data = commond.getObj().toByteArray();
        return ZipTool.uncompressBytes(data);//解压缩
    }

    @Override
    public void doRequeset(IoSession session, ProtocolDecoderOutput out,ReceiveData receiveData) throws IOException {
    }


    private Response getError(ResponseCode.Error error,int seq,int module,int cmd){
        Response res = Response.defaultResponse(module,cmd,seq);
        res.setStatus(ResponseCode.getCodeValue(error));
        return res;
    }
}
