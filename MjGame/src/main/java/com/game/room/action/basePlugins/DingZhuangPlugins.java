package com.game.room.action.basePlugins;

import com.game.core.config.AbstractStagePlugin;
import com.game.core.config.IOptPlugin;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.Arrays;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class DingZhuangPlugins<T extends MjTable> extends AbstractStagePlugin<T> {
    @Override
    public IOptPlugin createNew() {
        return new DingZhuangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response,int roleId, NetGame.NetOprateData oprateData) {
        if(table.getNextBankerUid() <= 0){
            table.setNextBankerUid(table.getChairs()[0].getId());
        }

        table.setBankId(table.getNextBankerUid());

        table.getStatusData().setOver(true);

        playLog.info("定庄:bankId:"+table.getBankId());
        return true;
    }

}
