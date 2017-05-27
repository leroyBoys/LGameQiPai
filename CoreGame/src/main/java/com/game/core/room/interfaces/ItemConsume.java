package com.game.core.room.interfaces;

/**
 * Created by Administrator on 2017/5/27.
 */
public interface ItemConsume {
    public boolean checkCard(int roleId,int needCardCount);

    public void removeCard(int roleId,int needCardCount);

    public static class TestItemConsume implements ItemConsume{
        private static final TestItemConsume intance = new TestItemConsume();
        private TestItemConsume(){}

        public static final TestItemConsume getIntance(){
            return intance;
        }


        @Override
        public boolean checkCard(int roleId, int needCardCount) {
            return true;
        }
        @Override
        public void removeCard(int roleId, int needCardCount) {
        }
    }
}
