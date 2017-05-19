package com.game.core.chat;

import com.game.socket.module.UserVistor;
import com.module.ChannelType;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/19.
 */
public interface ChatFilter {
    /**
     *  只用于前台发送聊天系统
     * @param type
     * @param receiveUid 接收uid
     * @param msg
     * @param isActionEffect 是否是特效
     */
    public boolean sendMsg(ChannelType type, UserVistor vistor, int receiveUid, String msg, int isActionEffect);

    /**
     *  发送系统消息
     * @param type
     * @param receiveUid
     * @param msg
     */
    public boolean sendSystemMsg(ChannelType type, int receiveUid, String msg);
    /**
     * 给玩家发送系统消息
     * @param uid
     * @param msg
     */
    public boolean sendSystemMsg(int uid, String msg);

}
