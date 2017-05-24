package com.game.socket.listen;

import com.lsocket.listen.HeartListen;
import org.apache.mina.core.session.IoSession;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/3.
 */
public class GameHeartLinster implements HeartListen {
    @Override
    public boolean checkHeart(IoSession session) {
        return false;
    }
}
