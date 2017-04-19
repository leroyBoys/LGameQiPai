/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * buff效果对象
 *
 * @author leroy_boy
 */
public class BuffData {

    private BuffType buffType;//buff类型
    private EffectData.Type type = EffectData.Type.NULL;//
    private Object value;
    private int count=1;//持续回合数

    public BuffType getBuffType() {
        return buffType;
    }

    public void setBuffType(BuffType buffType) {
        this.buffType = buffType;
    }

    public EffectData.Type getType() {
        return type;
    }

    public void setType(EffectData.Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
