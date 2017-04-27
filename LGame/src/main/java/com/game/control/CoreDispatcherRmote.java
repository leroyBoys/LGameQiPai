package com.game.control;

import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.message.Request;
import com.lsocket.module.Visitor;
import org.apache.mina.core.session.IdleStatus;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class CoreDispatcherRmote extends CoreDispatcher<Visitor,Request> {

    public void session_closed(Visitor visitor) {

    }

    public void sessionIdle(Visitor visitor, IdleStatus idleStatus) {

    }
}
