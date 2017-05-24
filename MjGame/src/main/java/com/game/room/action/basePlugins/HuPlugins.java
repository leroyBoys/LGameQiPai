package com.game.room.action.basePlugins;

import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.ChiAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class HuPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {

        return false;
    }


    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public HuPlugins createNew() {
        return new HuPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        MjChairInfo chair = table.getChairByUid(roleId);
        StepGameStatusData lastStep = (StepGameStatusData) chair.getTableVo().getStepHistoryManager().getLastStep();

        List<Integer> cards = new LinkedList<>();
        final int lastCard = lastStep.getCards().get(0);
        for(Integer card:stepGameStatusData.getCards()){
            cards.add(card);
            if(card == lastCard){
                continue;
            }

            chair.getHandsContainer().removeCardFromHand(card,1);
        }

        MjCardPoolEngine mjCardPoolEngine = table.getCardPool();
        mjCardPoolEngine.removeLastCard();

        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);
        return true;
    }

    @Override
    public int chickMatch(T table,List<Integer> card, StepGameStatusData stepData) {
        return 1;
    }
}
