package com.game.core.room;

import java.util.LinkedList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public abstract class BaseStepHistory<T extends BaseTableVo> {
    private LinkedList<Integer> actionTypeSteps = new LinkedList<>();

    public void add(int actionType){
        actionTypeSteps.add(actionType);
    }
}
