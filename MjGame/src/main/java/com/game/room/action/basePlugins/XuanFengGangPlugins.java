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
 * 东西南北
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class XuanFengGangPlugins<T extends MjTable> extends AnGangGangPlugins<T> {
    @Override
    protected boolean checkExecute(BaseChairInfo chair) {
        MjAutoCacheHandContainer mjAutoCache = (MjAutoCacheHandContainer) chair.getHandsContainer().getAutoCacheHands();
        Map<Integer, Integer> countMap =  mjAutoCache.getCardNumMap();
        if (!countMap.containsKey(41) || !countMap.containsKey(42) || !countMap.containsKey(43)|| !countMap.containsKey(44)){
            return false;
        }

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        StepGameStatusData stepGameStatusData = new StepGameStatusData(GangAction.getInstance(),chair.getId(),chair.getId(),0,this);
        stepGameStatusData.setCard(0);//固定的值所以只需要约定，只判定子类型即可，不用判定内容
        gameStatusData.addCanDoDatas(stepGameStatusData);
        return true;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo room) {

    }

    @Override
    public XuanFengGangPlugins createNew() {
        return new XuanFengGangPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        if (stepGameStatusData.getiOptPlugin().getPlugin().getSubType() != this.getPlugin().getSubType()) {
            return false;
        }

        MjChairInfo chair = table.getChairByUid(roleId);
        MjHandCardsContainer handCardsContainer = chair.getHandsContainer();
        handCardsContainer.removeCardFromHand(41,1);
        handCardsContainer.removeCardFromHand(42,1);
        handCardsContainer.removeCardFromHand(43,1);
        handCardsContainer.removeCardFromHand(44,1);

        List<Integer> cards = new LinkedList<>();
        cards.add(0);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);
        return true;
    }

    @Override
    public int chickMatch(List<Integer> card, StepGameStatusData stepData) {
        return 1;
    }
}
