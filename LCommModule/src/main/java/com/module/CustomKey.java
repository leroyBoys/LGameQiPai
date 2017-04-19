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
public enum CustomKey {
    NUll(0),
    /**
     * 每日签到记录
     */
    sign_record(1),
    /**
     * 连续签到记录
     */
    conntinu_record(2),
    /**
     * 限购记录
     */
    shopLimit(3),
    /**
     * 全区限购记录
     */
    shopLimitWorld(4)
    
    ;
    private int Type;

    private CustomKey(int Type) {
        this.Type = Type;
    }

    public int getValue() {
        return this.Type;
    }

    public static CustomKey parse(int val) {
        for (CustomKey ct : values()) {
            if (ct.getValue() == val) {
                return ct;
            }
        }
        return NUll;
    }
}
