package com.game.core.room.calculator;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/27.
 */
public interface Calculator {
    public void clear();
    public void addPayDetailed(PayDetail ratePay);
    public Object executeCalculator();

    public void addRecord(int roleId, RecordType recordType, int count);

    public void setRecord(int roleId, RecordType recordType, int value);

    public void addCurRecord(int uid,RecordType recordType,int count);

    public void setCurRecord(int uid,RecordType recordType,int value);

    public void addScore(int uid,int score);

    public enum RecordType{
        /**
         * 本次得分
         */
        score(0),
        /**
         * 点炮次数
         */
        dianPaoCount(1),
        /**
         * 自摸次数
         */
        ziMoCount(2),
        /**
         * 抢杠次数
         */
        qiangGang(3),
        /**
         * 暗杠次数
         */
        anGang(4),
        /**
         * 明杠次数
         */
        mingGang(5),
        /**
         * 点杠次数
         */
        dianGang(6),
        ;
        public final int val;
        RecordType(int val){
            this.val = val;
        }
    }
}
