package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjTable;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class TingPlugins<T extends MjTable> extends DaPlugins<T>{
    @Override
    public final boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {
        return false;
    }

    @Override
    public TingPlugins createNew() {
        return new TingPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        return true;
    }

    @Override
    public int chickMatch(T table, List<Integer> card, StepGameStatusData stepData) {
        BaseChairInfo chairInfo = table.getChairByUid(stepData.getUid());

        return ((MjAutoCacheHandContainer)(chairInfo.getHandsContainer()).getAutoCacheHands()).containCard(card.get(0))?1:0;
    }
}
