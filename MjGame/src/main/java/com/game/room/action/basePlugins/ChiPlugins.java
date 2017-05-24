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
public class ChiPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int cNum, Object parems) {
        StepGameStatusData lastStep = (StepGameStatusData) chair.getTableVo().getStepHistoryManager().getLastStep();
        int fromId = lastStep.getUid();

        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        Map<Integer, Integer> countMap =  mjAutoCache.getCardNumMap();

        int step = chair.getTableVo().getStep();
        check(gameStatusData,countMap,cNum-2,cNum - 1,cNum,fromId,chair.getId(),step);
        check(gameStatusData,countMap,cNum-1,cNum,cNum + 1,fromId,chair.getId(),step);
        check(gameStatusData,countMap,cNum,cNum + 1,cNum + 2,fromId,chair.getId(),step);
        return false;
    }

    private void check(SuperGameStatusData gameStatusData,Map<Integer, Integer> countMap,int cardFirst,int cardSecond,int cardThree,int fromId,int roleId,int step){
        if (!countMap.containsKey(cardFirst) || !countMap.containsKey(cardSecond)) {
            return;
        }
        StepGameStatusData stepGameStatusData = new StepGameStatusData(ChiAction.getInstance(),fromId,roleId,cardFirst,this);
        stepGameStatusData.setCard(cardSecond);
        stepGameStatusData.setCard(cardThree);
        gameStatusData.addCanDoDatas(step,stepGameStatusData);
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public ChiPlugins createNew() {
        return new ChiPlugins();
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
        try{
            return card.get(0) == stepData.getCards().get(0) && card.get(1) == stepData.getCards().get(1)&&card.get(2) == stepData.getCards().get(2)?1:0;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }
}
