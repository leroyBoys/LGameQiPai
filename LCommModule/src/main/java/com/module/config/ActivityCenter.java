/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.annotation.Id;
import com.lgame.util.load.annotation.Resource;
/**
 *
 */
@Resource
public class ActivityCenter{

    @Id
    private int id;
    private String name;
    private String title;
    private String appear_time; //出现时间
    private String start_time;//开始时间
    private String end_time;//结束时间
    private String details; //详情
    private String skip;//跳转

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppear_time() {
        return appear_time;
    }

    public void setAppear_time(String appear_time) {
        this.appear_time = appear_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

}
