package com.game.socket.module;

import com.game.manager.DBServiceManager;
import com.game.manager.UpdateData;
import com.game.manager.UpdateDataManager;
import com.logger.type.LogType;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/10.
 */
public class GameRole extends UpdateData{

    private final int roleId;
    private int roomId;
    private volatile boolean roomIdUpdate = false;

    private int card;
    private volatile boolean cardUpdate = false;
    public GameRole(int roleId,int roomId,int card){
        super("");
        this.roleId = roleId;
        this.roomId = roomId;
        this.card = card;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
        roomIdUpdate = true;
        this.updateIng();
    }

    protected void updateIng(){
        super.updateIng();
        UpdateDataManager.getInstance().addUpdataData(roleId);
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
        cardUpdate = true;
        this.updateIng();
    }

    @Override
    public void update() {
        if(!isUpdate){
            return;
        }
        isUpdate = false;

        if(roomIdUpdate){
            roomIdUpdate = false;
            DBServiceManager.getInstance().getGameRedis().setRoomId(this.roleId,roomId);
        }

        if(cardUpdate){
            cardUpdate = false;
            DBServiceManager.getInstance().getUserService().updateMoney(this.roleId,card, LogType.Calculator,1);
            DBServiceManager.getInstance().getGameRedis().setCard(this.roleId,card);
        }
    }
}
