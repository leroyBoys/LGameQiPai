package com.game.room.action.condition;

import com.game.core.room.BaseChairInfo;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/26.
 */
public class QingYiseCheck extends MjConditionCheck {

    @Override
    public boolean check(MjTable mjTable, MjConditionType type, BaseChairInfo chairInfo) {
        ((MjHandCardsContainer)chairInfo.getHandsContainer()).getPengList();

        return mjTable.checkQYiSe(chairInfo.getHandsContainer().getHandCards(),getGroupCards(chairInfo));
    }
}
