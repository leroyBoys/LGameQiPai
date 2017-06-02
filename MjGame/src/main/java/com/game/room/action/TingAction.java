package com.game.room.action;

import com.game.core.constant.GameConst;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjChairInfo;
import com.game.room.action.basePlugins.IPluginHuCheck;
import com.game.room.status.StepGameStatusData;
import com.game.room.util.MJTool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class TingAction extends DaAction {
    private final static TingAction instance = new TingAction();
    protected TingAction(){}

    public static TingAction getInstance(){
        return instance;
    }

    @Override
    public int getActionType() {
        return GameConst.MJ.ACTION_TYPE_TING;
    }

    @Override
    public void check(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card, Object parems){
        List<Integer> hands = chairInfo.getHandsContainer().getHandCards();
        MjAutoCacheHandContainer autoHandCache = (MjAutoCacheHandContainer) chairInfo.getHandsContainer().getAutoCacheHands();

        SuperGameStatusData superGameStatusData = (SuperGameStatusData) chairInfo.getTableVo().getStatusData();

        int[][] cardArray = MJTool.getCardsByType(hands,0);
        for(Map.Entry<Integer,Integer> entry:autoHandCache.getCardNumMap().entrySet()){
            int[][] curArray = cloneArray(cardArray);
            int type = entry.getKey()/10;
            int id = entry.getKey()%10;
            curArray[type][id] = curArray[type][id]-1;

            IPluginHuCheck huCheck = superGameStatusData.getHuAction().getIPluginHuCheck(chairInfo,curArray);
            if(huCheck == null){
                continue;
            }

        }
    }

    private int[][] cloneArray(int[][] cardArray){
        int[][] newArray = new int[cardArray.length][];
        for(int i = 0;i<cardArray.length;i++){
            newArray[i] = Arrays.copyOf(cardArray[i],cardArray[i].length);
        }
        return newArray;
    }

    @Override
    public int getWeight() {
        return GameConst.Weight.HU_TING;
    }
}
