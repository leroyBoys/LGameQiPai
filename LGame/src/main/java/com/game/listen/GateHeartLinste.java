package com.game.listen;

import com.lsocket.listen.HeartListen;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Administrator on 2017/4/25.
 */
public class GateHeartLinste implements HeartListen {
    @Override
    public boolean checkHeart(IoSession session) {
        return false;
    }
}
