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
public class Version extends DbFactory implements java.io.Serializable{
    public final static Version intance = new Version();

    private int id;
    private int src_id;
    private int game_id;
    private String version;
    private String version_test;
    private String test_ip;
    private String res_url;
    private String down_url;
    private int sever_status;
    private String f_ids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSrc_id() {
        return src_id;
    }

    public void setSrc_id(int src_id) {
        this.src_id = src_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_test() {
        return version_test;
    }

    public void setVersion_test(String version_test) {
        this.version_test = version_test;
    }

    public String getTest_ip() {
        return test_ip;
    }

    public void setTest_ip(String test_ip) {
        this.test_ip = test_ip;
    }

    public String getRes_url() {
        return res_url;
    }

    public void setRes_url(String res_url) {
        this.res_url = res_url;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public int getSever_status() {
        return sever_status;
    }

    public void setSever_status(int sever_status) {
        this.sever_status = sever_status;
    }

    public String getF_ids() {
        return f_ids;
    }

    public void setF_ids(String f_ids) {
        this.f_ids = f_ids;
    }

    @Override
    public Version create(ResultSet rs) throws Exception {
        Version v = createNew();

        v.setDown_url(rs.getString("down_url"));
        v.setF_ids(rs.getString("f_ids"));
        v.setGame_id(rs.getInt("game_id"));
        v.setId(rs.getInt("id"));
        v.setRes_url(rs.getString("res_url"));
        v.setSever_status(rs.getInt("server_status"));
        v.setSrc_id(rs.getInt("src_id"));
        v.setTest_ip(rs.getString("test_ip"));
        v.setVersion(rs.getString("version"));
        v.setVersion_test(rs.getString("version_test"));
        return v;
    }

    @Override
    protected Version createNew() {
        return new Version();
    }
}
