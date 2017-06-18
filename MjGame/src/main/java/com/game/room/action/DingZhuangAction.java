package com.game.room.action;

import com.game.core.action.BaseAction;
import com.game.core.constant.GameConst;
import com.game.room.MjTable;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DingZhuangAction extends BaseAction<MjTable> {

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_DINGZHUANG;
    }

    @Override
    public void initAction(MjTable table) {
        doAction(table,null,0,null);
    }

    @Override
    public void overAction(MjTable table) {
        NetGame.NetOprateData.Builder dingzhuang = NetGame.NetOprateData.newBuilder();
        dingzhuang.setOtype(this.getActionType());
        dingzhuang.setUid(table.getBankId());

        table.addMsgQueueAll(dingzhuang.build(),0);
    }
}
