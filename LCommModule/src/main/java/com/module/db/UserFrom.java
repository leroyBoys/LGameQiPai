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
public class UserFrom extends DbFactory implements java.io.Serializable {
    public static final UserFrom intance = new UserFrom();

    private int id;
    private String userSrc;
    private String serialNum;
    private String info;
    private Date createDate;

    public UserFrom() {
    }

    @Override
    public <T extends DbFactory> T create(ResultSet rs) throws Exception {
        UserFrom userFrom = createNew();
        return null;
    }

    @Override
    protected UserFrom createNew() {
        return new UserFrom();
    }

    public UserFrom(int id, String userSrc, String serialNum, String info, Date createDate) {
        this.id = id;
        this.userSrc = userSrc;
        this.serialNum = serialNum;
        this.info = info;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserSrc() {
        return userSrc;
    }

    public void setUserSrc(String userSrc) {
        this.userSrc = userSrc;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
