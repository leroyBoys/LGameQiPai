package com.game.action.dao.mysql.template;

/**
 * Created by Administrator on 2017/4/15.
 */
public class ServerTemplate {
    public final static String GET_SERVER_BY_GROUP = "select * from server where group = ?";
    public final static String GET_SERVER_BY_ID = "SELECT * FROM game_server WHERE id =?";
    public final static String GET_SERVERGROUP = "SELECT * FROM servergroup WHERE `GROUP` = ?";
}
