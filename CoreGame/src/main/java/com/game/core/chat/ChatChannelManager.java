/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.core.chat;


import com.game.core.admin.AdminCmdFilter;
import com.game.core.config.GameSwitch;
import com.game.core.config.GameSwitchManager;
import com.game.socket.module.UserVistor;
import com.module.ChannelType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leroy
 */
public class ChatChannelManager implements ChatFilter{
    private final List<ChatFilter> filters;
    private final static ChatChannelManager instance = new ChatChannelManager();
    private ChatChannelManager(){
        filters = new ArrayList<>(2);
        filters.add(new ChannelServiceImpl());

        if(GameSwitchManager.getInstance().isOpen(GameSwitch.Type.Admin)){
            filters.add(AdminCmdFilter.getInstance());
        }
    }

    public static final ChatChannelManager getInstance(){
        return instance;
    }

    @Override
    public boolean sendMsg(ChannelType type, UserVistor vistor, int receiveUid, String msg, int isActionEffect) {
        for(int i = 0;i<filters.size();i++){
            if(filters.get(i).sendMsg(type,vistor,receiveUid,msg,isActionEffect)){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean sendSystemMsg(ChannelType type, int receiveUid, String msg) {
        for(int i = 0;i<filters.size();i++){
            if(filters.get(i).sendSystemMsg(type,receiveUid,msg)){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean sendSystemMsg(int uid, String msg) {
        for(int i = 0;i<filters.size();i++){
            if(filters.get(i).sendSystemMsg(uid,msg)){
                return false;
            }
        }
        return false;
    }
}
