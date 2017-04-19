/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

/**
 * 杂表
 * @author leroy
 */
public enum ZabiaoConfigType {
    /**
     * 补签开心币基础数值
     */
    buSignbase(1),;
    private final int val;

    private ZabiaoConfigType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static ZabiaoConfigType valOf(int v) {
        for (ZabiaoConfigType ss : ZabiaoConfigType.values()) {
            if (ss.getVal() == v) {
                return ss;
            }
        }
        return buSignbase;
    }
}
