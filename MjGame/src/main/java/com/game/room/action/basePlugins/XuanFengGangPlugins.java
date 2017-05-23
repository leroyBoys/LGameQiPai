package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjTable;
import com.game.room.action.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class XuanFengGangPlugins<T extends MjTable> extends AnGangGangPlugins<T> {
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public XuanFengGangPlugins createNew() {
        return new XuanFengGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        int card = (int) table.getCardPool().getRemainCards().remove(0);
        table.getChairByUid(roleId).getHandsContainer().addHandCards(card);
        stepGameStatusData.setCard(card);
        return true;
    }
}
