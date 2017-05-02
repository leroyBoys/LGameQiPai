/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.config.annotation.Id;
import com.config.annotation.Resource;

/**
 * 副本房间
 *
 * @author leroy
 */
@Resource
public class StaticRoom {

    @Id
    private int id;
    private BattleFieldType battleFieldType;//副本题目类型????
    private String desc;
    private int questCollectionId;//题库id
    private int questNum;//题目数量
   // private boolean isRandom;//是否随机

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BattleFieldType getBattleFieldType() {
        return battleFieldType;
    }

    public void setBattleFieldType(BattleFieldType battleFieldType) {
        this.battleFieldType = battleFieldType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQuestCollectionId() {
        return questCollectionId;
    }

    public void setQuestCollectionId(int questCollectionId) {
        this.questCollectionId = questCollectionId;
    }

    public int getQuestNum() {
        return questNum;
    }

    public void setQuestNum(int questNum) {
        this.questNum = questNum;
    }
//
//    public boolean isIsRandom() {
//        return isRandom;
//    }
//
//    public void setIsRandom(boolean isRandom) {
//        this.isRandom = isRandom;
//    }

    public static enum BattleFieldType {
        /**
         * 普通
         */
        common(1),
        /**
         * 争分夺秒
         */
        fmbz(2),
        /**
         * 狭路相逢
         */
        xlxf(3),
        /**
         * boos战
         */
        boss(4)
        ;
        private final int key;

        private BattleFieldType(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

    }
}
