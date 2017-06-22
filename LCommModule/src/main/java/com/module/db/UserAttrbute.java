/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.mysql.impl.DbFactory;

import java.sql.ResultSet;

/**
 *
 * @author leroy_boy
 */
public class UserAttrbute extends DbFactory implements java.io.Serializable {
    public static final UserAttrbute instance = new UserAttrbute();

    private int id;
    private String address;
    private String email;
    private String identityCard;
    private int mobile;
    private int qq;//Type.role
    private String realName;
    private byte sex;

    public UserAttrbute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getQq() {
        return qq;
    }

    public void setQq(int qq) {
        this.qq = qq;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    @Override
    public UserAttrbute create(ResultSet rs) throws Exception {
        UserAttrbute userAttrbute = createNew();
        userAttrbute.setId(rs.getInt("id"));
        userAttrbute.setAddress(rs.getString("address"));
        userAttrbute.setEmail(rs.getString("email"));
        userAttrbute.setIdentityCard(rs.getString("identity_card"));
        userAttrbute.setMobile(rs.getInt("mobile"));
        userAttrbute.setQq(rs.getInt("qq"));
        userAttrbute.setRealName(rs.getString("real_name"));
        userAttrbute.setSex(rs.getByte("sex"));
        return userAttrbute;
    }

    @Override
    protected UserAttrbute createNew() {
        return new UserAttrbute();
    }
}
