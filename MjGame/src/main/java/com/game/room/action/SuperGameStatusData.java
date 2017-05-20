package com.game.room.action;

import com.game.core.room.BaseStatusData;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;

import java.util.LinkedList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class SuperGameStatusData extends BaseStatusData {
    protected LinkedList<StepGameStatusData> canDoDatas = new LinkedList<>();

    public void addCanDoDatas(StepGameStatusData stepGameStatusData){
        canDoDatas.add(stepGameStatusData);
    }

    public void moPai(MjTable table, int uid){
        MoAction.getInstance().doAction(table,null,uid,null);
    }

    protected boolean checkCanGang(MjChairInfo chairInfo, int card){
        return true;
    }


    public void checkCanDo(MjChairInfo chairInfo,int card) {
        checkGang(chairInfo,0);
        checkChi(chairInfo,0);
        checkPeng(chairInfo,0);
        checkHu(chairInfo,0);

    }

    public void checkGang(MjChairInfo chairInfo,int card) {
        if(!checkCanGang(chairInfo,card)){
            return;
        }

        GangAction.getInstance().check(chairInfo,card,null);
    }

    protected boolean checkCanChi(MjChairInfo chairInfo,int card){
        return true;
    }

    public void checkChi(MjChairInfo chairInfo,int card) {
        if(!checkCanChi(chairInfo,card)){
            return;
        }

        ChiAction.getInstance().check(chairInfo,card,null);
    }

    protected boolean checkCanPeng(MjChairInfo chairInfo,int card){
        return true;
    }

    public void checkPeng(MjChairInfo chairInfo,int card) {
        if(!checkCanPeng(chairInfo,card)){
            return;
        }

        PengAction.getInstance().check(chairInfo,card,null);
    }


    protected HuAction.CheckHuType checkCanHu(MjChairInfo chairInfo, int card){
        return HuAction.CheckHuType.Hu;
    }

    public void checkHu(MjChairInfo chairInfo,int card) {
        HuAction.CheckHuType checkHuType = checkCanHu(chairInfo,card);
        if(checkHuType == HuAction.CheckHuType.NULL){
            return;
        }

        HuAction.getInstance().check(chairInfo,card,checkHuType);
    }

    public LinkedList<StepGameStatusData> getCanDoDatas() {
        return canDoDatas;
    }
}
