package com.game.room.action.plugins;

import com.game.core.config.IOptPlugin;
import com.game.core.room.BaseStatusData;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetCommon;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class YaPaoPlugins implements IOptPlugin<MjTable> {
    @Override
    public IOptPlugin createNew() {
        return new YaPaoPlugins();
    }

    @Override
    public synchronized Object doOperation(MjTable table, Response response, NetCommon.NetOprateData oprateData) {
        MjChairInfo info = table.getChairByUid(oprateData.getUid());
        if(!info.isCanYaPao()){
            return null;
        }

        BaseStatusData.DefaultStatusData statusData = table.getStatusData();
        int size = statusData.addDoneUid(oprateData.getUid());
        if(size <= 0){
            return null;
        }

        info.setYapaoNum(oprateData.getDval());

        if(size == table.getChairs().length){
            table.getStatusData().setOver(true);
        }
        return null;
    }

}
