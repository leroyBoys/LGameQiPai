/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

/**
 *
 * @author leroy_boy
 */
public class UserToken {
    private int uid;
    private String key;
    private String ip;
    private long endTime=0l;

    public UserToken(int uid, String key, String ip) {
        this.uid = uid;
        this.key = key;
        this.ip = ip;
    }
    
    
    public UserToken() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

        
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    /**
     * 是否失效
     * @return 
     */
    public boolean Invalid(){
        return System.currentTimeMillis() - endTime>0;
    }
}
