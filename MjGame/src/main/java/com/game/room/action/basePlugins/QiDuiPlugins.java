package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.room.action.HuAction;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class QiDuiPlugins extends IPluginHuCheck{
    /**
     *
     * @return
     */
    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public QiDuiPlugins createNew() {
        return new QiDuiPlugins();
    }

    @Override
    public boolean checkExecute(HuAction.HuType huType, int[][] cardsType,BaseChairInfo chair) {
        return huType == HuAction.HuType.QIDUI;
    }
}
