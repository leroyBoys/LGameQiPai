/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

import com.mysql.impl.DbFactory;

import java.sql.ResultSet;

/**
 *
 * @author leroy_boy
 */
public class GameNotice extends DbFactory implements java.io.Serializable{
    public static final GameNotice instance = new GameNotice();

    private int id;
    private int type;
    private String titleName;
    private String titleDesc;
    private String startTime;
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitleName() {
        return titleName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    @Override
    public GameNotice create(ResultSet rs) throws Exception {
        GameNotice g = createNew();
        g.setEndTime(rs.getString("end_time"));
        g.setId(rs.getInt("id"));
        g.setStartTime(rs.getString("start_time"));
        g.setTitleDesc(rs.getString("title_desc"));
        g.setTitleName(rs.getString("title_name"));
        g.setType(rs.getInt("type"));
        return g;
    }

    @Override
    protected GameNotice createNew() {
        return new GameNotice();
    }
}
