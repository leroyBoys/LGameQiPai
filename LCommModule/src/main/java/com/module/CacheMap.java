/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author leroy
 */
public class CacheMap<K,V> extends ConcurrentHashMap<K,V>{
    private Date updateDate;
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
