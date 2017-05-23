package com.game.room.status;

import com.game.room.MjChairInfo;
import com.game.room.action.HuAction;
import com.game.room.action.SuperGameStatusData;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class XXGameStatusData extends SuperGameStatusData {

    protected boolean checkCanChi(MjChairInfo chairInfo,int card){
        return false;
    }

    protected HuAction.CheckHuType checkCanHu(MjChairInfo chairInfo, int card){
        return HuAction.CheckHuType.Hu;
    }
}
