package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseGameStateData;
import com.game.core.room.BaseTableVo;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.*;
import com.game.room.action.basePlugins.AbstractActionPlugin;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;
import com.module.core.ResponseCode;
import com.module.net.NetGame;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class SuperGameStatusData extends BaseGameStateData {
    protected LinkedList<StepGameStatusData> canDoDatas = new LinkedList<>();
    protected Set<Integer> firstActionSet = new HashSet<>();//第一可以操作的集合

    public void addCanDoDatas(StepGameStatusData stepGameStatusData){
        canDoDatas.add(stepGameStatusData);
        if(!firstActionSet.isEmpty()){
            firstActionSet.clear();
        }
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

    private void sortCanDoDatas(final BaseTableVo table) {
        if(canDoDatas.size() <=1){
            return;
        }

        Collections.sort(canDoDatas, new Comparator<StepGameStatusData>() {
            @Override
            public int compare(StepGameStatusData o1, StepGameStatusData o2) {
                Integer wight1 = o1.getAction().getWeight();
                Integer wight2 = o2.getAction().getWeight();

                if(wight1 == wight2){
                    wight1 = getIdexWeight(table,table.getChairByUid(o1.getUid()).getIdx());
                    wight2 = getIdexWeight(table,table.getChairByUid(o2.getUid()).getIdx());
                }
                return wight1.compareTo(wight2);
            }
        });
    }

    /**
     *  根据当前的位置判断获得一个目标位置与自己位置优先级的权重值
     * @param table
     * @param targetIndex
     * @return
     */
    private int getIdexWeight(BaseTableVo table,int targetIndex){
        if(table.getFocusIdex() > targetIndex){
            return table.getChairs().length-table.getFocusIdex()+targetIndex;
        }
        return targetIndex - table.getFocusIdex();
    }

    public boolean isEmpty(){
        return canDoDatas.isEmpty();
    }

    @Override
    public synchronized NetGame.NetOprateData.Builder getCanDoDatas(BaseTableVo table, int roleId) {
        if(firstActionSet.isEmpty()){
            sortCanDoDatas(table);
        }

        if(roleId != 0 && roleId != canDoDatas.getFirst().getUid()){
            return null;
        }

        NetGame.NetOprateData.Builder netOprate = super.getCanDoDatas(table,roleId);

        for(StepGameStatusData step:canDoDatas){
            if(netOprate.getUid() != step.getUid()){
                break;
            }

            firstActionSet.add(step.getAction().getActionType());
            NetGame.NetKvData.Builder netKv = NetGame.NetKvData.newBuilder();
            netKv.setK(step.getAction().getActionType());
            netKv.setV(step.getiOptPlugin().getPlugin().getSubType());
            netOprate.addAllDlist(step.getCards());
            netOprate.addKvDatas(netKv);
        }
        return netOprate;
    }

    @Override
    public NetGame.NetOprateData.Builder getStatusDetail(BaseTableVo tableVo) {
        return null;
    }

    private void clearCanDoDatas(int roleId){
        Iterator<StepGameStatusData> ites = canDoDatas.iterator();
        while (ites.hasNext()){
            if(roleId != ites.next().getUid()){
                break;
            }
            ites.remove();
        }
    }

    public synchronized boolean checkMatch(MjTable table, int roleId, NetGame.NetOprateData netOprateData, Response response){

        if(netOprateData.getOtype() == GameConst.MJ.ACTION_TYPE_GUO){
            if(canDoDatas.isEmpty() || canDoDatas.getFirst().getUid() != roleId){
                table.sendError(ResponseCode.Error.parmter_error,roleId);
                return false;
            }

            clearCanDoDatas(roleId);
            GuoAction.getInstance().doAction(table,response,roleId,netOprateData);
            return true;
        }

        if(!firstActionSet.contains(netOprateData.getOtype())){
            table.sendError(ResponseCode.Error.parmter_error,roleId);
            return false;
        }

        StepGameStatusData firstMatch = null;
        for(StepGameStatusData stepStatus:canDoDatas){
            if(roleId != stepStatus.getUid()){
                break;
            }

            if(stepStatus.getAction().getActionType() != netOprateData.getOtype()){
                continue;
            }

            if(netOprateData.getDval() != 0 && stepStatus.getiOptPlugin().getPlugin().getSubType() != netOprateData.getDval()){
                continue;
            }

            if(((AbstractActionPlugin)stepStatus.getiOptPlugin()).chickMatch(netOprateData.getDlistList(),stepStatus) == 1){
                firstMatch = stepStatus;
                break;
            }

        }

        if(firstMatch == null){
            table.sendError(ResponseCode.Error.parmter_error,roleId);
            return false;
        }

       // clearCanDoDatas(roleId);
        canDoDatas.clear();
        table.addStep();
        table.getStepHistoryManager().add(firstMatch);
        firstMatch.getAction().doAction(table,response,roleId,netOprateData);
        return true;
    }
}
