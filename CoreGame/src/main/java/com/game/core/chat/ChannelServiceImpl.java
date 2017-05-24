/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.core.chat;


import com.game.Handler.ChatCmd;
import com.game.core.TableManager;
import com.game.core.constant.GameConst;
import com.game.core.room.BaseTableVo;
import com.game.manager.OnlineManager;
import com.game.socket.module.UserVistor;
import com.game.util.SensitiveUtil;
import com.lgame.util.comm.StringTool;
import com.lsocket.message.Response;
import com.module.ChannelType;
import com.module.Status;
import com.module.net.NetGame;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author leroy
 */
public class ChannelServiceImpl implements ChatFilter {
    private static final Set<ChannelType> saveChannelTypeMap = new HashSet<>();
    
    private static final int SystemUid = 0;
    private static final String SystemName = "[系统]";
    static{
        saveChannelTypeMap.add(ChannelType.world);
    }

    @Override
    public boolean sendMsg(ChannelType type, UserVistor vistor, int pid, String msg, int isActionEffect) {

        String userName = "";
        if (vistor != null) {

            if (vistor.getRoleInfo().getUserStatus() == Status.UserStatus.gag) {
                // SendMsg.sendErrorMsg(response.getSeq(), uid, ResponseCode.Error.cantNotChat);
                sendSystemMsg(pid, "你的账号已禁言");
                return true;
            }
            if(type == ChannelType.privateChat){
                if (vistor.getRoleId() == pid) {
                    sendSystemMsg(vistor.getRoleId(), "不能给自己发送消息！");
                    return true;
                }
            }
            userName = vistor.getRoleInfo().getUserAlise();
        }
        ///检测扣除道具情况
        
        if (isActionEffect == 0) {
            msg = valiaContent(msg);
            if (msg == null) {
                return true;
            }
            //存数据库
            saveToDb(type, vistor.getRoleId(), pid, msg);
        }
        _sendChannelMsg(type, vistor.getRoleId(), userName, pid, msg, isActionEffect);
        return true;
    }
    private void saveToDb(ChannelType type, int uid, int pid, String msg){
     
    }

    private final String charFilter(String str) {
        return str.replaceAll("\\[|\\]|【|】|\\{|\\}|!|！", "*");
    }

    /**
     * 校验过了聊天内容并重新封装
     *
     * @param str
     * @return
     */
    public String valiaContent(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        String reg = "\\{.*?\\}";
        Pattern pattern = Pattern.compile(reg);
        StringBuilder sb = new StringBuilder();
        String[] contents = pattern.split(str);
        if (contents.length == 1) {
            return SensitiveUtil.replace(charFilter(str));
        }

        Matcher matcher = pattern.matcher(str);
        String[] items = new String[contents.length - 1];
        int index = 0;
        while (matcher.find()) {
            String itemStr = matcher.group();
            items[index++] = itemStr;
        }
        for (int i = 0; i < contents.length; i++) {
            //    System.out.println(contents[i]);
            sb.append(SensitiveUtil.replace(charFilter(contents[i])));
            if (i != contents.length - 1) {
                sb.append(items[i]);
            }
        }

        ///  System.out.println("替换后的字符串：" + sb.toString());
        return sb.toString();
    }

    @Override
    public boolean sendSystemMsg(ChannelType type, int pid, String msg) {
        int uid = SystemUid;//系统
        String userName = SystemName;
        _sendChannelMsg(type, uid, userName, uid, msg, 0);
        return true;
    }

    private void _sendChannelMsg(ChannelType type, int sendUid, String sendUserName, int receiveUid, String msg, int isActionEffect) {
        switch (type) {
            case world:
                sendMsgToWorld(sendUid, sendUserName, msg, isActionEffect);
                break;
            case room:
                sendMsgToRoom(sendUid, sendUserName, receiveUid, msg, isActionEffect);
                break;
            case group:
                sendMsgToGroup(sendUid, sendUserName, receiveUid, msg, isActionEffect);
                break;
            case privateChat:
                sendMsgToOther(sendUid, sendUserName, receiveUid, msg);
                break;
            case teamChat:
                break;
            case broadCast:
                sendMsgToBroadCast(sendUid, sendUserName, msg);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean sendSystemMsg(int uid, String msg) {
        sendSystemMsg(ChannelType.privateChat, uid, msg);
        return true;
    }

    private void sendMsg(Response response,int roleId){
        UserVistor vistor = OnlineManager.getIntance().getRoleId(roleId);
        if(vistor == null){
            return;
        }
        vistor.sendMsg(response);
    }

    private void sendMsgToOther(int sendUid, String userName, int pid, String msg) {
       /* Response response = Response.defaultResponse(GameConst.MOUDLE_Chat, ChatCmd.Chat.getValue(), 0);
        response.setObj(new SEChat(sendUid, userName, ChannelType.world.getType(), msg));
        this.sendMsg(response,sendUid);*/
    }

    private void sendMsgToWorld(int sendUid, String userName, String msg, int isActionEffect) {
        //不跨服
       /* Response response = Response.defaultResponse(GameConst.MOUDLE_Chat, ChatCmd.Chat.getValue(), 0);
        response.setObj(new SEChat(sendUid, userName, ChannelType.world.getType(), msg, isActionEffect));
        SendMsg.sendMsgAllOnline(response);*/
    }

    private NetGame.NetChat.Builder getChat(int sendUid, String userName, int channel, String content) {
        NetGame.NetChat.Builder chat = getChat(sendUid,userName,channel,content,0);
        return chat;
    }

    private NetGame.NetChat.Builder getChat(int sendUid, String userName, int channel, String content, int isaction) {
        NetGame.NetChat.Builder chat = NetGame.NetChat.newBuilder();
        chat.setReceiveId(sendUid);
        if(StringTool.isNotNull(userName)){
            chat.setUserName(userName);
        }
        chat.setChannel(channel);
        if(StringTool.isNotNull(content)){
            chat.setContent(content);
        }

        return chat;
    }

    private void sendMsgToRoom(int sendUid, String sendUserName, int receiveUid, String msg, int actionEffect) {
        UserVistor vistor = OnlineManager.getIntance().getRoleId(sendUid);
        if(vistor == null){
            return;
        }

        BaseTableVo tableVo = TableManager.getInstance().getTable(vistor.getGameRole().getRoomId());
        if(tableVo == null){
            return;
        }

        Response response = Response.defaultResponse(GameConst.MOUDLE_Chat, ChatCmd.Chat.getValue(), 0);
        response.setObj(getChat(sendUid, sendUserName, ChannelType.world.getType(), msg, actionEffect).build());
        for(int i=0;i<tableVo.getChairs().length;i++){
            if(tableVo.getChairs()[i] == null){
                continue;
            }

            vistor = OnlineManager.getIntance().getRoleId(tableVo.getChairs()[i].getId());
            if(vistor == null){
                continue;
            }

            vistor.sendMsg(response);
        }
    }

    private void sendMsgToGroup(int sendUid, String sendUserName, int receiveUid, String msg, int actionEffect) {
    }

    private void sendMsgToBroadCast(int sendUid, String sendUserName, String msg) {
        //不跨服
      /*  Response response = Response.defaultResponse(GameConst.MOUDLE_Chat, ChatCmd.Chat.getValue(), 0);
        response.setObj(new SEChat(sendUid, sendUserName, ChannelType.world.getType(), msg));
        SendMsg.sendMsgAllOnline(response);*/
    }

}
