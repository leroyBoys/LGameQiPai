/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.module.SetKey;

/**
 *
 * @author leroy
 */
public class UserSet {

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

}
