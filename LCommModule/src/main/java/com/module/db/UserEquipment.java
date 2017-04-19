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
public class UserEquipment {
    private int id;
    private int uid;
    private int itemId;
    private boolean isBing;
    private Type.PositionType positionType;
    
    
    public UserEquipment() {
    }
    public UserEquipment(int id, int uid, int itemId) {
        this.id = id;
        this.uid = uid;
        this.itemId = itemId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type.PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(Type.PositionType positionType) {
        this.positionType = positionType;
    }

    public boolean isIsBing() {
        return isBing;
    }

    public void setIsBing(boolean isBing) {
        this.isBing = isBing;
    }
    
    
}
