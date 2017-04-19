/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leroy
 */
public class ResponseCode {

    public static enum Error {
        succ(0),
        /**
         * 玩家不存在
         */
        player_no_exit(1),
        /**
         * 非法参数
         */
        parmter_error(2),
        /**
         * 授权失效
         */
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        key_error(3),
        /**
         * 服务器繁忙（网络堵塞请稍后再试）
         */
        server_busy(4),
        /**
         * 超时重新登陆
         */
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        login_timeout(5),
        /**
         * 掉线需要重新连
         */
        connect_again_require(6),
        /**
         * 封号
         */
        free_now(7),
        /**
         * 客户端数量过多
         */
        one_server_too_connect(8),
        /**
         * 服务器人数过多，请换其他区试试
         */
        server_too_busy(9),
        /**
         * 你的账号在其他地方登陆
         */
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        other_login(10),
        /**
         * 你的账号掉线了
         */
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        is_break(11),
        /**
         * 账号已经存在
         */
        user_exit(12),
        /**
         * 玩家等级过低
         */
        role_level_to_low(13),
        /**
         * 请清空至少{0}个包裹
         */
        itemPackageTooSmal(14),
        /**
         * {0}数量不足{1}
         */
        item_not_enough(15),
        /**
         * 你的账号被禁言了
         */
        cantNotChat(16),
        /**
         * 不能给自己聊天
         */
        cantNotToSelf(17),
        /**
         * 今天已经签到过了
         */
        today_sign_repeat(18),
        /**
         * 道具已经改变{0},{1}....变动的码表道具id
         */
        @ShowTip(Value = ShowTip.Tip.noTip)
        item_change(19),
        //         /**
        //         * 道具已经改变{0},{1}....变动的玩家道具id
        //         */
        //        @ShowTip(Value = ShowTip.Tip.noTip)
        //        user_item_change(20)
        /**
         * 数据不同步，重新登陆
         */
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        dataNotSame(20),
        /**
         * 密码错误
         */
        pwd_error(21),
         /**
         * 商品{0}数量不够或者已下架
         */
        shop_item_not_enough(22),
        /**
         * 技能已达到最高等级
         */
        skill_is_top(23),
        /**
         * 你已经签过到了
         */
        buSignFail1(24),
        /**
         * 角色已存在
         */
        role_exit(25),
        /**
         * 该技能已达满级
         */
        skillToTop(26),
        /**
         * 你还没达到{0}级
         */
        levelToLowToStudySkill(27),
        /**
         * 缺少技能点
         */
        skillPointLittle(28),
        /**
         *  成就{0}没有达到完成条件
         */
        skillLessAchieve(29),
        /**
         * 该位置不能防止此类技能
         */
        skillPlaceError(30),
        /**
         * 你还没有获得{0}技能
         */
        notGetSkill(31),
        /**
         * 你还没有学习能量球{0}
         */
        notStudyEnergy(32),
        /**
         * 该能量球已关闭
         */
        notExitEnergy(33),
        /**
         * 对方不在线
         */
        other_not_onLine(34),
        /**
         * 当前不能答题
         */
        cantnotAnswer(35)
        ;
        private final int code;

        private Error(int code) {
            this.code = code;
        }

        public Integer value() {
            return code;
        }

        public static Error valueOf(int val) {
            for (Error error : Error.values()) {
                if (error.value() == val) {
                    return error;
                }
            }
            return null;
        }

    }
    public final static Map<Integer, Integer> codevalues = new HashMap<>();

    private static void initCodeValues() {
        if (!codevalues.isEmpty()) {
            return;
        }
        Field[] fields = Error.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isEnumConstant()) {

                if (field.isAnnotationPresent(ShowTip.class)) {
                    ShowTip tip = (ShowTip) field.getAnnotation(ShowTip.class);
                    int key = Error.valueOf(field.getName()).value();
                    if (tip == null) {
                        codevalues.put(key, key + ShowTip.Tip.onlyTip.getValue() * 1000);
                    } else {
                        codevalues.put(key, key + tip.Value().getValue() * 1000);
                    }
                } else {
                    int key = Error.valueOf(field.getName()).value();
                    codevalues.put(key, key + ShowTip.Tip.onlyTip.getValue() * 1000);
                }
            }
        }
    }

    public static int getCodeValue(Error error) {
        initCodeValues();
        Integer code = codevalues.get(error.value());
        if (code == null) {
            return -1;
        }
        return code;
    }

    private Error error;
    private int[] paratemers;

    public ResponseCode() {
    }

    public ResponseCode(Error error, int... paratemers) {
        this.error = error;
        this.paratemers = paratemers;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public int[] getParatemers() {
        return paratemers;
    }

    public void setParatemers(int[] paratemers) {
        this.paratemers = paratemers;
    }

}
