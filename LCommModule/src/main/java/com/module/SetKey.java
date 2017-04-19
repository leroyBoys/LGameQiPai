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
public enum SetKey {
    NUll(0),
    /**
     * 技能槽
     */
    skill(1),
    /**
     * 系统设置
     */
    systemSet(2),
    /**
     * 能量槽
     */
    energy(3)
    ;
    private int Type;

    private SetKey(int Type) {
        this.Type = Type;
    }

    public int getValue() {
        return this.Type;
    }

    public static SetKey parse(int val) {
        for (SetKey ct : values()) {
            if (ct.getValue() == val) {
                return ct;
            }
        }
        return NUll;
    }
 
}
