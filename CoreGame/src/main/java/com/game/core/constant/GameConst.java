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


    public static final int ACTION_TYPE_TURN = 6;
    public static enum Gang{
        MingGang(231),
        AnGang(232),
        JianGang(233),
        SiFengGang(234),
        SanJianKe(235);

        private final int val;
        Gang(int val){
            this.val = val;
        }

        public int getVal(){
            return val;
        }
    }
}