package com.game.core.action;

import com.game.socket.module.UserVistor;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseChairStatus;
import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public abstract class IdleAction<T extends BaseTableVo> extends BaseAction <T> {
    @Override
    public final boolean isChangeToNextStatus(BaseTableVo table) {
        return false;
    }

    protected abstract <S extends BaseChairStatus> S getReadyStatus();

    @Override
    public void doAction(T table, Response response, UserVistor visitor, NetGame.NetOprateData netOprateData) {
        this.ready(table,visitor.getRoleId());
    }

    @Override
    public void systemDoAction(T table, int roleId, Object paramter) {
       this.ready(table,roleId);
    }

    private void ready(T table,int roleId){
        BaseChairInfo chairInfo = table.getChairByUid(roleId);
        if(chairInfo.getStatus().getVal() == getReadyStatus().getVal()){
            return;
        }

        chairInfo.setStatus(getReadyStatus());
        if(table.getChairCountByStatus(chairInfo.getStatus()) == table.getChairs().length){
            table.getStatusData().setOver(true);
        }

        //发送数据
        table.sendChairStatusMsgWithOutUid(roleId);
    }
    
    @Override
    public void overAction(BaseTableVo table) {
        table.cleanTableCache();
    }
}
