package com.game.room.action;

import com.game.room.MjChairInfo;
import com.game.room.MjTable;

import java.util.LinkedList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class XXGameStatusData extends SuperGameStatusData {
    protected LinkedList<StepGameStatusData> canDoDatas = new LinkedList<>();

    public void addCanDoDatas(StepGameStatusData stepGameStatusData){
        canDoDatas.add(stepGameStatusData);
    }

    public void moPai(MjTable table, int uid){
        MoAction.getInstance().doAction(table,null,uid,null);
    }

    protected boolean checkCanChi(MjChairInfo chairInfo,int card){
        return false;
    }

    protected HuAction.CheckHuType checkCanHu(MjChairInfo chairInfo, int card){
        return HuAction.CheckHuType.Hu;
    }
}
