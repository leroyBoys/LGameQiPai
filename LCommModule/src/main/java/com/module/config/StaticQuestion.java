/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.config.annotation.Id;
import com.config.annotation.Resource;
import com.module.QuestItem;
import com.module.QuestionType;

import java.util.List;

/**
 * 题目
 *
 * @author leroy
 */
@Resource
public class StaticQuestion {

    @Id
    private int id;
    private QuestionType questionType;
    private String questDesc;//题目描述
    private List<QuestItem> answerList1;
    private List<QuestItem> answerList2;
    /**
     * 正确答案:“,”分割（按顺序，没顺序的无所谓）如，选择：1,2，连线1:2,3:5
     *
     * 选择题：填写题目区域1中的正确答案编号；多个答案之间用","分割，如1,3 填空题：填空处用"()"代替，答案按顺序填写编号
     * 连线题：按区域1的顺序填写编号,如1-3,2-2或者1-3,2-3 对错提：1对，2错
     */
    private String rightAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public int getType() {
        return  questionType.getVal();
    }

    public String getQuestDesc() {
        return questDesc;
    }

    public void setQuestDesc(String questDesc) {
        this.questDesc = questDesc;
    }

    public List<QuestItem> getAnswerList1() {
        return answerList1;
    }

    public void setAnswerList1(List<QuestItem> answerList1) {
        this.answerList1 = answerList1;
    }

    public List<QuestItem> getAnswerList2() {
        return answerList2;
    }

    public void setAnswerList2(List<QuestItem> answerList2) {
        this.answerList2 = answerList2;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

}
