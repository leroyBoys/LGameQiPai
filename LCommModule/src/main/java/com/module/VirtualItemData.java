/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 *  虚拟道具（服务器与客户端对应关系）
 * @author leroy
 */
public class VirtualItemData {

    private VirtualItemType boxItemType;//宝箱道具类型
    private int keyId;//（类型对应）道具id
    private double valueNum;//（类型对应）道具数量

    public VirtualItemType getBoxItemType() {
        return boxItemType;
    }

    public void setBoxItemType(VirtualItemType boxItemType) {
        this.boxItemType = boxItemType;
    }


    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public double getValueNum() {
        return valueNum;
    }

    public void setValueNum(double valueNum) {
        this.valueNum = valueNum;
    }

}
