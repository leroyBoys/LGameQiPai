package com.game.room.action.basePlugins;

import com.game.core.config.IPluginCheckCanExecuteAction;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjChairInfo;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.Arrays;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class MoPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction<T,StepGameStatusData>{
    @Override
    public boolean checkExecute(BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public void createCanExecuteAction(T table, StepGameStatusData stepGameStatusData) {
        SuperGameStatusData statusData = table.getStatusData();
        MjChairInfo chairInfo = table.getChairs()[table.getFocusIdex()];
        statusData.checkGang(chairInfo,0);
        statusData.checkHu(chairInfo,0);
        statusData.checkDa(chairInfo,0);
    }

    @Override
    public MoPlugins createNew() {
        return new MoPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        int card = (int) table.getCardPool().getRemainCards().remove(0);
        table.getChairByUid(roleId).getHandsContainer().addHandCards(card);
        stepGameStatusData.setCard(card);

        playLog.info("  摸牌:"+card+":roleId:"+roleId+"+ size:"+table.getChairByUid(roleId).getHandsContainer().getHandCards().size()+ Arrays.toString(table.getChairByUid(roleId).getHandsContainer().getHandCards().toArray()));

        createCanExecuteAction(table,stepGameStatusData);
        return true;
    }

    @Override
    public int chickMatch(T table,List<Integer> card, StepGameStatusData stepData) {
        return 1;
    }
}
