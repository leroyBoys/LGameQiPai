package com.game.room.action.plugins;

import com.game.room.MjTable;
import com.game.room.action.basePlugins.DingZhuangPlugins;
import com.lgame.util.comm.RandomTool;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class XXDingZhuangPlugins extends DingZhuangPlugins {
    @Override
    protected int getFirstBankId(MjTable table) {
        return table.getChairs()[RandomTool.Next(table.getChairs().length)].getId();
    }
}
