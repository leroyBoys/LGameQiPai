/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.config.BaseXml;
import com.module.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author leroy
 */
public class StaticShopItem implements BaseXml {

    private int id;
    private int itemId;
    private String name;
    private String icon;//icon
    private int hot;//热度
    private String desc;//描述
    private int price;//原价价格
    private double discount;//折扣

    private Type.PriceType priceType;//价格类型
    private List<Type.ShopTabType> tabs;//标签页

    private int limitMaxNum;//限购单位数量0无限
    private int unitNum;//单位数量
    private int vipLv;//需要vip等级
    private boolean used;//购买后是否直接使用

    private Type.HotType hotType;//热卖类型：0普通，1打折，2：热卖，3：推荐，4限时5限购
    private Date upTime;//上架时间
    private Date downTime;//下架时间

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount == 0 ? 1 : discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Type.PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(Type.PriceType priceType) {
        this.priceType = priceType;
    }

    public List<Type.ShopTabType> getTabs() {
        if (tabs == null) {
            tabs = new ArrayList<>();
            tabs.add(Type.ShopTabType.second);
        }
        return tabs;
    }

    public void setTabs(List<Type.ShopTabType> tabs) {
        this.tabs = tabs;
    }

    public int getLimitMaxNum() {
        return limitMaxNum;
    }

    public void setLimitMaxNum(int limitMaxNum) {
        this.limitMaxNum = limitMaxNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Type.HotType getHotType() {
        return hotType;
    }

    public void setHotType(Type.HotType hotType) {
        this.hotType = hotType;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }

    public boolean iExit() {
        if (getUpTime() == null || (getUpTime().getTime() - System.currentTimeMillis()) / 1000 <= 0) {//显示
            if (getDownTime() == null || (getDownTime().getTime() - System.currentTimeMillis()) / 1000 > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTheSame(Object obj) {
        return false;
    }

}
