/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;


import com.config.annotation.Id;
import com.config.annotation.Resource;
import com.module.ConditionData;
import com.module.ItemData;

import java.util.List;

/**
 *
 * @author leroy_boy
 */
@Resource
public class StaticAchievement {

    @Id
    private int id;
    private String name;
    private String icon_id;
    private int type;//累计/单次
    private List<ConditionData> conditions;//条件
    private String desc;
    private List<ItemData> rewardItems;//itemID:num:bongding,itemID:num:bangding,

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(String icon_id) {
        this.icon_id = icon_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ConditionData> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionData> conditions) {
        this.conditions = conditions;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ItemData> getRewardItems() {
        return rewardItems;
    }

    public void setRewardItems(List<ItemData> rewardItems) {
        this.rewardItems = rewardItems;
    }

}
