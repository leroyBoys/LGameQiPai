/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.action;


import com.game.core.constant.GameConst;
import com.lsocket.handler.ModuleCmd;
import com.lsocket.manager.CMDManager;
import com.lsocket.module.HttpRequestType;

/**
 */
public enum GameCommCmd implements ModuleCmd {

    /**
     * 未定义
     */
    NULL(-1,true,HttpRequestType.tcp),

    CREATE_TABLE(1,true,HttpRequestType.tcp),
    /**
     * 加入游戏（进入桌子）
     */
    ENTER_GAME(1,true,HttpRequestType.tcp),
    /**
     * 退出游戏（退出桌子）
     */
    EXIT_GAME(2, true,HttpRequestType.tcp),
    /**
     * 开始游戏前的准备
     */
    READY_NOW(4, true,HttpRequestType.tcp),
    /**
     * 出牌
     */
    CARDS_TURN(5, true,HttpRequestType.tcp),
    /**
     * 托管
     */
    AUTO_PLAY(6, true,HttpRequestType.tcp),
    /**
     * 发牌
     */
    DISTRIBUTECARD(8,true,HttpRequestType.tcp),
    /**
     * 倍数
     */
    MULTIPLEVALUE(9,true,HttpRequestType.tcp),
    /**
     * 加入游戏（中途进入桌子）
     */
    ENTER_GAME_MIDDLE(12, true,HttpRequestType.tcp),
    ;

    private final int value;
    private final boolean isRequireOnline;//是否需要登录
    private final HttpRequestType requetType;//请求方式

    GameCommCmd(int value,boolean isRequireOnline,HttpRequestType requetType) {
        this.value = value;
        this.requetType = requetType;
        this.isRequireOnline = isRequireOnline;
    }

    public int getValue() {
        return value;
    }

    public boolean isRequireOnline() {
        return isRequireOnline;
    }

    public HttpRequestType getRequetType() {
        return requetType;
    }

    @Override
    public int getCmd_c() {
        return CMDManager.getCmd_M(GameConst.MOUDLE_GameComm,value);
    }
}
