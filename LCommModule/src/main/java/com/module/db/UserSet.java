/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.module.SetKey;
import com.mysql.impl.DbFactory;

import java.sql.ResultSet;

/**
 *
 * @author leroy
 */
public class UserSet extends DbFactory {
    public static final UserSet instance = new UserSet();

    private int uid;
    private SetKey setKey;

    private int cid;//子id
    private String val;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public SetKey getSetKey() {
        return setKey;
    }

    public void setSetKey(SetKey setKey) {
        this.setKey = setKey;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public UserSet create(ResultSet rs) throws Exception {
        UserSet ut = createNew();
        ut.setCid(rs.getInt("cid"));
        ut.setSetKey(SetKey.parse(rs.getInt("pid")));
        ut.setVal(rs.getString("val"));
        return ut;
    }

    @Override
    protected UserSet createNew() {
        return new UserSet();
    }
}
