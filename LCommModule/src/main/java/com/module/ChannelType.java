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
public enum ChannelType {
    /**
     * 世界聊天
     */
    world(0),
    /**
     * 班级
     */
    classs(1),
    /**
     * 队伍聊天
     */
    group(2),
    /**
     * 私聊
     */
    privateChat(3),
    /**
     * 团队聊天
     */
    teamChat(4),
    /**
     * 广播
     */
    broadCast(5);
    private final int type;

    private ChannelType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ChannelType valueOf(int type) {
        for (ChannelType ct : values()) {
            if (type == ct.getType()) {
                return ct;
            }
        }
        return world;
    }

}
