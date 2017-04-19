/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 客户短提示逻辑处理(默认纯提示)
 * @author leroy
 */
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ShowTip {
    public Tip Value() default Tip.onlyTip;
    /**
     * 错误返回操作类型
     */
    public static enum Tip {
        /**
         * 不提示
         */
        noTip(1),
        /**
         * 纯提示
         */
        onlyTip(2),
        /**
         * 重新登陆
         */
        loginAgain(3);
        private final int value;

        private Tip(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }
}
