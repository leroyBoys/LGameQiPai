/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * 品质
 *
 * @author leroy_boy
 */
public enum Quality {

    normal(0);
    private final int val;
    
    private Quality(int val) {
        this.val = val;
    }
    public int getVal() {
        return val;
    }
}
