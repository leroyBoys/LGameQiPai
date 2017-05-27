package com.game.room.calculator;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.calculator.DefaultCalculator;
import com.game.core.room.calculator.PayDetail;
import com.game.room.MjTable;
import com.module.net.NetGame;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/27.
 */
public class MjCalculator extends DefaultCalculator<MjTable> {
    /** 每一步的得失分条目列表 */
    private ArrayList<PayDetail> payDetailList = new ArrayList<>();
    private NetGame.RQREsult rqrEsult;
    /** 合并得失分项目 */
    private boolean merageItem = false;

    /** 玩家id---得失分 */
    protected Map<Integer,Integer> scoreAddDetails = new HashMap<>();

    public MjCalculator(MjTable room) {
        super(room);
    }

    @Override
    public void clearCache() {
        payDetailList.clear();
        rqrEsult = null;
    }

    @Override
    public void addPayDetailed(PayDetail ratePay) {
        payDetailList.add(ratePay);
    }

    protected StepPayDetail getNewStepPayDetail(){
        return new StepPayDetail();
    }

    @Override
    public final NetGame.RQREsult executeCalculator() {
        if(rqrEsult != null){
            return rqrEsult;
        }
        logger.debug("room [" + room.getId() + "] is calculating ");

        /////uid-名目列表
        Map<Integer,List<NetGame.NetKvData>> payList = new HashMap<>();

        Map<Integer,StepPayDetail> map = new HashMap<>();
        StepPayDetail lastPayDetail = null;
        for(int i = 0;i<payDetailList.size();i++){
            PayDetail payDetail = payDetailList.get(i);
            if(!payDetail.isValid()){
                continue;
            }

            StepPayDetail stepPayDetail = map.get(payDetail.getStep());
            if(stepPayDetail == null){
                stepPayDetail = getNewStepPayDetail();
                map.put(payDetail.getStep(),stepPayDetail);

                if(lastPayDetail != null && lastPayDetail.executeCalculator(room)){
                    addPayDetails(payList,lastPayDetail.getPayList());
                }

                lastPayDetail = stepPayDetail;
            }
            stepPayDetail.addPayDetail(payDetail);
        }

        if(lastPayDetail != null){
            lastPayDetail.executeCalculator(room);
            addPayDetails(payList,lastPayDetail.getPayList());
        }

        ///算分
        calculatorScore();

        NetGame.RQREsult.Builder result = NetGame.RQREsult.newBuilder();
        result.setFlag(room.getGameOverType().ordinal());

        for(Map.Entry<Integer,List<NetGame.NetKvData>> entry:payList.entrySet()){
            NetGame.NetMjUserResult.Builder netMjResult = NetGame.NetMjUserResult.newBuilder();
            netMjResult.addAllScores(entry.getValue());
            netMjResult.setScore(scoreAddDetails.get(entry.getKey()));

        }
        rqrEsult = result.build();
        return rqrEsult;
    }

    public void addPayDetails(Map<Integer,List<NetGame.NetKvData>> allPayList, Map<Integer,List<NetGame.NetKvData>> payList){
        if(payList == null || payList.isEmpty()){
            return;
        }

        for(Map.Entry<Integer,List<NetGame.NetKvData>> entry:payList.entrySet()){
            List<NetGame.NetKvData> list = allPayList.get(entry.getKey());
            if(list == null){
                allPayList.put(entry.getKey(),entry.getValue());
                continue;
            }
            meragePayDetail(list,entry.getValue());
        }
    }


    public void meragePayDetail(List<NetGame.NetKvData> allKvs,List<NetGame.NetKvData> newKvs){
        if(!merageItem){
            allKvs.addAll(newKvs);
            return;
        }

        Map<Integer,NetGame.NetKvData> newKvMap = new HashMap<>(newKvs.size());
        for(NetGame.NetKvData kvData:newKvs){
            newKvMap.put(kvData.getK(),kvData);
        }

        for(NetGame.NetKvData kvData:allKvs){
            NetGame.NetKvData newKvdata = newKvMap.get(kvData.getK());

            newKvMap.put(kvData.getK(),kvData);
        }

    }

    @Override
    public void addScore(int uid,int score){
        Integer curScore = scoreAddDetails.get(uid);
        if(curScore == null){
            curScore = 0;
        }

        curScore+=score;
        scoreAddDetails.put(uid,curScore);
    }

    protected void calculatorScore(){
        for(Map.Entry<Integer,Integer> entry:scoreAddDetails.entrySet()){
            BaseChairInfo info = room.getChairByUid(entry.getKey());
            info.setTotalScore(info.getTotalScore()+entry.getValue());
        }
    }
}
