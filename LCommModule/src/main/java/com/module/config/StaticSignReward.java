/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.annotation.Id;
import com.lgame.util.load.annotation.Resource;
import com.module.ItemData;

import java.util.List;

/**
 * 每日签到
 * @author leroy
 */
@Resource
public class StaticSignReward{
    @Id
    private int id;
    private String icon;
    private String dateTime;
    private String desc;
    private List<ItemData> datas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<ItemData> getDatas() {
        return datas;
    }

    public void setDatas(List<ItemData> datas) {
        this.datas = datas;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
