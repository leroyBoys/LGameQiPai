package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.calculator.PayDetail;
import com.game.log.MJLog;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.GangAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class MingGangPlugins<T extends MjTable>  extends GangPlugins<T>{
    @Override
    public boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {

        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        if(mjAutoCache.getCardCount(card) <3){
            return false;
        }

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(GangAction.getInstance(),stepGameStatusData.getUid(),chair.getId(),card,this));
        return false;
    }

    @Override
    public void createCanExecuteAction(T table, StepGameStatusData stepGameStatusData) {
        SuperGameStatusData statusData = table.getStatusData();
        statusData.checkMo(table,stepGameStatusData.getUid());
    }

    @Override
    public MingGangPlugins createNew() {
        return new MingGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        if (stepGameStatusData.getiOptPlugin().getPlugin().getSubType() != this.getPlugin().getSubType()) {
            return false;
        }

        MjChairInfo chair = table.getChairByUid(roleId);
        MjCardPoolEngine mjCardPoolEngine = table.getCardPool();

        final int lastCard = mjCardPoolEngine.removeLastCard();
        chair.getHandsContainer().removeCardFromHand(lastCard,3);
        List<Integer> cards = new LinkedList<>();
        cards.add(lastCard);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);

        PayDetail pay = payment(table,stepGameStatusData);
        pay.setPayType(PayDetail.PayType.ADD);
        this.createCanExecuteAction(table,stepGameStatusData);

        MJLog.play("明杠",lastCard,roleId,table);
        return true;
    }
}
