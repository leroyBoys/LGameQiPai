/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.config.BaseXml;

import java.util.List;

/**
 * 题库
 * @author leroy
 */
public class StaticQuestionCollection implements BaseXml {
    private int  id;//题库id
    private List<Integer> questIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getQuestIds() {
        return questIds;
    }

    public void setQuestIds(List<Integer> questIds) {
        this.questIds = questIds;
    }

    @Override
    public boolean isTheSame(Object obj) {
        return false;
    }
    
}
