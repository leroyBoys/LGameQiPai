/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 * 题目类型
 *
 * @author leroy
 */
public enum QuestionType {
    /**
     * 选择
     */
    choice(1),
    /**
     * 判断
     */
    judge(2),
    /**
     * 连线
     */
    line(3),
    /**
     * 填空
     */
    fillBlanks(4);
    private final int val;

    private QuestionType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public QuestionType valueofVal(int val) {
        for (QuestionType object : QuestionType.values()) {
            if (object.val == val) {
                return object;
            }
        }
        return choice;
    }
}
