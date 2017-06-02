package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseGameStateData;
import com.game.core.room.BaseTableVo;
import com.game.core.room.GameOverType;
import com.game.log.MJLog;
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

    public void addCanDoDatas(int step, StepGameStatusData stepGameStatusData){
        canDoDatas.add(stepGameStatusData);

      //  MJLog.canDoActions2("writeStep:"+writeStep+" readStep:"+readStep,step,this);

        if(this.writeStep != step){
            this.writeStep = step;
        }
    }

    public final void checkMo(BaseTableVo table,int roleId){
        if(!canDoDatas.isEmpty()){
            return;
        }

        if(table.isGameOver()){
            gameOver(table);
            return;
        }

        addCanDoDatas(table.getStep(),new StepGameStatusData(MoAction.getInstance(),roleId));
    }

    public final void gameOver(BaseTableVo table){
        addCanDoDatas(table.getStep(),new StepGameStatusData(com.game.room.action.GameOverAction.getInstance(),0));
    }

    protected boolean checkCanGang(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(stepGameStatusData.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_DA && stepGameStatusData.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_MOPAI){
            return false;
        }

        return true;
    }

    public final void checkGang(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData,int card) {
        if(!checkCanGang(chairInfo,stepGameStatusData,card)){
            return;
        }

        GangAction.getInstance().check(chairInfo,stepGameStatusData,card,null);
    }

    protected boolean checkCanChi(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(stepGameStatusData.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_DA || card <= 0){
            return false;
        }
        return true;
    }

    public final void checkChi(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(!checkCanChi(chairInfo,stepGameStatusData,card)){
            return;
        }

        ChiAction.getInstance().check(chairInfo,stepGameStatusData,card,null);
    }

    protected boolean checkCanPeng(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(stepGameStatusData.getAction().getActionType() != GameConst.MJ.ACTION_TYPE_DA || card <= 0){
            return false;
        }

        return true;
    }

    public final void checkPeng(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(!checkCanPeng(chairInfo,stepGameStatusData,card)){
            return;
        }

        PengAction.getInstance().check(chairInfo,stepGameStatusData,card,null);
    }


    protected boolean checkCanHu(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        return true;
    }

    public final void checkHu(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(!checkCanHu(chairInfo,stepGameStatusData,card)){
            return;
        }

        getHuAction().check(chairInfo,stepGameStatusData,card,null);
    }

    public HuAction getHuAction(){
        return HuAction.getInstance();
    }

    protected boolean checkCanTing(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData){
        return true;
    }

    public final void checkTing(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData){
        if(!checkCanTing(chairInfo,stepGameStatusData)){
            return;
        }

        TingAction.getInstance().check(chairInfo,stepGameStatusData,0,null);
    }

    public void checkDa(MjChairInfo chairInfo) {
        if(canDoDatas.isEmpty()){
            DaAction.getIntance().check(chairInfo,null,0,null);
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
        MJLog.requset(netOprateData,roleId,table);

        if(canDoDatas.isEmpty()){
            return false;
        }

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
            if(netOprateData.getDlistCount() != 0){
                table.getChairByUid(roleId).setPassCard(netOprateData.getDlist(0));
            }

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

        if(firstMatch.getAction().getActionType() != 0){
            table.addStep();
            table.getStepHistoryManager().add(firstMatch);
        }

        firstMatch.getAction().doAction(table,response,roleId,firstMatch,netOprateData);

    }

    public String toJson(){
        StringBuilder sb = new StringBuilder(" size:"+canDoDatas.size()+"  ");
        Iterator<StepGameStatusData> ites = canDoDatas.iterator();
        while (ites.hasNext()){
            StepGameStatusData tmp = ites.next();
            sb.append(tmp.toJson());
        }
        return sb.toString();
    }
}
