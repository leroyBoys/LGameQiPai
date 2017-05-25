package com.game.room.action.basePlugins;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.*;
import com.game.room.action.GangAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class BuGangPlugins<T extends MjTable> extends GangPlugins<T>{
    @Override
    public final boolean checkExecute(BaseChairInfo chair, int card, Object parems) {

        StepGameStatusData lastStep = (StepGameStatusData) chair.getTableVo().getStepHistoryManager().getLastStep();
        if(lastStep.getUid() != chair.getId() || lastStep.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_MOPAI){
            return false;
        }

        return checkExecute(chair);
    }

    protected boolean checkExecute(BaseChairInfo chair){
        MjHandCardsContainer mjHandCardsContainer = (MjHandCardsContainer) chair.getHandsContainer();
        List<GroupCard>  pengGoups = mjHandCardsContainer.getPengList();
        if(pengGoups == null || pengGoups.isEmpty()){
            return false;
        }

        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) mjHandCardsContainer.getAutoCacheHands();
        Map<Integer, Integer> cardNumMap =  mjAutoCache.getCardNumMap();

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        boolean isMatch = false;
        for(GroupCard goup:pengGoups){
            int cardNum = goup.getCards().get(0);
            if(!cardNumMap.containsKey(cardNum)){
                continue;
            }

            isMatch = true;
            gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(GangAction.getInstance(),chair.getId(),chair.getId(),cardNum,this));
        }

        return isMatch;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo table) {
        SuperGameStatusData statusData = (SuperGameStatusData) table.getStatusData();
        StepGameStatusData lastStep = (StepGameStatusData) table.getStepHistoryManager().getLastStep();
        int card = lastStep.getCards().get(0);
        for(int i = 0;i<table.getChairs().length;i++){
            if(table.getChairs()[i] == null || i == table.getFocusIdex()){
                continue;
            }
            statusData.checkHu((MjChairInfo) table.getChairs()[i],card);
        }

        statusData.checkMo(table);
    }

    @Override
    public BuGangPlugins createNew() {
        return new BuGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        if (stepGameStatusData.getiOptPlugin().getPlugin().getSubType() != this.getPlugin().getSubType()) {
            return false;
        }

        MjChairInfo chair = table.getChairByUid(roleId);
        final int cardNum = stepGameStatusData.getCards().get(0);
        MjHandCardsContainer handCardsContainer = chair.getHandsContainer();
        handCardsContainer.removeCardFromHand(cardNum,1);

        List<GroupCard> pengGoups = handCardsContainer.getPengList();//移除碰
        Iterator<GroupCard> iterators = pengGoups.iterator();
        while (iterators.hasNext()) {
            if (iterators.next().getCards().get(0) == cardNum) {
                iterators.remove();
                break;
            }
        }

        List<Integer> cards = new LinkedList<>();
        cards.add(cardNum);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);
        createCanExecuteAction(table);
        return true;
    }
}
