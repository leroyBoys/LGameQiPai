package com.game.room.calculator;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.calculator.DefaultCalculator;
import com.game.core.room.calculator.PayDetail;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.lgame.util.json.JsonUtil;
import com.module.net.NetGame;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/27.
 */
public class MjCalculator extends DefaultCalculator<MjTable> {
    ////上一个补杠
    protected PayDetail lastBuGang;


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
    protected void clearCache() {
        payDetailList.clear();
        scoreAddDetails.clear();
        rqrEsult = null;
    }

    @Override
    public void addPayDetailed(PayDetail ratePay) {
        payDetailList.add(ratePay);
    }

    protected StepPayDetail getNewStepPayDetail(int type){
        if(type == GameConst.MJ.ACTION_TYPE_HU){
            return new HuStepPayDetail();
        }
        return new StepPayDetail();
    }

    @Override
    public final NetGame.RQREsult executeCalculator() {
        if (rqrEsult != null) {
            return rqrEsult;
        }
        fomateLog("is calculating ");

        /////uid-名目列表
        Map<Integer, List<NetGame.NetKvData>> payList = new HashMap<>();

        Map<Integer, StepPayDetail> map = new HashMap<>();
        StepPayDetail lastPayDetail = null;
        for (int i = 0; i < payDetailList.size(); i++) {
            PayDetail payDetail = payDetailList.get(i);
            if (!payDetail.isValid()) {
                continue;
            }

            StepPayDetail stepPayDetail = map.get(payDetail.getStep());
            if (stepPayDetail == null) {
                stepPayDetail = getNewStepPayDetail(payDetail.getType());
                map.put(payDetail.getStep(), stepPayDetail);

                if (lastPayDetail != null && lastPayDetail.executeCalculator(room)) {
                    addPayDetails(payList, lastPayDetail.getPayList());
                }

                lastPayDetail = stepPayDetail;
            }
            stepPayDetail.addPayDetail(this, payDetail);
        }

        if (lastPayDetail != null) {
            lastPayDetail.executeCalculator(room);
            addPayDetails(payList, lastPayDetail.getPayList());
        }

        ///算分
        calculatorScore();

        NetGame.RQREsult.Builder result = NetGame.RQREsult.newBuilder();
        result.setFlag(room.getGameOverType().ordinal());

        for (Map.Entry<Integer, List<NetGame.NetKvData>> entry : payList.entrySet()) {
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

        List<NetGame.NetKvData> newKvDatas = new LinkedList<>();

        Iterator<NetGame.NetKvData> iterators = allKvs.iterator();
        while (iterators.hasNext()){
            NetGame.NetKvData cur = iterators.next();

            NetGame.NetKvData newKvdata = newKvMap.get(cur.getK());
            if(newKvdata == null){
                newKvDatas.add(newKvdata);
                continue;
            }

            NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder(cur);
            netKvData.setV(netKvData.getV()+newKvdata.getV());
            newKvDatas.add(netKvData.build());
            iterators.remove();
        }

        allKvs.addAll(newKvDatas);
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

        fomateLog("final score:"+ JsonUtil.getJsonFromBean(scoreAddDetails));
    }

    protected void fomateLog(String log){
        logger.info("tableId:"+room.getId()+",round:"+ room.getCurRount()+log);
    }

    public PayDetail getLastBuGang() {
        return lastBuGang;
    }

    public void setLastBuGang(PayDetail lastBuGang) {
        this.lastBuGang = lastBuGang;
    }


}
