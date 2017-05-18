package com.game.core.room;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface IMessageQueue<M> {
    public void addMsg(M m,int seq);

    public void addMsgList(List<M> mList,int seq);

    public M getMsg();

    public List<M> getMsgs();

    public void sendNow(int seq);
}
