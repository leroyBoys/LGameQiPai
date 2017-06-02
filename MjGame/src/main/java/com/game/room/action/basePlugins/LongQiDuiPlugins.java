package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.room.action.HuAction;
import com.game.room.util.MJTool;

/**
 * 一色+七对
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class LongQiDuiPlugins extends IPluginHuCheck{
    /**
     *
     * @return
     */
    @Override
    public int getWeight() {
        return 10;
    }

    @Override
    public LongQiDuiPlugins createNew() {
        return new LongQiDuiPlugins();
    }

    @Override
    public boolean checkExecute(HuAction.HuType huType, int[][] cardsType,BaseChairInfo chair) {
        if(huType != HuAction.HuType.QIDUI){
            return false;
        }
        //待完成
        return MJTool.oneCorlor(chair.getHandsContainer().getHandCards(),null);
    }
}
