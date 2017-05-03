package com.game.action;

import com.game.socket.module.UserVistor;
import com.lsocket.handler.CmdModule;
import com.lsocket.message.Request;
import com.lsocket.message.Response;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public abstract class GameCmdModule extends CmdModule<UserVistor,Request,Response> {
}
