/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * 条件内容
 *
 * @author leroy_boy
 */
public class ConditionData {

    private TargetType targetType;
    private Object type_value;

    public Object getType_value() {
        return type_value;
    }

    public void setType_value(Object type_value) {
        this.type_value = type_value;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

}
