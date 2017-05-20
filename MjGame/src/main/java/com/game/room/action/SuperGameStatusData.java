package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseStatusData;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.lgame.util.comm.KVData;

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
        KVData last = (KVData) chairInfo.getTableVo().getStepHistoryManager().getActionTypeSteps().getLast();
        if(card>0){
            if(last.getK() != GameConst.MJ.ACTION_TYPE_DA){
                return false;
            }
        }else if(last.getK() != GameConst.MJ.ACTION_TYPE_MOPAI){
            return false;
        }

        return true;
    }


    public final void checkCanDo(MjChairInfo chairInfo,int card) {
        checkGang(chairInfo,0);
        checkChi(chairInfo,0);
        checkPeng(chairInfo,0);
        checkHu(chairInfo,0);

    }

    public final void checkGang(MjChairInfo chairInfo,int card) {
        if(!checkCanGang(chairInfo,card)){
            return;
        }

        GangAction.getInstance().check(chairInfo,card,null);
    }

    protected boolean checkCanChi(MjChairInfo chairInfo,int card){
        KVData<Integer,Integer> last = (KVData) chairInfo.getTableVo().getStepHistoryManager().getActionTypeSteps().getLast();
        if(last.getK() != GameConst.MJ.ACTION_TYPE_DA || card <= 0){
            return false;
        }
        BaseChairInfo lastUserInfo = chairInfo.getTableVo().getChairByUid(last.getV());
        if(chairInfo.getIdx() != chairInfo.getTableVo().nextFocusIndex(lastUserInfo.getIdx())){
            return false;
        }
        return true;
    }

    public final void checkChi(MjChairInfo chairInfo,int card) {
        if(!checkCanChi(chairInfo,card)){
            return;
        }

        ChiAction.getInstance().check(chairInfo,card,null);
    }

    protected boolean checkCanPeng(MjChairInfo chairInfo,int card){
        KVData<Integer,Integer> last = (KVData) chairInfo.getTableVo().getStepHistoryManager().getActionTypeSteps().getLast();
        if(last.getK() != GameConst.MJ.ACTION_TYPE_DA || card <= 0){
            return false;
        }

        return true;
    }

    public final void checkPeng(MjChairInfo chairInfo,int card) {
        if(!checkCanPeng(chairInfo,card)){
            return;
        }

        PengAction.getInstance().check(chairInfo,card,null);
    }


    protected HuAction.CheckHuType checkCanHu(MjChairInfo chairInfo, int card){
        return HuAction.CheckHuType.Hu;
    }

    public final void checkHu(MjChairInfo chairInfo,int card) {
        HuAction.CheckHuType checkHuType = checkCanHu(chairInfo,card);
        if(checkHuType == HuAction.CheckHuType.NULL){
            return;
        }

        HuAction.getInstance().check(chairInfo,card,checkHuType);
    }

    public final LinkedList<StepGameStatusData> getCanDoDatas() {
        return canDoDatas;
    }
}
