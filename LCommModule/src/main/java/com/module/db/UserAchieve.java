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
public class UserAchieve {

    private int roleId;//角色id
    private int achieveId;//静态成就id
    private short status;//0未完成，1完成未领取，2完成并领取

    public UserAchieve(int roleId, int achieveId, short status) {
        this.roleId = roleId;
        this.achieveId = achieveId;
        this.status = status;
    }

    public UserAchieve() {
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

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

}
