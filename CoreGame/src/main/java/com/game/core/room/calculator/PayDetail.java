package com.game.core.room.calculator;

import com.game.core.config.IOptPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
public class PayDetail {
    /** 第几步 */
    private int step;
    private IOptPlugin iOptPlugin;
    /** 是否有效 */
    private boolean isValid = true;
    /** 支付分数的玩家 */
    private List<Integer> fromUids;
    /** 是否是最终得失分玩家映射关系  */
    private boolean finalFirst = false;
    /** 是否为额外添加的得失分 */
    private boolean extraAdd = false;
    /** 获得分数的玩家 */
    private int toUid;
    /** 番 */
    private int rate;

    private PayType payType = PayType.Multiple;
    private int type;
    private int subType;
    private int lostScoreType;//失分名目


    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isValid() {
        if(!isValid){
            return false;
        }

        return true;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<Integer> getFromUids() {
        return fromUids;
    }

    public void setFromUids(List<Integer> fromUids) {
        this.fromUids = fromUids;
    }

    public void setFromUids(List<Integer> fromUids, boolean finalFirst) {
        this.fromUids = fromUids;
        this.finalFirst = finalFirst;
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public PayType getPayType() {
        return payType;
    }

    public boolean isFinalFirst() {
        return finalFirst;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public int getLostScoreType() {
        return lostScoreType;
    }

    public IOptPlugin getiOptPlugin() {
        return iOptPlugin;
    }

    public void setiOptPlugin(IOptPlugin iOptPlugin) {
        this.iOptPlugin = iOptPlugin;
    }

    public boolean isExtraAdd() {
        return extraAdd;
    }

    public void setExtraAdd(boolean extraAdd) {
        this.extraAdd = extraAdd;
    }

    public void setLostScoreType(int lostScoreType) {
        this.lostScoreType = lostScoreType;
    }

    public String toJson(){
        StringBuilder sb = new StringBuilder(" payDetail:step:");
        sb.append(this.getStep()).append(" rate:").append(rate).append(",").append(payType).append(",").append(getiOptPlugin().getPlugin().getPluginName());
        sb.append("toUid:").append(this.getToUid()).append(" fromids:").append(Arrays.toString(getFromUids().toArray()));
        return sb.toString();
    }
    public enum PayType{
        NULL,ADD, Multiple
    }
}
