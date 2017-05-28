package com.game.room.calculator;

import com.game.core.config.TablePluginManager;
import com.game.core.constant.GameConst;
import com.game.core.room.calculator.PayDetail;
import com.game.room.MjTable;
import com.module.net.NetGame;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/27.
 */
public class HuStepPayDetail extends StepPayDetail{

    protected int getBaseScore(MjTable room){
        return 0;
    }

    @Override
    protected boolean execute(MjTable room) {
        int multiRate = 1<<multipleRateTotal;

        int maxFanObj = room.getMaxFan();
        if(maxFanObj != 0 && multiRate>maxFanObj){
            multiRate = maxFanObj;
        }

        int addRate = addRateTotal+TablePluginManager.getInstance().getRoomSetting(room.getGameId()).getBaseScore();
        boolean isFirstAdd = true;
      /*  int every = 0;
        if(isFirstAdd){//先加再乘
            every = addRate*multiRate;
        }else {
            every = multiRate+addRate;
        }*/

        int allScore=0;
        for(int uid : fromUids){
            int score = multiRate*(addRate+getYaPao(room,uid,toUid));
            allScore+=score;
            room.getCalculator().addScore(uid,-score);
        }

        this.gainTotal = allScore;
        room.getCalculator().addScore(toUid,gainTotal);
        return true;
    }

    private int getYaPao(MjTable room, int uid, int toUid) {
        return room.getChairByUid(uid).getYapaoNum()+room.getChairByUid(toUid).getYapaoNum();
    }
}
