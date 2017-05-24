package com.game.room.action.basePlugins;

import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.DaAction;
import com.game.room.action.MoAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.Iterator;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class DaPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        gameStatusData.addCanDoDatas(new StepGameStatusData(DaAction.getIntance(),chair.getId(),chair.getId(),this));
        return false;
    }

    @Override
    public void createCanExecuteAction(BaseTableVo table) {
        SuperGameStatusData statusData = (SuperGameStatusData) table.getStatusData();

        for(int i = 0;i<table.getChairs().length;i++){
            MjChairInfo chairInfo = (MjChairInfo) table.getChairs()[i];
            statusData.checkGang(chairInfo,0);
            statusData.checkChi(chairInfo,0);
            statusData.checkPeng(chairInfo,0);
            statusData.checkHu(chairInfo,0);
        }

        if(statusData.isEmpty()){
       //     statusData.addCanDoDatas(new StepGameStatusData(MoAction.getIntance(),chair.getId(),chair.getId(),this));
        }
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {

        int removeCard = stepGameStatusData.getCards().get(0);
        table.getChairByUid(roleId).getHandsContainer().removeCardFromHand(removeCard,1);

        MjCardPoolEngine poolEngine = table.getCardPool();
        poolEngine.playOutCard(roleId,removeCard);
        return true;
    }

    @Override
    public DaPlugins createNew() {
        return new DaPlugins();
    }

    public int chickMatch(List<Integer> card, StepGameStatusData stepData) {
        return 1;
    }
}
