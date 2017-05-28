package com.game.room.action.basePlugins;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.core.room.calculator.PayDetail;
import com.game.log.MJLog;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjChairInfo;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;
import com.game.room.action.GangAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class AnGangGangPlugins<T extends MjTable> extends GangPlugins<T>{
    @Override
    public final boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {
        if(stepGameStatusData.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_MOPAI){
            return false;
        }

        return checkExecute(chair);
    }

    protected boolean checkExecute(BaseChairInfo chair){
        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        int targetCount = mjAutoCache.containCardCount(4);
        if(targetCount == 0){
            return false;
        }

        GangAction action = GangAction.getInstance();

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        for(Map.Entry<Integer,Integer> entry:mjAutoCache.getCardNumMap().entrySet()){
            if(entry.getValue() < 4){
                continue;
            }

            gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(action,chair.getId(),chair.getId(),entry.getKey(),this));
        }
        return true;
    }

    @Override
    public void createCanExecuteAction(T table,StepGameStatusData stepGameStatusData) {
        SuperGameStatusData statusData = table.getStatusData();
        statusData.checkMo(table,stepGameStatusData.getUid());
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

        PayDetail pay = payment(table,stepGameStatusData);
        pay.setPayType(PayDetail.PayType.ADD);

        createCanExecuteAction(table,stepGameStatusData);
        MJLog.play("暗杠：",cardNum,roleId,table);
        return true;
    }

}
