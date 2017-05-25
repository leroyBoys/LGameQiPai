package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.core.room.PayDetail;
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
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class MingGangPlugins<T extends MjTable>  extends GangPlugins<T>{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {

        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        Map<Integer, Integer> countMap =  mjAutoCache.getCardNumMap();
        Integer num = countMap.get(card);
        if(num == null || num <3){
            return false;
        }

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(GangAction.getInstance(),chair.getId(),chair.getId(),card,this));
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo table) {
        SuperGameStatusData statusData = (SuperGameStatusData) table.getStatusData();
        statusData.checkMo(table);
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
        StepGameStatusData lastStep = (StepGameStatusData) chair.getTableVo().getStepHistoryManager().getLastStep(-2);

        MjCardPoolEngine mjCardPoolEngine = table.getCardPool();
        mjCardPoolEngine.removeLastCard();

        final int lastCard = lastStep.getCards().get(0);
        chair.getHandsContainer().removeCardFromHand(lastCard,3);
        List<Integer> cards = new LinkedList<>();
        cards.add(lastCard);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);

        PayDetail pay = payment(stepGameStatusData);

        this.createCanExecuteAction(table);
        return true;
    }
}
