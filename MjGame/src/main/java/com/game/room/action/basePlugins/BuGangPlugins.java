package com.game.room.action.basePlugins;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.core.room.calculator.PayDetail;
import com.game.log.MJLog;
import com.game.room.*;
import com.game.room.action.GangAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.calculator.MjCalculator;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class BuGangPlugins<T extends MjTable> extends GangPlugins<T>{
    @Override
    public final boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {
        if(stepGameStatusData.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_MOPAI){
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

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        boolean isMatch = false;
        for(GroupCard goup:pengGoups){
            int cardNum = goup.getCards().get(0);
            if(!mjAutoCache.containCard(cardNum)){
                continue;
            }

            isMatch = true;
            gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(GangAction.getInstance(),chair.getId(),chair.getId(),cardNum,this));
        }

        return isMatch;
    }

    @Override
    public void createCanExecuteAction(T table, StepGameStatusData stepGameStatusData) {
        SuperGameStatusData statusData = table.getStatusData();
        int card = stepGameStatusData.getCards().get(0);
        for(int i = 0;i<table.getChairs().length;i++){
            if(table.getChairs()[i] == null || i == table.getFocusIdex()){
                continue;
            }
            statusData.checkHu(table.getChairs()[i],stepGameStatusData,card);
        }

        statusData.checkMo(table,stepGameStatusData.getUid());
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
        createCanExecuteAction(table,stepGameStatusData);

        PayDetail pay = payment(table,stepGameStatusData);
        pay.setPayType(PayDetail.PayType.ADD);

        ((MjCalculator)table.getCalculator()).setLastBuGang(pay);
        MJLog.play("补杠",cardNum,roleId,table);
        return true;
    }
}
