package com.game.core.constant;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class GameConst {
    public static final int MOUDLE_System = 0;
    public static final int MOUDLE_Login = 1;
    public static final int MOUDLE_User = 2;
    public static final int MOUDLE_GameComm = 10;
    public static final int MOUDLE_Mj = 11;
    public static final int MOUDLE_Ddz = 12;


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

}