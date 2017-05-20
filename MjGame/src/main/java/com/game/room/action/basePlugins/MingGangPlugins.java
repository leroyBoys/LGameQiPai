package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class MingGangPlugins<T extends MjTable>  extends GangPlugins<T>{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public MingGangPlugins createNew() {
        return new MingGangPlugins();
    }

}
