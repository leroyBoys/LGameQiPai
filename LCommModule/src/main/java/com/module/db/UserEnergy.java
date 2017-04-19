/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

/**
 *
 * @author leroy
 */
public class UserEnergy {

    private int uid;
    private int energyId;//技能id
    private long endTime;//有效期：-1无限

    /**
     * 是否有效
     * @return 
     */
    public boolean isValid(){
        return endTime == -1 || endTime > System.currentTimeMillis();
    }
    
    
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getEnergyId() {
        return energyId;
    }

    public void setEnergyId(int energyId) {
        this.energyId = energyId;
    }
}
