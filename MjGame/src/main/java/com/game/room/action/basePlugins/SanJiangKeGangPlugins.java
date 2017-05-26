package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjChairInfo;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;
import com.game.room.action.GangAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 中发白+任意中发白
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class SanJiangKeGangPlugins<T extends MjTable> extends AnGangGangPlugins<T> {
    @Override
    protected boolean checkExecute(BaseChairInfo chair) {
        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        if (!mjAutoCache.containCard(45) || !mjAutoCache.containCard(46) || !mjAutoCache.containCard(47)){
            return false;
        }
        if((mjAutoCache.getCardCount(45)+mjAutoCache.getCardCount(46)+mjAutoCache.getCardCount(47))<4){
            return false;
        }

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();

        final int step = chair.getTableVo().getStep();

        checkCard(mjAutoCache,gameStatusData,45,chair.getId(),step);
        checkCard(mjAutoCache,gameStatusData,46,chair.getId(),step);
        checkCard(mjAutoCache,gameStatusData,47,chair.getId(),step);
        return true;
    }

    private void checkCard(MjAutoCacheHandContainer mjAutoCache,SuperGameStatusData gameStatusData,int card,int roleId,int step){
        if(mjAutoCache.getCardCount(card) < 2){
            return;
        }

        StepGameStatusData stepGameStatusData = new StepGameStatusData(GangAction.getInstance(),roleId,roleId,card,this);
        stepGameStatusData.setCard(card);
        gameStatusData.addCanDoDatas(step,stepGameStatusData);
    }

    @Override
    public SanJiangKeGangPlugins createNew() {
        return new SanJiangKeGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        if (stepGameStatusData.getiOptPlugin().getPlugin().getSubType() != this.getPlugin().getSubType()) {
            return false;
        }

        MjChairInfo chair = table.getChairByUid(roleId);
        MjHandCardsContainer handCardsContainer = chair.getHandsContainer();

        int extraCardNum = stepGameStatusData.getCards().get(0);
        handCardsContainer.removeCardFromHand(45, extraCardNum == 45?2:1);
        handCardsContainer.removeCardFromHand(46, extraCardNum == 46?2:1);
        handCardsContainer.removeCardFromHand(47, extraCardNum == 47?2:1);

        List<Integer> cards = new LinkedList<>();
        cards.add(extraCardNum);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);

        createCanExecuteAction(table);
        return true;
    }

}
