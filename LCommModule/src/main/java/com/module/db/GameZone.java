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
public class GameZone extends DbFactory implements java.io.Serializable{
    public static final GameZone instance = new GameZone();

    private int id;
    private int version_id;
    private String zoneName;
    private String zoneIcon;
    private String zoneDesc;
    private int zoneNum;
    private String ip;
    private int port;
    private int udp_port;
    private int maxCount;
    private int cuCount;
    private byte serverStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneIcon() {
        return zoneIcon;
    }

    public void setZoneIcon(String zoneIcon) {
        this.zoneIcon = zoneIcon;
    }

    public String getZoneDesc() {
        return zoneDesc;
    }

    public void setZoneDesc(String zoneDesc) {
        this.zoneDesc = zoneDesc;
    }

    public int getZoneNum() {
        return zoneNum;
    }

    public void setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getCuCount() {
        return cuCount;
    }

    public void setCuCount(int cuCount) {
        this.cuCount = cuCount;
    }

    public byte getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(byte serverStatus) {
        this.serverStatus = serverStatus;
    }

    public int getUdp_port() {
        return udp_port;
    }

    public void setUdp_port(int udp_port) {
        this.udp_port = udp_port;
    }

    @Override
    public GameZone create(ResultSet rs) throws Exception {
        GameZone v = new GameZone();
        v.setCuCount(rs.getInt("cur_count"));
        v.setId(rs.getInt("id"));
        v.setIp(rs.getString("ip"));
        v.setMaxCount(rs.getInt("max_count"));
        v.setPort(rs.getInt("port"));
        v.setUdp_port(rs.getInt("udp_port"));
        v.setServerStatus(rs.getByte("server_status"));
        v.setVersion_id(rs.getInt("g_v_id"));
        v.setZoneDesc(rs.getString("zone_desc"));
        v.setZoneIcon(rs.getString("zone_icon"));
        v.setZoneName(rs.getString("zone_name"));
        v.setZoneNum(rs.getInt("zone_num"));
        return v;
    }

    @Override
    protected GameZone createNew() {
        return new GameZone();
    }
}
