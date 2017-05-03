package com.game.core.action;

import com.game.core.UserVistor;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseChairStatus;
import com.game.core.room.BaseTableVo;
import com.lsocket.message.Response;
import com.module.net.NetCommon;

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
    public void doAction(T table, Response response, UserVistor visitor, NetCommon.NetOprateData netOprateData) {
        BaseChairInfo chairInfo = table.getChairByUid(visitor.getUid());
        if(chairInfo.getStatus().getVal() == getReadyStatus().getVal()){
            return;
        }

        chairInfo.setStatus(getReadyStatus());
        if(table.getChairCountByStatus(chairInfo.getStatus()) == table.getChairs().length){
            table.getStatusData().setOver(true);
        }

        //发送数据

        //visitor.getIoSession()
    }

    @Override
    public void overAction(BaseTableVo table) {
        table.cleanTableCache();
    }
}
