package com.game.core.admin;

import com.game.core.chat.ChatFilter;
import com.game.socket.module.UserVistor;
import com.lgame.util.comm.StringTool;
import com.module.ChannelType;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/19.
 */
public class AdminCmdFilter implements ChatFilter {
    private static final char cmdPrefix = '!';
    private static final String cmdDiffChar = ":";
    private static AdminCmdFilter ourInstance = new AdminCmdFilter();
    public static AdminCmdFilter getInstance() {
        return ourInstance;
    }
    private AdminCmdFilter() {
    }

    public void load(){
    }

    private boolean isAdmin(ChannelType type,String content){
        if(ChannelType.room != type){
            return false;
        }

        if(StringTool.isEmpty(content) || content.length() < 2 || content.charAt(0) != cmdPrefix){
            return false;
        }

        return true;
    }

    @Override
    public boolean sendMsg(ChannelType type, UserVistor vistor, int receiveUid, String content, int isActionEffect) {
        if(!isAdmin(type,content)){
            return false;
        }

        content = content.substring(1);
        String[] cmdStrArray = content.split(cmdDiffChar);
        String cmd = cmdStrArray[0];

        AdminCmdHandler.Handler adminHandler = AdminCmdHandler.Handler.valueOf(cmd);
        if(adminHandler == null){
            return false;
        }

        adminHandler.getDispatch().dispatch(content,vistor);
        return true;
    }


    @Override
    public boolean sendSystemMsg(ChannelType type, int receiveUid, String msg) {
        return false;
    }
    @Override
    public boolean sendSystemMsg(int uid, String msg) {
        return false;
    }

    public void ready(String content, UserVistor vistor) {
    }
}
