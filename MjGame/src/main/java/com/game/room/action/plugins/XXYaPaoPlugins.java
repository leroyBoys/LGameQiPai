package com.game.room.action.plugins;

import com.game.core.config.AbstractStagePlugin;
import com.game.core.config.IOptPlugin;
import com.game.core.room.BaseStatusData;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.logger.log.SystemLogger;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class XXYaPaoPlugins extends AbstractStagePlugin<MjTable> {
    @Override
    public IOptPlugin createNew() {
        return new XXYaPaoPlugins();
    }

    @Override
    public synchronized Boolean doOperation(MjTable table, Response response, NetGame.NetOprateData oprateData) {
        MjChairInfo info = table.getChairByUid(oprateData.getUid());
        if(!info.isCanYaPao()){
            SystemLogger.warn(this.getClass(),"yapao fail roleId:"+oprateData.getUid()+" yapao:"+info.isCanYaPao());
            return false;
        }

        BaseStatusData.DefaultStatusData statusData = table.getStatusData();
        int size = statusData.addDoneUid(oprateData.getUid());
        if(size <= 0){
            SystemLogger.warn(this.getClass(),"yapao fail roleId:"+oprateData.getUid()+" size:"+size);
            return false;
        }

        info.setYapaoNum(oprateData.getDval());

        if(size == table.getChairs().length){
            table.getStatusData().setOver(true);
        }
        return true;
    }

}
