package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseGameStateData;
import com.game.core.room.BaseTableVo;
import com.game.core.room.GameOverType;
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
    private volatile int writeStep = -1;
    private volatile int readStep = -1;

    @Override
    public SuperGameStatusData createNew() {
        return new SuperGameStatusData();
    }

    public void addCanDoDatas(int step, StepGameStatusData stepGameStatusData){
        canDoDatas.add(stepGameStatusData);

        if(this.writeStep != step){
            this.writeStep = step;
        }
    }

    public final void checkMo(BaseTableVo table,int roleId){
        if(!canDoDatas.isEmpty()){
            return;
        }

        if(table.isGameOver()){
            addCanDoDatas(table.getStep(),new StepGameStatusData(com.game.room.action.GameOverAction.getInstance(),roleId));
            return;
        }

        addCanDoDatas(table.getStep(),new StepGameStatusData(MoAction.getInstance(),roleId));
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
            DaAction.getIntance().check(chairInfo,0,null);
        }
    }

    private void sortCanDoDatas(final BaseTableVo table) {
        readStep = writeStep;
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
                return wight2.compareTo(wight1);
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
        System.out.println("===========canDoDatasLength:"+canDoDatas.size());

        return canDoDatas.isEmpty();
    }

    @Override
    public boolean contains(int uid) {
        return true;
    }

    @Override
    public synchronized NetGame.NetOprateData.Builder getCanDoDatas(BaseTableVo table, int roleId) {
        if(readStep != writeStep){
            sortCanDoDatas(table);
        }

        if(roleId != 0 && roleId != canDoDatas.getFirst().getUid()){
            return null;
        }

        roleId = canDoDatas.getFirst().getUid();
        firstActionSet.clear();
        NetGame.NetOprateData.Builder netOprate = super.getCanDoDatas(table,roleId);
        netOprate.setUid(roleId);
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

    public StepGameStatusData getFirst(){
        return canDoDatas.getFirst();
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
        if(canDoDatas.getFirst().isAuto()){
            this.next(canDoDatas.getFirst(),table,response,roleId,netOprateData);
            return true;
        }


        if(netOprateData.getOtype() == GameConst.MJ.ACTION_TYPE_GUO){
            if(canDoDatas.isEmpty() || canDoDatas.getFirst().getUid() != roleId){
                table.sendError(ResponseCode.Error.parmter_error,roleId);
                return false;
            }

            clearCanDoDatas(roleId);
            table.addMsgQueue(roleId,netOprateData,response==null?0:response.getSeq());

            checkMo(table,table.getChairs()[table.nextFocusIndex(table.getFocusIdex())].getId());
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

            if(((AbstractActionPlugin)stepStatus.getiOptPlugin()).chickMatch(table,netOprateData.getDlistList(),stepStatus) == 1){
                firstMatch = stepStatus;
                break;
            }

        }

        if(firstMatch == null){
            table.sendError(ResponseCode.Error.parmter_error,roleId);
            return false;
        }

        this.next(firstMatch,table,response,roleId,netOprateData);
        return true;
    }

    private void next(StepGameStatusData firstMatch, MjTable table, Response response,int roleId,NetGame.NetOprateData netOprateData){
        if(firstMatch.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_HU){
            canDoDatas.clear();
        }else {//如果是胡牌操作，
            Iterator<StepGameStatusData> ites = canDoDatas.iterator();
            while (ites.hasNext()){
                StepGameStatusData tmp = ites.next();
                if(roleId ==tmp.getUid() || tmp.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_HU){
                    ites.remove();
                    continue;
                }
            }
        }

        table.addStep();
        table.getStepHistoryManager().add(firstMatch);

        if(netOprateData != null){
            firstMatch.setCards(netOprateData.getDlistList());
        }
        firstMatch.getAction().doAction(table,response,roleId,firstMatch);
    }

    public String toJson(){
        StringBuilder sb = new StringBuilder();
        Iterator<StepGameStatusData> ites = canDoDatas.iterator();
        while (ites.hasNext()){
            StepGameStatusData tmp = ites.next();
            sb.append(tmp.toJson());
        }
        return sb.toString();
    }
}
