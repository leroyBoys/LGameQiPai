package com.game.core.room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class StepHistory<STEP> {
    private List<STEP> actionTypeSteps = new ArrayList<>();

    public void clean(){
        actionTypeSteps.clear();
    }
    public void add(STEP data){
        actionTypeSteps.add(data);
    }

    public STEP getLastStep() {
        return actionTypeSteps.get(actionTypeSteps.size()-1);
    }

    public STEP getLastStep(int lastStep) {
        return actionTypeSteps.get(actionTypeSteps.size()+lastStep);
    }

}
