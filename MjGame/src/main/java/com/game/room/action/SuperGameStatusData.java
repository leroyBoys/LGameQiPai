package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseStatusData;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.lgame.util.comm.KVData;
import com.module.net.NetGame;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class SuperGameStatusData extends BaseStatusData {
    protected LinkedList<StepGameStatusData> canDoDatas = new LinkedList<>();
    protected boolean isChange = true;

    public void addCanDoDatas(StepGameStatusData stepGameStatusData){
        canDoDatas.add(stepGameStatusData);
        isChange = true;
    }

    public void moPai(MjTable table, int uid){
        MoAction.getInstance().doAction(table,null,uid,null);
    }


    public final void checkCanDo(MjChairInfo chairInfo,int card) {
        checkGang(chairInfo,0);
        checkChi(chairInfo,0);
        checkPeng(chairInfo,0);
        checkHu(chairInfo,0);

    }

    protected boolean checkCanGang(MjChairInfo chairInfo, int card){
        StepGameStatusData last = (StepGameStatusData) chairInfo.getTableVo().getStepHistoryManager().getLastStep();
        if(card>0){
            if(last.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_DA){
                return false;
            }
        }else if(last.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_MOPAI){
            return false;
        }

        return true;
    }

    public final void checkGang(MjChairInfo chairInfo,int card) {
        if(!checkCanGang(chairInfo,card)){
            return;
        }

        GangAction.getInstance().check(chairInfo,card,null);
    }

    protected boolean checkCanChi(MjChairInfo chairInfo,int card){
        StepGameStatusData last = (StepGameStatusData) chairInfo.getTableVo().getStepHistoryManager().getLastStep();
        if(last.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_DA || card <= 0){
            return false;
        }
        BaseChairInfo lastUserInfo = chairInfo.getTableVo().getChairByUid(last.getUid());
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
        StepGameStatusData last = (StepGameStatusData) chairInfo.getTableVo().getStepHistoryManager().getLastStep();
        if(last.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_DA || card <= 0){
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

    public void checkDa(MjChairInfo chairInfo, int card) {
        if(canDoDatas.isEmpty()){
            addCanDoDatas(new StepGameStatusData(DaAction.getIntance(),chairInfo.getId(),chairInfo.getId(),null));
        }
    }

    public StepGameStatusData getFirstCanDoAction() {
        return canDoDatas.getFirst();
    }

    public void sortCanDoDatas(final MjTable table) {
        isChange = false;
        if(canDoDatas.size() <=1){
            return;
        }

        Collections.sort(canDoDatas, new Comparator<StepGameStatusData>() {
            @Override
            public int compare(StepGameStatusData o1, StepGameStatusData o2) {
                Integer wight1 = o1.getiOptPlugin().getWeight();
                Integer wight2 = o2.getiOptPlugin().getWeight();

                if(wight1 == wight2){
                    StepGameStatusData last = (StepGameStatusData) table.getStepHistoryManager().getLastStep();
                    int lastIdex = table.getChairByUid(last.getUid()).getIdx();
                    wight1 = getIdexWeight(table.getChairByUid(o1.getUid()).getIdx(),lastIdex);
                    wight2 = getIdexWeight(table.getChairByUid(o2.getUid()).getIdx(),lastIdex);
                }
                return wight1.compareTo(wight2);
            }
        });
    }

    private int getIdexWeight(int myIndex,int targetIndex){
        return 1;
    }

    public NetGame.NetOprateData getCanDoDatas(){
        NetGame.NetOprateData.Builder netOprate = NetGame.NetOprateData.newBuilder();
        netOprate.setUid(canDoDatas.getFirst().getUid());

        for(StepGameStatusData step:canDoDatas){
            if(netOprate.getUid() != step.getUid()){
                break;
            }

            NetGame.NetKvData.Builder netKv = NetGame.NetKvData.newBuilder();
            netKv.setK(step.getAction().getActionType());
            netKv.setV(step.getiOptPlugin().getPlugin().getSubType());
            if(step.getCard() != 0){

            }
/*
            netKv.addAllDlist(step.ge)
            netOprate.addKvDatas()*/
        }
        return netOprate.build();
    }
}
