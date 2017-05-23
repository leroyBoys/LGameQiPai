package com.game.room.action.basePlugins;

import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjTable;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.Iterator;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class DaPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {

        int removeCard = stepGameStatusData.getCards().get(0);
        MjCardPoolEngine poolEngine = table.getCardPool();

        Iterator<Integer> it = table.getChairByUid(roleId).getHandsContainer().getHandCards().iterator();
        while (it.hasNext()) {
            Integer card = it.next();
            if (card == removeCard) {
                it.remove();
                poolEngine.playOutCard(roleId,card);
                break;
            }
        }

        return true;
    }

    @Override
    public DaPlugins createNew() {
        return new DaPlugins();
    }

}
