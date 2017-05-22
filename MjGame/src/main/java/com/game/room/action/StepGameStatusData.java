package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.room.BaseStatusData;
import com.game.room.action.GameAction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class StepGameStatusData extends BaseStatusData {
    private final IOptPlugin iOptPlugin;
    private final int fromId;
    private final int uid;
    private List<Integer> cards = new LinkedList<>();
    private final GameOperateAction action;

    public StepGameStatusData(GameOperateAction action,int fromId,int uid,IOptPlugin iOptPlugin){
        this.action = action;
        this.fromId = fromId;
        this.uid = uid;
        this.iOptPlugin = iOptPlugin;
    }

    public List<Integer> getCards() {
        return cards;
    }

    public void setCards(List<Integer> cards) {
        this.cards = cards;
    }

    public IOptPlugin getiOptPlugin() {
        return iOptPlugin;
    }

    public int getFromId() {
        return fromId;
    }

    public GameOperateAction getAction() {
        return action;
    }

    public int getUid() {
        return uid;
    }
}
