/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.mysql.impl.DbFactory;

import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author leroy_boy
 */
public class UserInfo extends DbFactory implements java.io.Serializable {
    public static final UserInfo instance = new UserInfo();

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
    private OnLineType onLineType;
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

    public OnLineType getOnLineType() {
        return onLineType;
    }

    public void setOnLineType(OnLineType onLineType) {
        this.onLineType = onLineType;
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

    @Override
    public UserInfo create(ResultSet rs) throws Exception {
        UserInfo userInfo = createNew();
        userInfo.setCreateDate(rs.getDate("create_date"));
        userInfo.setDeviceId(rs.getInt("device_id"));
        userInfo.setId(rs.getInt("id"));
        userInfo.setInviteCode(rs.getString("invite_code"));

        int onlinetype = rs.getInt("is_online");
        int length = OnLineType.values().length-1;
        userInfo.setOnLineType(OnLineType.values()[Math.min(length,onlinetype)]);
        userInfo.setLoginOffTime(rs.getDate("login_off_time"));
        userInfo.setLoginTime(rs.getDate("login_time"));
        userInfo.setRole(rs.getByte("role"));
        userInfo.setStatusEndTime(rs.getDate("status_endtime"));
        userInfo.setUserFromId(rs.getInt("user_from_id"));
        userInfo.setUserName(rs.getString("user_name"));
        userInfo.setUserStatus(rs.getByte("user_status"));

        return userInfo;
    }

    @Override
    protected UserInfo createNew() {
        return new UserInfo();
    }


    public enum OnLineType{
        waitLogin,login,offline
    }
}
