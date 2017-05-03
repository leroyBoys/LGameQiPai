package com.game.room.action;

import com.game.core.action.IdleAction;
import com.game.core.room.mj.MjChairStatus;
import com.game.core.room.mj.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MjIdleAction extends IdleAction<MjTable> {

    @Override
    protected MjChairStatus getReadyStatus() {
        return MjChairStatus.ready;
    }

}
