package com.game.core.room;

import com.game.core.room.interfaces.IMessageQueue;
import com.game.socket.module.UserVistor;
import com.module.net.NetGame;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
public class MessageQueue implements IMessageQueue<NetGame.NetOprateData> {
    private final BaseTableVo tableVo;
    private UserVistor vistor;
    private LinkedList<NetGame.NetOprateData> msgs = new LinkedList<>();
    public MessageQueue(UserVistor vistor,BaseTableVo tableVo){
        this.vistor = vistor;
        this.tableVo = tableVo;
    }

    public void setVistor(UserVistor vistor) {
        this.vistor = vistor;
    }

    @Override
    public synchronized void addMsg(NetGame.NetOprateData netOprateData,int seq) {
        if(vistor == null){
            return;
        }

        if(tableVo.isMsgCache){
            msgs.add(netOprateData);
            return;
        }
        tableVo.sendGameResponse(netOprateData,vistor,seq);
    }

    @Override
    public synchronized void addMsgList(List<NetGame.NetOprateData> netOprateDatas,int seq) {
        if(vistor == null){
            return;
        }

        if(tableVo.isMsgCache){
            msgs.addAll(netOprateDatas);
            return;
        }
        tableVo.sendGameResponse(netOprateDatas,vistor,seq);

    }

    @Override
    public synchronized NetGame.NetOprateData getMsg() {
        return null;
    }

    @Override
    public synchronized List<NetGame.NetOprateData> getMsgs() {
        return null;
    }

    public UserVistor getVistor() {
        return vistor;
    }

    @Override
    public synchronized void sendNow(int seq) {
        if(msgs.isEmpty()){
            return;
        }
        tableVo.sendGameResponse(msgs,vistor,seq);
        msgs = new LinkedList<>();
    }
}
