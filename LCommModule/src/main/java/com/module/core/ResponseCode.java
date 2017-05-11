/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.core;

import com.lsocket.message.ErrorCode;
import com.lsocket.message.Response;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leroy
 */
public class ResponseCode {

    public static enum Error implements ErrorCode<Response>{
        succ(0),
        @Comment("玩家不存在")
        player_no_exit(1),

        @Comment("非法参数")
        parmter_error(2),

        @Comment("授权失效")
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        key_error(3),

        @Comment("服务器繁忙（网络堵塞请稍后再试）")
        server_busy(4),

        @Comment("超时重新登陆")
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        login_timeout(5),

        @Comment("掉线需要重新连")
        connect_again_require(6),

        @Comment("封号")
        free_now(7),

        @Comment("客户端数量过多")
        one_server_too_connect(8),

        @Comment("服务器人数过多，请换其他区试试")
        server_too_busy(9),

        @Comment("你的账号在其他地方登陆")
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        other_login(10),

        @Comment("你的账号掉线了")
        @ShowTip(Value = ShowTip.Tip.loginAgain)
        is_break(11),

        @Comment("账号已经存在")
        user_exit(12),

        @Comment("对方不在线")
        other_not_onLine(13),

        @Comment("房间不存在")
        room_not_exit(14),

        @Comment("人数已满")
        room_is_full(15),

        @Comment("桌子不存在")
        table_not_exit(16)
        ;
        private final int code;

        private Error(int code) {
            this.code = code;
        }

        public int value() {
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

        @Override
        public Response getMsg() {
            return Response.defaultResponse(getCodeValue(this));
        }
    }
    public final static Map<Integer, Integer> codevalues = new HashMap<>();

    /**
     * 返回错误码-描述对照
     * @return
     */
    public static Map<Integer,String> initCodeValues() {
        if (!codevalues.isEmpty()) {
            return null;
        }
        Map<Integer,String> codeValueDescMap = new HashMap<>();

        Field[] fields = Error.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isEnumConstant()) {
                int key = Error.valueOf(field.getName()).value();

                if (field.isAnnotationPresent(ShowTip.class)) {
                    ShowTip tip = field.getAnnotation(ShowTip.class);
                    if (tip == null) {
                        codevalues.put(key, key + ShowTip.Tip.onlyTip.getValue() * 1000);
                    } else {
                        codevalues.put(key, key + tip.Value().getValue() * 1000);
                    }
                } else {
                    codevalues.put(key, key + ShowTip.Tip.onlyTip.getValue() * 1000);
                }

                Comment tip = field.getAnnotation(Comment.class);
                String desc = "";
                if(tip == null){
                    desc = tip.value();
                }
                codeValueDescMap.put(codevalues.get(key),desc);
            }
        }

        return codeValueDescMap;
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
