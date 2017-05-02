/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.config.annotation.Resource;
import com.module.EffectData;
import com.module.Quality;
import com.module.Type;

import java.util.Date;
import java.util.List;

/**
 *
 * @author leroy_boy
 */
@Resource
public class StaticItem {

    private int id;
    private String name;
    private String icon_id;//道具icon
    private String itemDesc;
    private Type.ItemType itemType;
    private Type.SystemItemType systemItemType;
    private List<EffectData> effectDatas;

    private boolean isShow;//是否在背包显示
    private int level;//等级
    private boolean isStack = false;//是否允许堆叠
    private boolean isDel = false;//是否允许删除
    private Date endTime;//消失时间
    private int vaildTime;//有效期（秒，0为无限期）
    /**
     * 品质
     */
    private Quality quality;

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

    public boolean isIsStack() {
        return isStack;
    }

    public void setIsStack(boolean isStack) {
        this.isStack = isStack;
    }

    public void setIcon_id(String icon_id) {
        this.icon_id = icon_id;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public boolean isIsShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getVaildTime() {
        return vaildTime;
    }

    public void setVaildTime(int vaildTime) {
        this.vaildTime = vaildTime;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Type.ItemType getItemType() {
        return itemType;
    }

    public void setItemType(Type.ItemType itemType) {
        this.itemType = itemType;
    }

    public Type.SystemItemType getSystemItemType() {
        return systemItemType;
    }

    public void setSystemItemType(Type.SystemItemType systemItemType) {
        this.systemItemType = systemItemType;
    }

    public List<EffectData> getEffectDatas() {
        return effectDatas;
    }

    public void setEffectDatas(List<EffectData> effectDatas) {
        this.effectDatas = effectDatas;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

}
