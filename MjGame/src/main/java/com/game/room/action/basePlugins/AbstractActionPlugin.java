package com.game.room.action.basePlugins;

import com.game.core.config.IOptPlugin;
import com.game.core.config.PluginGen;
import com.game.core.room.BaseTableVo;
import com.game.core.room.PayDetail;
import com.game.room.status.StepGameStatusData;
import com.lsocket.message.Response;

import java.util.List;

/**
 * 游戏中行为插件（吃碰杠胡）
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public abstract class AbstractActionPlugin<A extends BaseTableVo> implements IOptPlugin<A,StepGameStatusData> {
  //  protected Logger playLog = LoggerFactory.getLogger(LogType.Play.getLogName());
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
     * 0自摸所有人支付，1点炮的人支付
     * */
    public PayDetail payment(StepGameStatusData stepGameStatusData) {
        String str = getPlugin().getEffectStr();
        if (str == null || str.equals(""))
            return null;
        PayDetail ratePay = new PayDetail();
        return ratePay;
    }
}
