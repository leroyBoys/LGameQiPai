package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjChairInfo;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;
import com.game.room.action.GangAction;
import com.game.room.action.MoAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class AnGangGangPlugins<T extends MjTable> extends GangPlugins<T>{
    @Override
    public final boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        if(card != 0){
            return false;
        }

        StepGameStatusData lastStep = (StepGameStatusData) chair.getTableVo().getStepHistoryManager().getLastStep();
        if(lastStep.getUid() != chair.getId()){
            return false;
        }

        return checkExecute(chair);
    }

    protected boolean checkExecute(BaseChairInfo chair){
        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        List<Integer> cards = mjAutoCache.getCardCountMap().get(4);
        if(cards == null){
            return false;
        }

        GangAction action = GangAction.getInstance();
        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        for(Integer cardNum:cards){
            gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(action,chair.getId(),chair.getId(),cardNum,this));
        }
        return true;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo table) {
        SuperGameStatusData statusData = (SuperGameStatusData) table.getStatusData();
        statusData.checkMo(table);
    }

    @Override
    public AnGangGangPlugins createNew() {
        return new AnGangGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        if (stepGameStatusData.getiOptPlugin().getPlugin().getSubType() != this.getPlugin().getSubType()) {
            return false;
        }

        MjChairInfo chair = table.getChairByUid(roleId);

        final int cardNum = stepGameStatusData.getCards().get(0);
        MjHandCardsContainer handCardsContainer = chair.getHandsContainer();
        handCardsContainer.removeCardFromHand(cardNum,4);

        List<Integer> cards = new LinkedList<>();
        cards.add(cardNum);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);
        createCanExecuteAction(table);

        playLog.info("angang:roleId:"+roleId+" card:"+cardNum+" "+ Arrays.toString(table.getChairByUid(roleId).getHandsContainer().getHandCards().toArray()));
        return true;
    }

}
