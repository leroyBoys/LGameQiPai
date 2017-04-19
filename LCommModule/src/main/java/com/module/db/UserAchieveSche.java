/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.lgame.util.comm.StringTool;
import com.module.TargetType;

/**
 *
 * @author leroy
 */
public class UserAchieveSche {

    private int roleId;//角色id
    private int achieveId;//静态成就id
    private TargetType type;//目标类型
    private String sechedule;//进度情况

    public UserAchieveSche() {
    }

    public UserAchieveSche(int roleId, int achieveId, TargetType type) {
        this.roleId = roleId;
        this.achieveId = achieveId;
        this.type = type;
        this.sechedule = "";
    }

    public UserAchieveSche(int roleId, int achieveId, TargetType type, String sechedule) {
        this.roleId = roleId;
        this.achieveId = achieveId;
        this.type = type;
        this.sechedule = sechedule;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getAchieveId() {
        return achieveId;
    }

    public void setAchieveId(int achieveId) {
        this.achieveId = achieveId;
    }

    public TargetType getType() {
        return type;
    }

    public void setType(TargetType type) {
        this.type = type;
    }

    public String getSechedule() {
        return sechedule;
    }

    public void setSechedule(String sechedule) {
        this.sechedule = sechedule;
    }

    public boolean addSechedule(String id) {
        if (StringTool.isEmpty(sechedule)) {
            sechedule = id;
            return true;
        }
        String splitChar = StringTool.SIGN4;
        String[] array = sechedule.split(splitChar);
        for (String str : array) {
            if (str.equals(id)) {
                return false;
            }
        }
        sechedule += splitChar + id;
        return true;
    }
}
