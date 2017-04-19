/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.config.BaseXml;
import com.module.ItemData;
import com.module.Type;
import com.module.VirtualItemData;
import com.module.VirtualItemType;

import java.util.List;

/**
 *
 * @author leroy_boy
 */
public class StaticBox implements BaseXml {

    private int id;
    private String name;
    private String desc;
    private Type.BoxType type;
    private int needLevel = 0;
    private List<ItemData> openNeedItems;

    private List<BoxItemData> boxItems;

    @Override
    public boolean isTheSame(Object obj) {
        return false;
    }

    
    public static class BoxItemData  extends VirtualItemData {


        private boolean isNum;//绑定/是否百分比

        //概率权重值为0时必得
        private double rateValue;//概率权重值（最大值100 000）
        
        //计算使用 end>cur>=start
        private int start;
        private int end;

        public BoxItemData() {
        }
        

        public BoxItemData(VirtualItemType boxItemType, int keyId, double valueNum, boolean isNum, double rateValue) {
            super.setBoxItemType(boxItemType);
            super.setKeyId(keyId);
            super.setValueNum(valueNum);
            this.isNum = isNum;
            this.rateValue = rateValue;
        }

        public boolean isIsNum() {
            return isNum;
        }

        public void setIsNum(boolean isNum) {
            this.isNum = isNum;
        }

        public double getRateValue() {
            return rateValue;
        }

        public void setRateValue(double rateValue) {
            this.rateValue = rateValue;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public boolean isIsMstAdd() {
            
            return rateValue == 0 || rateValue == 100000;
        }


    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Type.BoxType getType() {
        return type;
    }

    public void setType(Type.BoxType type) {
        this.type = type;
    }

    public int getNeedLevel() {
        return needLevel;
    }

    public void setNeedLevel(int needLevel) {
        this.needLevel = needLevel;
    }

    public List<ItemData> getOpenNeedItems() {
        return openNeedItems;
    }

    public void setOpenNeedItems(List<ItemData> openNeedItems) {
        this.openNeedItems = openNeedItems;
    }

    public List<BoxItemData> getBoxItems() {
        return boxItems;
    }

    public void setBoxItems(List<BoxItemData> boxItems) {
        this.boxItems = boxItems;
    }


}
