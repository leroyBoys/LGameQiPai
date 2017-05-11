package com.game.core.config;

import com.lgame.util.load.annotation.Id;
import com.lgame.util.load.annotation.Resource;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
@Resource(suffix="xls",dataFromLine=3)
public class pluginGen {
    @Id
    private int tempId = 0;
    private int gameId;
    private String pluginName;
    /** 哪个操作的插件 */
    private String actionType;
    /** 插件执行的判定条件 */
    private String conditionStr;
    /** 插件执行的判定条件的类 */
    private String pluginClass;
    /** 插件执行的效果 */
    private String effectStr;

    private int zimoFlag = 0;
    private int dianedFlag = 0;
    private int zimoedFlag = 0;
    private int dianFlag = 0;
    private int subType = 0;
    private int canDoType = 0; //客户端可做的操作

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
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

    public int getZimoFlag() {
        return zimoFlag;
    }

    public void setZimoFlag(int zimoFlag) {
        this.zimoFlag = zimoFlag;
    }

    public int getDianedFlag() {
        return dianedFlag;
    }

    public void setDianedFlag(int dianedFlag) {
        this.dianedFlag = dianedFlag;
    }

    public int getZimoedFlag() {
        return zimoedFlag;
    }

    public void setZimoedFlag(int zimoedFlag) {
        this.zimoedFlag = zimoedFlag;
    }

    public int getDianFlag() {
        return dianFlag;
    }

    public void setDianFlag(int dianFlag) {
        this.dianFlag = dianFlag;
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
