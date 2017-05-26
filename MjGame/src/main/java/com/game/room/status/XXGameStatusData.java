package com.game.room.status;

import com.game.room.MjChairInfo;
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

    protected boolean checkCanChi(MjChairInfo chairInfo,int card){
        return false;
    }

    protected HuAction.CheckHuType checkCanHu(MjChairInfo chairInfo, int card){
        if(!chairInfo.isCanDo()){
            return HuAction.CheckHuType.NULL;
        }
        return HuAction.CheckHuType.Hu;
    }

    @Override
    protected boolean checkCanPeng(MjChairInfo chairInfo, int card) {
        if(!chairInfo.isCanDo()){
            return false;
        }

        return super.checkCanPeng(chairInfo, card);
    }

    @Override
    protected boolean checkCanGang(MjChairInfo chairInfo, int card) {
        if(!chairInfo.isCanDo()){
            return false;
        }
        return super.checkCanGang(chairInfo, card);
    }
}
