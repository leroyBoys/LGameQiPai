package com.game.core.room;

import com.lgame.util.comm.KVData;

import java.util.LinkedList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class StepHistory<T extends BaseTableVo> {
    private LinkedList<KVData> actionTypeSteps = new LinkedList<>();


    public void add(int actionType,int roleId){
        actionTypeSteps.add(new KVData(roleId,actionType));
    }
}
