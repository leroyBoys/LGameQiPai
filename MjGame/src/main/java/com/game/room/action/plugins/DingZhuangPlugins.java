package com.game.room.action.plugins;

import com.game.core.config.IOptPlugin;
import com.lsocket.message.Response;
import com.module.net.NetCommon;
import com.game.room.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class DingZhuangPlugins implements IOptPlugin<MjTable> {
    @Override
    public IOptPlugin createNew() {
        return new DingZhuangPlugins();
    }

    @Override
    public Object doOperation(MjTable table, Response response,NetCommon.NetOprateData oprateData) {
        if(table.getNextBankerUid() <= 0){
            table.setNextBankerUid(table.getChairs()[0].getId());
        }

        table.setBankId(table.getNextBankerUid());

        return null;
    }

}
