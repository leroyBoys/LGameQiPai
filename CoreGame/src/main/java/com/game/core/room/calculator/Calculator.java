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
         * 结算得分
         */
        score(0),
        /**
         * 叫地主次数
         */
        gurid(1),
        /**
         * 炸弹次数
         */
        four(2),
        /**
         * 火箭次数
         */
        boom(3),
        /**
         * 春天次数
         */
        spring(4),
        /**
         * 胜利次数
         */
        winCount(5),
        /**
         * 最高得分
         */
        maxAddSocre(6),
        ;
        public final int val;
        RecordType(int val){
            this.val = val;
        }
    }
}
