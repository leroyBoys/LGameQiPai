/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * 虚拟道具（服务器与客户端对应关系）
 * @author leroy
 */
public enum VirtualItemType {
    /**
     * 自定义
     */
    NULL(0),
    /**
     * 道具
     */
    item(1),
    /**
     * 效果
     */
    effect(2);
    private final int val;

    private VirtualItemType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
