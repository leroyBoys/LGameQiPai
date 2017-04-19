/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * 道具使用效果对象
 *
 * @author leroy_boy
 */
public class EffectData {

    private EffectType effectType;//效果类型
    private Type type = Type.NULL;//
    private Object value;
    private int count = 1;//持续回合数
    

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public Type getType() {
        return type;
    }

    public void setType(int type) {
        this.type = Type.valOf(type);
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

    public static enum Type {

        /**
         * 未定义
         */
        NULL(0),
        /**
         * 百分比
         */
        per(1),
        /**
         * 数值
         */
        num(2);
        private final int val;

        private Type(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static Type valOf(int va) {
            for (Type t : Type.values()) {
                if (t.getVal() == va) {
                    return t;
                }
            }
            return NULL;
        }

    }
}
