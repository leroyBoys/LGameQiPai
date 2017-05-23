package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class SiFengGangPlugins<T extends MjTable> extends AnGangGangPlugins<T> {
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public SiFengGangPlugins createNew() {
        return new SiFengGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        int card = (int) table.getCardPool().getRemainCards().remove(0);
        table.getChairByUid(roleId).getHandsContainer().addHandCards(card);
        stepGameStatusData.setCard(card);
        return true;
    }
}
