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
        Map<Integer, Integer> countMap =  mjAutoCache.getCardNumMap();
        if (!countMap.containsKey(45) || !countMap.containsKey(46) || !countMap.containsKey(47)){
            return false;
        }
        if((countMap.get(45)+countMap.get(46)+countMap.get(47))<4){
            return false;
        }

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();

        List<Integer> cards = mjAutoCache.getCardCountMap().get(2);
        for(Integer card:cards){
            if (card == 45 || card == 46||card == 47){
                StepGameStatusData stepGameStatusData = new StepGameStatusData(GangAction.getInstance(),chair.getId(),chair.getId(),card,this);
                stepGameStatusData.setCard(card);
                gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),stepGameStatusData);
                continue;
            }
        }
        return true;
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

        int extraCardNum = stepGameStatusData.getCards().get(0);
        int card45 = extraCardNum == 45?2:1;
        int card46 = extraCardNum == 46?2:1;
        int card47 = extraCardNum == 47?2:1;

        MjChairInfo chair = table.getChairByUid(roleId);
        MjHandCardsContainer handCardsContainer = chair.getHandsContainer();
        Iterator<Integer> it = handCardsContainer.getHandCards().iterator();
        while (it.hasNext()) {
            int cardNum = it.next();
            if(cardNum < 45){
                continue;
            }

            if(cardNum == 45){
                if(card45 > 0){
                    it.remove();
                    card45--;
                }
            }else if(cardNum == 46){
                if(card46 > 0){
                    it.remove();
                    card46--;
                }
            }else if(cardNum == 47){
                if(card47 > 0){
                    it.remove();
                    card47--;
                }
            }
        }

        List<Integer> cards = new LinkedList<>();
        cards.add(extraCardNum);
        chair.getHandsContainer().addOutCard(this.getPlugin().getSubType(), cards);
        return true;
    }

}
