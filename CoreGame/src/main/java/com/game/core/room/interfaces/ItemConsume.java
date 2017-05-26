package com.game.core.room.interfaces;

import com.sun.mail.imap.protocol.Item;

/**
 * Created by Administrator on 2017/5/27.
 */
public interface ItemConsume {
    public boolean checkCard(int roleId,int needCardCount);

    public void removeCard(int roleId,int needCardCount);

    public static class TestItemConsume implements ItemConsume{
        @Override
        public boolean checkCard(int roleId, int needCardCount) {
            return true;
        }
        @Override
        public void removeCard(int roleId, int needCardCount) {
        }
    }
}
