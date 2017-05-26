package com.game.room.action.condition;

import com.game.core.room.BaseChairInfo;
import com.game.room.GroupCard;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/26.
 */
public abstract class MjConditionCheck<T extends MjTable> {
    protected List<GroupCard> getGroupCards(BaseChairInfo chairInfo){
        List<GroupCard> list = new LinkedList<>();
        MjHandCardsContainer handers = (MjHandCardsContainer) chairInfo.getHandsContainer();
        if(!handers.getPengList().isEmpty()){
            list.addAll(handers.getPengList());
        }

        if(!handers.getChiGangList().isEmpty()){
            list.addAll(handers.getChiGangList());
        }

        return list;
    }

    public abstract boolean check(T t, MjConditionType type, BaseChairInfo chairInfo);
}
