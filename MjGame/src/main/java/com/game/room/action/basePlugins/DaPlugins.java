package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.log.MJLog;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.DaAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class DaPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction<T, StepGameStatusData>{
    @Override
    public boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {
        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(DaAction.getIntance(),chair.getId(),chair.getId(),this));
        return false;
    }

    @Override
    public void createCanExecuteAction(T table,StepGameStatusData stepGameStatusData) {
        SuperGameStatusData statusData =  table.getStatusData();

        int card = stepGameStatusData.getCards().get(0);

        int nextIdex = table.nextFocusIndex(table.getChairByUid(stepGameStatusData.getUid()).getIdx());
        int nextUid = table.getChairs()[nextIdex].getId();

        for(int i = 0;i<table.getChairs().length;i++){
            if(i == table.getFocusIdex()){
                continue;
            }
            MjChairInfo chairInfo = table.getChairs()[i];

            if(nextUid == chairInfo.getId()){
                statusData.checkChi(chairInfo,stepGameStatusData,card);
            }
            statusData.checkGang(chairInfo,stepGameStatusData,card);
            statusData.checkPeng(chairInfo,stepGameStatusData,card);
            statusData.checkTing(chairInfo,stepGameStatusData);
        }
        statusData.checkMo(table,nextUid);
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        int removeCard = stepGameStatusData.getCards().get(0);
        table.getChairByUid(roleId).getHandsContainer().removeCardFromHand(removeCard,1);

        MjCardPoolEngine poolEngine = table.getCardPool();
        poolEngine.playOutCard(roleId,removeCard);
        MJLog.play("打",removeCard,roleId,table);

        this.createCanExecuteAction(table,stepGameStatusData);

        return true;
    }

    @Override
    public DaPlugins createNew() {
        return new DaPlugins();
    }

    public int chickMatch(T table,List<Integer> card, StepGameStatusData stepData) {
        BaseChairInfo chairInfo = table.getChairByUid(stepData.getUid());

        return ((MjAutoCacheHandContainer)(chairInfo.getHandsContainer()).getAutoCacheHands()).containCard(card.get(0))?1:0;
    }
}
