/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.util;

import java.util.Date;

/**
 *
 * @author leroy_boy
 */
public class GameConst {

    /**
     * 根据离线时间判断当前是否还可以重连（此时玩家已经处于离线状态）
     *
     * @param loginoffTime
     * @return
     */
    public static boolean isCanReConnect(Date loginoffTime) {
        return loginoffTime == null || loginoffTime.getTime() + GameConst.Time.reconnect_time_max * 1000 - System.currentTimeMillis() > 0;
    }

    public static class Time {//单位秒

        /**
         * cache 失效时间
         */
        public static final int cacheVailTime = 300;
        /**
         * token 延长时间
         */
        public static final int key_last_second = 300;
        /**
         * 允许重连的最大时间
         */
        public static final int reconnect_time_max = 300;

        private static final char[] monthNum = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};

        public static char getMonthNum(int num) {
            return monthNum[num - 1];
        }

    }

    public static class Item {

        /**
         * 元宝
         */
        public static final int gold = 1;
        /**
         * 绑定元宝
         */
        public static final int bgold = 2;
        /**
         * 游戏币
         */
        public static final int money = 3;
        /**
         * 经验
         */
        public static final int exp = 4;
        /**
         * 技能点
         */
        public static final int skillPoint = 5;
        /**
         * 道具最大堆叠数量
         */
        public static final int stackItemMaxAmount = 99;

        public static String getItemChannelMsg(int itemid) {
            return "{" + 0 + ":" + itemid + "}";
        }

        /**
         * 是否在前台ui
         *
         * @param itemId
         * @return
         */
        public static boolean isOnMainUi(int itemId) {
            return itemId == money || itemId == gold;
        }
    }

    public static class Config {

        ;///是否向文件中添加日志
        /**
         * 默认用户密码
         */
        public static final String pwd = "8888";
        /**
         * 宝箱精度值(/100)
         */
        public static final int BoxPreNum = 100000;
        /**
         * 是否使用缓存数据库
         */
        public static final boolean isUseCacheDb = false;

        /**
         * 连续签到周期
         */
        public static final int signContinusCycle = 7;
        /**
         * 默认头像
         */
        public static final String DefaultHeadImag = "icon_Role_101000";
        public static int BB_DEFAULT_ID = 101000;//默认bbid

        /**
         * 根据实际的天数获得相对应的循环周期内的zhi
         *
         * @param realNum
         * @return
         */
        public static int getConCycleDayNum(int realNum) {
            int pre = realNum % signContinusCycle;
            if (pre == 0) {
                return signContinusCycle;
            }
            return pre;
        }

        /**
         * 获得体力上限
         *
         * @param userLv
         * @return
         */
        public static int getStrengthLimt(int userLv) {
            return 100;
        }

    }

    public static class DBConfig {

        /**
         * 存储过程，更新道具类型
         */
        public static final int SetItemType_Item = 1;
        /**
         * 存储过程，更新道具类型
         */
        public static final int SetItemType_equipment = 2;
    }

    /**
     * 系统功能id
     */
    public static class SystemFuntionId {

        /**
         * 好友消息推送id
         */
        public static int pushFriendMsgId = 10001;
        /**
         * 拒绝陌生人添加好友请求
         */
        public static int refruseNofriend = 10002;

    }

}
