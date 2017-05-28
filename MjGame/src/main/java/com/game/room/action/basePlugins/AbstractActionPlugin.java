package com.game.room.action.basePlugins;

import com.game.core.config.IOptPlugin;
import com.game.core.config.PluginGen;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.core.room.calculator.PayDetail;
import com.game.room.status.StepGameStatusData;
import com.logger.type.LogType;
import com.lsocket.message.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 游戏中行为插件（吃碰杠胡）
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public abstract class AbstractActionPlugin<A extends BaseTableVo> implements IOptPlugin<A,StepGameStatusData> {
    protected Logger playLog = LoggerFactory.getLogger(LogType.Play.getLogName());
    protected PluginGen pluginGen;

    @Override
    public IOptPlugin createNew() {
        return null;
    }

    @Override
    public void setPlugin(PluginGen plugin) {
        this.pluginGen = plugin;
    }

    @Override
    public PluginGen getPlugin() {
        return pluginGen;
    }

    @Override
    public boolean doOperation(A table, Response response,int roleId, StepGameStatusData stepGameStatusData) {
        return false;
    }

    public int chickMatch(A table,List<Integer> card, StepGameStatusData stepData) {
        return stepData.getCards().get(0) == card.get(0)?1:0;
    }

    /**
     *
     * 1自摸所有人支付，其它点炮的人支付
     * */
    public PayDetail payment(A table,StepGameStatusData stepGameStatusData) {
        String str = getPlugin().getEffectStr();
        if (str == null || str.equals(""))
            return null;
        PayDetail ratePay = new PayDetail();
        int payFromAll = getPlugin().getEffectArray()[0];
        boolean isAll = payFromAll == 1?true:false;
        if(!isAll){
            if (stepGameStatusData.getFromId() == stepGameStatusData.getUid()) {
                isAll = true;
            } else {
                isAll = false;
            }
        }

        if(isAll){
            int toUid = stepGameStatusData.getUid();
            ratePay.setToUid(toUid);
            ratePay.setFromUids(getPayUids(table,toUid));
        }else {
            int toUid = stepGameStatusData.getUid();
            ratePay.setToUid(toUid);
            List<Integer> fromIds = new LinkedList<>();
            fromIds.add(stepGameStatusData.getFromId());
            ratePay.setFromUids(fromIds);
        }

        ratePay.setRate(getPlugin().getEffectArray()[1]);
        ratePay.setStep(table.getStep());
        ratePay.setType(stepGameStatusData.getAction().getActionType());
        ratePay.setSubType(getPlugin().getSubType());
        ratePay.setLostScoreType(this.getLostType());
        ratePay.setiOptPlugin(this);
        table.getCalculator().addPayDetailed(ratePay);
        return ratePay;
    }

    protected List<Integer> getPayUids(A table,int roleId){
        List<Integer> list = new LinkedList<>();
        for (int i=0;i<table.getChairs().length;i++){
            BaseChairInfo p = table.getChairs()[i];
            if((roleId == p.getId())){
                continue;
            }
            list.add(p.getId());
        }
        return list;
    }

    public int getLostType() {
        return getPlugin().getSubType();
    }
}
