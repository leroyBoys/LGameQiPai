/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 *
 * @author leroy
 */
public class RankType {

    private final String key;

    private RankType() {
        this.key = "";
    }

    private RankType(String key) {
        this.key = key;
    }

    public static RankType valueOf(RankFunctionType type, String... ids) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());
        if (ids != null && ids.length > 0) {
            int i = 0;
            for (String id : ids) {
                if (i != 0) {
                    sb.append("_");
                }
                sb.append(id);
                i++;
            }
        }
        return new RankType(sb.toString());
    }

    public String getKey() {
        return key;
    }

    public static enum RankFunctionType {

        /**
         * 跳跃高度
         */
        jumpHeigh(1),
        /**
         * 好友
         */
        Friend_Fortune(2),
        /**
         * 充值
         */
        ReCharge(3);

        private RankFunctionType(int key) {
            this.key = key;
        }

        private final int key;

        public int getKey() {
            return key;
        }
    }
}
