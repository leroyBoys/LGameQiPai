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
 * 连续签到
 * @author leroy
 */
@Resource
public class StaticContinueSignReward{
    @Id
    private String icon;
    private String num;
    private String desc;
    private List<ItemData> datas;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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
