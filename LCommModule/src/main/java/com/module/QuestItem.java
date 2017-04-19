/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 *
 * @author leroy
 */
public class QuestItem {
    private int num;//编号
    private String content;//内容

    public QuestItem(int num, String content) {
        this.num = num;
        this.content = content;
    }

    public QuestItem() {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
