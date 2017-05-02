/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;


import com.config.annotation.Resource;

/**
 * 杂表
 * @author leroy
 */
@Resource
public class StaticConfig {
    
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
    
}
