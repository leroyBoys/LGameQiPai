package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.calculator.PayDetail;
import com.game.log.MJLog;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.PengAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class PengPlugins<T extends MjTable> extends ChiPlugins<T>{
    @Override
    public boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {
        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        if(mjAutoCache.getCardCount(card) <2){
            return false;
        }

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(PengAction.getInstance(),stepGameStatusData.getUid(),chair.getId(),card,this));
        return true;
    }

    @Override
    public PengPlugins createNew() {
        return new PengPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        MjChairInfo chair = table.getChairByUid(roleId);

        MjCardPoolEngine mjCardPoolEngine = table.getCardPool();
        mjCardPoolEngine.removeLastCard();

        final int lastCard = stepGameStatusData.getCards().get(0);
        chair.getHandsContainer().removeCardFromHand(lastCard,2);
        List<Integer> cards = new LinkedList<>();
        cards.add(lastCard);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);

        PayDetail pay = payment(table,stepGameStatusData);

        MJLog.play("碰",lastCard,roleId,table);

        this.createCanExecuteAction(table,stepGameStatusData);

        return true;
    }

    @Override
    public int chickMatch(T table,List<Integer> card, StepGameStatusData stepData) {
        return 1;
    }
}
