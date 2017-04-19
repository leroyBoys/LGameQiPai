/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * 用户来源
 * @author leroy_boy
 */
public enum FromType {
    /**
     * 自己
     */
    self(0),
    /**
     * 腾讯
     */
    tx(1),
    /**
     * 360
     */
    safe(2),
    ;
    private final int  code;

    private FromType(int code) {
        this.code = code;
    }

    public int val() {
        return code;
    }
    
}
