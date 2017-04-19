/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 *
 * @author leroy_boy
 */
public class Status {

    public static enum ServerStatus {

        /**
         * 停机维护
         */
        stoping(0),
        /**
         * 正常运行
         */
        runing(1);
        private final int val;

        private ServerStatus(int val) {
            this.val = val;
        }

        public static ServerStatus val(int val) {
            for (ServerStatus v : ServerStatus.values()) {
                if (val == v.getVal()) {
                    return v;
                }
            }
            return null;
        }

        public int getVal() {
            return val;
        }
    }

    public enum UserStatus {
        /**
         * 未创建
         */
        not_create(-1),
        /**
         * 正常
         */
        normal(0),
        /**
         * 禁言
         */
        gag(1),
        /**
         * 封号
         */
        freeze(2);
        private final int value;

        private UserStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        public static UserStatus indexOf(int val){
            for(UserStatus us : UserStatus.values()){
                if(us.getValue() == val){
                    return us;
                }
            }
            return freeze;
        }
    }

    public enum ScheStatus {

        /**
         * 未激活状态
         */
        close(0),
        /**
         * 未完成
         */
        noComplete(1),
        /**
         * 完成
         */
        complete(2);
        private final int value;

        private ScheStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ScheStatus val(int val) {
            for (ScheStatus v : ScheStatus.values()) {
                if (val == v.getValue()) {
                    return v;
                }
            }
            return null;
        }
    }

    public enum RewardStatus {

        /**
         * 未领取
         */
        unGet(0),
        /**
         * 已领取
         */
        Geted(1);
        private final int value;

        private RewardStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
