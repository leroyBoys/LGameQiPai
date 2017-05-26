package com.game.room.status;

import com.game.core.config.IOptPlugin;
import com.game.core.room.BaseGameStateData;
import com.game.room.action.GameOperateAction;
import com.logger.type.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class StepGameStatusData {
    protected static  final Logger playLog = LoggerFactory.getLogger(LogType.Play.getLogName());
    private final IOptPlugin iOptPlugin;
    private final int fromId;
    private final int uid;
    private List<Integer> cards = new LinkedList<>();
    private final GameOperateAction action;
    private boolean isAuto = false;

    public StepGameStatusData(GameOperateAction action,int uid){
        this.action = action;
        this.fromId = 0;
        this.uid = uid;
        this.iOptPlugin = null;
        isAuto = true;
    }

    public StepGameStatusData(GameOperateAction action,int fromId,int uid,IOptPlugin iOptPlugin){
        this.action = action;
        this.fromId = fromId;
        this.uid = uid;
        this.iOptPlugin = iOptPlugin;
    }

    public StepGameStatusData(GameOperateAction action,int fromId,int uid,int card,IOptPlugin iOptPlugin){
        this.action = action;
        this.fromId = fromId;
        this.uid = uid;
        this.iOptPlugin = iOptPlugin;
        this.setCard(card);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public List<Integer> getCards() {
        return cards;
    }

    public void setCards(List<Integer> cards) {
        this.cards = cards;
    }

    public void setCard(int card) {
        this.cards.add(card);
    }

    public IOptPlugin getiOptPlugin() {
        return iOptPlugin;
    }

    public boolean isAuto() {
        return isAuto;
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

    public void toJson(){
        playLog.info(action==null?"":action.getClass().getSimpleName()+":cards:"+ Arrays.toString(cards.toArray())+"  subType:"+(iOptPlugin==null?0:iOptPlugin.getPlugin().getSubType())+" /fromId"+fromId+" uid:"+uid);
    }
}
