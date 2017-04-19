/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 *
 * @author leroy_boy
 */
public class ItemData {

    private int itemId;
    private int num;
    private boolean isBing;
    
    public ItemData() {
    }

    public ItemData(int itemId, int num) {
        this.itemId = itemId;
        this.num = num;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isIsBing() {
        return isBing;
    }

    public void setIsBing(boolean isBing) {
        this.isBing = isBing;
    }

}
