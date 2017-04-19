/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.config.BaseXml;

/**
 * 杂表
 * @author leroy
 */
public class StaticConfig implements BaseXml {
    
    private ZabiaoConfigType type;
    
    private String content;

    public ZabiaoConfigType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = ZabiaoConfigType.valueOf(type);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
    
    @Override
    public boolean isTheSame(Object obj) {
        if(obj == null){
            return false;
        }
        StaticConfig _obj = (StaticConfig)obj;
        return _obj.getType() == this.getType();
    }
    
}
