/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * 排序类型
 *
 * @author leroy
 */
public enum SortType {

    /**
     * 升序
     */
    Asc(1),
    /**
     * 降序
     */
    Desc(2);

    private SortType(int key) {
        this.key = key;
    }

    private final int key;

    public int getKey() {
        return key;
    }
}
