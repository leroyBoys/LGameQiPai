package com.game.core.constant;

import java.util.Date;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class GameConst {
    /** 系统module */
    public static final int MOUDLE_System = 0;
    /** 系统登录module */
    public static final int MOUDLE_Login = 1;
    /** 用户信息module */
    public static final int MOUDLE_User = 2;
    /**  聊天module */
    public static final int MOUDLE_Chat = 3;
    /** 游戏共有流程module */
    public static final int MOUDLE_GameComm = 10;
    /** 麻将module */
    public static final int MOUDLE_Mj = 11;
    /**  斗地主module */
    public static final int MOUDLE_Ddz = 12;

    /**  准备阶段 */
    public static final int ACTION_TYPE_READY= 4;
    /**  发牌 */
    public static final int ACTION_TYPE_FAPAI= 5;
    /** 轮到谁 */
    public static final int ACTION_TYPE_TURN = 6;

    public final static class MJ{
        /** 定庄 */
        public static final int ACTION_TYPE_DINGZHUANG = 201;
        /** 压跑 */
        public static final int ACTION_TYPE_YAPao = 205;
        /** 摸牌*/
        public static final int ACTION_TYPE_MOPAI = 211;
        /** 可操作集合 */
        public static final int ACTION_TYPE_CanDoActions = 212;
        /** 打/弃牌 */
        public static final int ACTION_TYPE_DA = 213;
        /** 碰 */
        public static final int ACTION_TYPE_PENG = 214;
        /** 过 */
        public static final int ACTION_TYPE_GUO = 215;
        /** 吃 */
        public static final int ACTION_TYPE_CHI = 220;
        /** 杠 */
        public static final int ACTION_TYPE_GANG = 230;
        public static final int ACTION_TYPE_GANG_MingGang = 231;
        public static final int ACTION_TYPE_GANG_AnGang = 232;
        public static final int ACTION_TYPE_GANG_JianGang = 233;//捡杠-过手杠
        public static final int ACTION_TYPE_GANG_SiFengGang = 234;//四风
        public static final int ACTION_TYPE_GANG_SanJianKe = 235;//三剑客
        public static final int ACTION_TYPE_GANG_DaGang = 236;//大杠
        /** 听牌 */
        public static final int ACTION_TYPE_TING = 240;
        /** 胡牌 */
        public static final int ACTION_TYPE_HU = 250;

    }

    public static class Weight{
        public static final int CHI = 1<<2;
        public static final int PENG = CHI<<1;
        public static final int GANG = PENG<<1;
        public static final int HU_TING = GANG<<1;
    }


    /**
     * 根据离线时间判断当前是否还可以重连（此时玩家已经处于离线状态）
     *
     * @param loginoffTime
     * @return
     */
    public static boolean isCanReConnect(Date loginoffTime) {
        return loginoffTime == null || loginoffTime.getTime() + Time.reconnect_time_max * 1000 - System.currentTimeMillis() > 0;
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


        /** 麻将游戏内倒计时（毫秒 ） **/
        public static final int MJ_WAIT_SECONDS = 10*1000;

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