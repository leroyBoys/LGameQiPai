/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.module.Type;

/**
 *
 * @author leroy_boy
 */
public class UserItem {

    private int id;

    private int uid;
    private int itemId;
    private boolean isBing;//1绑定0非绑定
    private int amount;

    private Type.PositionType postionType;

    public UserItem() {
    }

    public UserItem(int uid, int itemId, boolean isBing, int amount) {
        this.uid = uid;
        this.itemId = itemId;
        this.isBing = isBing;
        this.amount = amount;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public boolean isIsBing() {
        return isBing;
    }

    public void setIsBing(boolean isBing) {
        this.isBing = isBing;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type.PositionType getPostionType() {
        return postionType;
    }

    public void setPostionType(Type.PositionType postionType) {
        this.postionType = postionType;
    }

}
