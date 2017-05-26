package com.game.core.config;

import com.lgame.util.load.annotation.Id;
import com.lgame.util.load.annotation.Resource;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
@Resource(suffix="xls",dataFromLine=3)
public class PluginGen {
    @Id
    private int tempId = 0;
    private String gameId;
    private String pluginName;
    /** 哪个操作的插件 */
    private String actionType;
    /** 插件执行的判定条件 */
    private String conditionStr;
    /** 插件执行的判定条件的类 */
    private String pluginClass;
    /** 插件执行的效果 */
    private String effectStr;

    private int subType = 0;
    private int canDoType = 0; //客户端可做的操作

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getConditionStr() {
        return conditionStr;
    }

    public void setConditionStr(String conditionStr) {
        this.conditionStr = conditionStr;
    }

    public String getPluginClass() {
        return pluginClass;
    }

    public void setPluginClass(String pluginClass) {
        this.pluginClass = pluginClass;
    }

    public String getEffectStr() {
        return effectStr;
    }

    public void setEffectStr(String effectStr) {
        this.effectStr = effectStr;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public int getCanDoType() {
        return canDoType;
    }

    public void setCanDoType(int canDoType) {
        this.canDoType = canDoType;
    }
}
