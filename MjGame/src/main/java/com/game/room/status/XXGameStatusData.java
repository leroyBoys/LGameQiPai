package com.game.room.status;

import com.game.core.constant.GameConst;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.game.room.action.HuAction;
import com.game.room.action.SuperGameStatusData;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class XXGameStatusData extends SuperGameStatusData {

    @Override
    public XXGameStatusData createNew() {
        return new XXGameStatusData();
    }

    protected boolean checkCanChi(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        return true;
        //return false;
    }

    protected boolean checkCanHu(MjChairInfo chair,StepGameStatusData stepGameStatusData, int card){
        if(!chair.isCanDo()){
            return false;
        }

        if((((MjTable)chair.getTableVo()).getType() & GameConst.XXMjType.ZIMO )== GameConst.XXMjType.ZIMO){
            if(card != 0){
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean checkCanPeng(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(!chairInfo.isCanDo()){
            return false;
        }

        return super.checkCanPeng(chairInfo,stepGameStatusData, card);
    }

    @Override
    protected boolean checkCanGang(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData, int card){
        if(!chairInfo.isCanDo()){
            return false;
        }
        return super.checkCanGang(chairInfo, stepGameStatusData,card);
    }

    protected boolean checkCanTing(MjChairInfo chairInfo,StepGameStatusData stepGameStatusData){
        return false;
    }
}