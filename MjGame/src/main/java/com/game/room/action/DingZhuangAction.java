package com.game.room.action;

import com.game.core.action.BaseAction;
import com.game.core.room.mj.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DingZhuangAction extends BaseAction<MjTable> {
    @Override
    public boolean isChangeToNextStatus(MjTable table) {
        return false;
    }

    @Override
    public void initAction(MjTable table) {
        doAction(table,null,null,null);
    }

}
