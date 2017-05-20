package com.game.room;

import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.core.room.BaseStatusData;
import com.game.room.action.*;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class SuperGameStatusData extends BaseStatusData {
    /** 可以操作的操作集合 大类（不含胡牌）-数据 */
    protected Map<Integer,StepGameStatusData> canDoDatas = new HashMap<>();
    protected List<StepGameStatusData> canHuDatas = new LinkedList<>();
    /** 可以操作的操作集合的一个权重 */
    protected int canDoDataLimit = 0;//
    private int createStep;

    public void addCanDoDatas(StepGameStatusData stepGameStatusData){
        canDoDataLimit |= stepGameStatusData.getiOptPlugin().getWeight();
        canDoDatas.put(stepGameStatusData.getType(),stepGameStatusData);
    }

    public void addCanHuDatas(StepGameStatusData stepGameStatusData){
        canDoDataLimit |= stepGameStatusData.getiOptPlugin().getWeight();
        canHuDatas.add(stepGameStatusData);
    }

    public void moPai(MjTable table, int uid){
        MoAction.getInstance().systemDoAction(table,uid,null);
    }

    protected boolean checkCanGang(MjChairInfo chairInfo, int card){
        return true;
    }

    public void checkGang(MjChairInfo chairInfo,int card) {
        if(!checkCanGang(chairInfo,card)){
            return;
        }

        GangAction.getInstance().check(chairInfo,card,null);
    }

    protected boolean checkCanChi(MjChairInfo chairInfo,int card){
        return true;
    }

    public void checkChi(MjChairInfo chairInfo,int card) {
        if(!checkCanChi(chairInfo,card)){
            return;
        }

        ChiAction.getInstance().check(chairInfo,card,null);
    }

    protected boolean checkCanPeng(MjChairInfo chairInfo,int card){
        return true;
    }

    public void checkPeng(MjChairInfo chairInfo,int card) {
        if(!checkCanPeng(chairInfo,card)){
            return;
        }

        PengAction.getInstance().check(chairInfo,card,null);
    }


    protected HuAction.CheckHuType checkCanHu(MjChairInfo chairInfo, int card){
        return HuAction.CheckHuType.Hu;
    }

    public void checkHu(MjChairInfo chairInfo,int card) {
        HuAction.CheckHuType checkHuType = checkCanHu(chairInfo,card);
        if(checkHuType == HuAction.CheckHuType.NULL){
            return;
        }

        HuAction.getInstance().check(chairInfo,card,checkHuType);
    }

}
