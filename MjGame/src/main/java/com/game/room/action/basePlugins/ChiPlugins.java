package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.log.MJLog;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.ChiAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class ChiPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction<T,StepGameStatusData>{
    @Override
    public boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {
        int fromId = stepGameStatusData.getUid();

        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();

        int step = chair.getTableVo().getStep();
        check(gameStatusData,mjAutoCache,card-2,card - 1,fromId,chair.getId(),step);
        check(gameStatusData,mjAutoCache,card-1,card + 1,fromId,chair.getId(),step);
        check(gameStatusData,mjAutoCache,card + 1,card + 2,fromId,chair.getId(),step);
        return false;
    }

    private void check(SuperGameStatusData gameStatusData,MjAutoCacheHandContainer mjAutoCache,int cardFirst,int cardSecond,int fromId,int roleId,int step){
        if (!mjAutoCache.containCard(cardFirst) || !mjAutoCache.containCard(cardSecond)) {
            return;
        }

        StepGameStatusData stepGameStatusData = new StepGameStatusData(ChiAction.getInstance(),fromId,roleId,cardFirst,this);
        stepGameStatusData.setCard(cardSecond);
        gameStatusData.addCanDoDatas(step,stepGameStatusData);
    }

    @Override
    public void createCanExecuteAction(T table,StepGameStatusData stepGameStatusData) {
        MjChairInfo info = table.getChairByUid(stepGameStatusData.getUid());
        SuperGameStatusData gameStatusData= table.getStatusData();
        gameStatusData.checkGang(info,stepGameStatusData,0);
        gameStatusData.checkTing(info,stepGameStatusData);
        gameStatusData.checkDa(info);
    }

    @Override
    public ChiPlugins createNew() {
        return new ChiPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        MjChairInfo chair = table.getChairByUid(roleId);

        List<Integer> cards = new LinkedList<>();
        for(Integer card:stepGameStatusData.getCards()){
            cards.add(card);
            chair.getHandsContainer().removeCardFromHand(card,1);
        }

        MjCardPoolEngine mjCardPoolEngine = table.getCardPool();
        cards.add(mjCardPoolEngine.removeLastCard());

        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);
        createCanExecuteAction(table,stepGameStatusData);

        MJLog.play("吃",Arrays.toString(cards.toArray()),roleId,table);
        return true;
    }

    @Override
    public int chickMatch(T table,List<Integer> card, StepGameStatusData stepData) {
        try{
            return card.get(0) == stepData.getCards().get(0) && card.get(1) == stepData.getCards().get(1)?1:0;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }
}
