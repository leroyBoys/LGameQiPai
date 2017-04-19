/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import java.util.Date;

/**
 *
 * @author leroy_boy
 */
public class UserInfo implements java.io.Serializable {
    private int id;
    private int deviceId;
    private int userFromId;
    private byte userFromType;//Type.UserFromType
    private String userName;
    private String userPwd;
    private byte role;//Type.role
    private String inviteCode;
    private byte userStatus;//Status.userStatus
    private Date statusEndTime;
    private boolean isOnline;
    private Date createDate;
    private Date loginTime;
    private Date loginOffTime;

    public UserInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(int userFromId) {
        this.userFromId = userFromId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public byte getRole() {
        return role;
    }

    public void setRole(byte role) {
        this.role = role;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(byte userStatus) {
        this.userStatus = userStatus;
    }

    public Date getStatusEndTime() {
        return statusEndTime;
    }

    public void setStatusEndTime(Date statusEndTime) {
        this.statusEndTime = statusEndTime;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLoginOffTime() {
        return loginOffTime;
    }

    public byte getUserFromType() {
        return userFromType;
    }

    public void setUserFromType(byte userFromType) {
        this.userFromType = userFromType;
    }

    public void setLoginOffTime(Date loginOffTime) {
        this.loginOffTime = loginOffTime;
    }
}
