package com.game.core.room;

/**
 * Created by Administrator on 2017/5/19.
 */
public class PayDetail {
    /** 第几步 */
    private int step;
    /** 是否有效 */
    private boolean isValid = true;
    /** 支付分数的玩家 */
    private int[] fromUids;
    /** 获得分数的玩家 */
    private int toUid;
    /** 番 */
    private int rate;
    private PayType payType = PayType.Multiple;

    private int addScoreType;//得分名目一般为actionType或者actionType的子类
    private int lostScoreType;//失分名目

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int[] getFromUids() {
        return fromUids;
    }

    public void setFromUids(int[] fromUids) {
        this.fromUids = fromUids;
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

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public int getAddScoreType() {
        return addScoreType;
    }

    public void setAddScoreType(int addScoreType) {
        this.addScoreType = addScoreType;
    }

    public int getLostScoreType() {
        return lostScoreType;
    }

    public void setLostScoreType(int lostScoreType) {
        this.lostScoreType = lostScoreType;
    }

    public enum PayType{
        ADD, Multiple
    }
}
