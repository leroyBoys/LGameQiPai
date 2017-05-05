/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.module.Status;
import com.mysql.impl.DbFactory;

import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author leroy
 */
public class RoleInfo extends DbFactory{
    public static final RoleInfo instance = new RoleInfo();

    private int id;
    private int uid;
    private String userAlise;
    private byte[] userHead;
    private String headImage;
    private int userLv;
    private long userExp;
    private int vipLevel;
    /**
     * vip到期时间 小于0无限期
     */
    private long vipEndTime;
    private int userSex;
    private Date createDate;
    private Status.UserStatus userStatus;//Status.userStatus

    private int strength;//体力
    private String signature;//签名
    private int skillPoint;//技能点

    public RoleInfo() {
    }

    @Override
    public RoleInfo create(ResultSet rs) throws Exception {
        RoleInfo role = createNew();
        role.setCreateDate(rs.getDate("create_date"));
        role.setHeadImage(rs.getString("head_image"));
        role.setId(rs.getInt("id"));
        role.setUid(rs.getInt("uid"));
        role.setUserAlise(rs.getString("user_alise"));
        role.setUserExp(rs.getLong("user_exp"));
        role.setUserHead(rs.getBytes("user_head"));
        role.setUserLv(rs.getInt("user_lv"));
        role.setUserSex(rs.getInt("user_sex"));
        role.setUserStatus(Status.UserStatus.indexOf(rs.getInt("user_status")));
        role.setVipEndTime(rs.getLong("vip_end_time"));
        role.setVipLevel(rs.getInt("vip_level"));
        return role;
    }

    @Override
    protected RoleInfo createNew() {
        return new RoleInfo();
    }

    public RoleInfo(int uid, String userAlise, String headImage, int userSex, String signature,int strength) {
        this.uid = uid;
        this.userAlise = userAlise;
        this.headImage = headImage;
        this.userLv = 1;
        this.userSex = userSex;
        this.signature = signature;
        this.createDate = new Date();
        this.userStatus = Status.UserStatus.not_create;
        this.strength = strength;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getVipLevel() {
        if (vipEndTime < 0 || vipEndTime >= System.currentTimeMillis()) {
            return Math.max(vipLevel, 1);
        } else {
            return 0;
        }
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public boolean isVip() {
        return getVipLevel() > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAlise() {
        return userAlise;
    }

    public void setUserAlise(String userAlise) {
        this.userAlise = userAlise;
    }

    public byte[] getUserHead() {
        return userHead;
    }

    public void setUserHead(byte[] userHead) {
        this.userHead = userHead;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getUserLv() {
        return userLv;
    }

    public void setUserLv(int userLv) {
        this.userLv = userLv;
    }

    public long getUserExp() {
        return userExp;
    }

    public void setUserExp(long userExp) {
        this.userExp = userExp;
    }

    public long getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(long vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Status.UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Status.UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getSkillPoint() {
        return skillPoint;
    }

    public void setSkillPoint(int skillPoint) {
        this.skillPoint = skillPoint;
    }

}
