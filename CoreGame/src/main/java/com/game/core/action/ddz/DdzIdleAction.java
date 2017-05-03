package com.game.core.action.ddz;


import com.game.core.action.IdleAction;
import com.game.core.room.ddz.DDzTable;
import com.game.core.room.ddz.DdzChairStatus;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DdzIdleAction extends IdleAction<DDzTable> {

    @Override
    protected DdzChairStatus getReadyStatus() {
        return DdzChairStatus.ready;
    }
}
