/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

/**
 *
 * @author Administrator
 */
public class UserFriends {
     private int id;
     private int uid;
     private int friendsid;

    public UserFriends() {
    }

    public UserFriends(int id, int uid, int friendsid) {
        this.id = id;
        this.uid = uid;
        this.friendsid = friendsid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFriendsid() {
        return friendsid;
    }

    public void setFriendsid(int friendsid) {
        this.friendsid = friendsid;
    }
}
