package com.game.core.action;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseGameStateData;
import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public abstract class IdleAction<T extends BaseTableVo> extends BaseAction <T> {

    public int getActionType(){
        return GameConst.ACTION_TYPE_READY;
    }

    @Override
    public void initAction(T table) {
        if(table.getCurChirCount() == 1){
            super.initAction(table);
        }
    }

    @Override
    public void doAction(T table, Response response, int roleId, NetGame.NetOprateData netOprateData) {
        this.ready(table,roleId);
    }

    protected void ready(T table,int roleId){
        BaseGameStateData readyStatus = table.getStatusData();

        if(readyStatus.contains(roleId)){
            return;
        }

        if(readyStatus.addDoneUid(roleId) == table.getChairs().length){
            readyStatus.setOver(true);
        }

        //发送数据
        NetGame.NetOprateData.Builder ready = table.getStatusData().getStatusDetail(table);
        ready.setUid(roleId);
        ready.setDval(1);
        table.addMsgQueueAll(ready.build(),0);

    }
    
    @Override
    public void overAction(BaseTableVo table) {
        table.cleanTableCache();
    }

    @Override
    public void tick(T table){
        for(int i = 0;i<table.getChairs().length;i++){
            if(table.getChairs()[i] == null || (!table.getChairs()[i].isRobot() && !table.getChairs()[i].isAuto())){
                continue;
            }
            this.doAction(table,null,table.getChairs()[i].getId(),null);
        }
    }
}
