/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

/**
 *
 * @author leroy_boy
 */
public class Type {

    public static enum GameNoticeType {

        /**
         * 停机维护
         */
        stoping(0),
        /**
         * 正常运行
         */
        runing(1);
        private final int val;

        private GameNoticeType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static enum UserFromType {

        /**
         * 普通用户
         */
        normal(0),
        /**
         * 腾讯用户
         */
        qq(1);
        private final int val;

        private UserFromType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public enum RoleType {

        /**
         * 游客
         */
        gurid(0),
        /**
         * 普通
         */
        nomal(1),
        /**
         * GM
         */
        GM(2);
        private final int value;

        private RoleType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定义系统道具类型
     */
    public enum SystemItemType {
        /**
         * 普通
         */
        none(0),
        /**
         * 游戏币(游戏内流通货币)
         */
        money(1),
        /**
         * 元宝（人民币之间兑换非绑定）
         */
        gold(2),
        /**
         * 绑定元宝
         */
        bgold(3),
        /**
         * 经验
         */
        exp(4),
        /**
         * 技能点
         */
        skillPoint(5),
        
        ;
        private final int value;

        private SystemItemType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 道具类型
     */
    public static enum ItemType {
        /**
         * 虚拟道具不存库，根据情况插入不同数据表
         */
        virtual(-1),
        /**
         * 收集
         */
        normal(0),
        /**
         * 装备
         */
        equpment(1),
        /**
         * 礼包
         */
        gift(2),
        /**
         * 补给
         */
        effect(3),
        /**
         * 强化
         */
        strength(4);
        private final int val;

        private ItemType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    /**
     * 宝箱类型
     */
    public static enum BoxType {

        /**
         * 全部获得
         */
        normal(0),
        /**
         * 随机获得
         */
        randomRate(1);
        private final int val;

        private BoxType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static BoxType valOf(int v) {

            for (BoxType ss : BoxType.values()) {
                if (ss.getVal() == v) {
                    return ss;
                }
            }
            return normal;
        }

    }

    /**
     * 道具使用效果类型
     */
    public static enum ItemEffectType {

        /**
         * 无效果
         */
        none(0),
        /**
         * 增加金币
         */
        addgold(1),
        /**
         * 增加道具
         */
        addItem(2),
        /**
         * 失去道具
         */
        loseItem(3);
        private final int val;

        private ItemEffectType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

//    /**
//     * 包裹类型
//     */
//    public static enum PackageType {
//
//        /**
//         * 普通道具包裹
//         */
//        items(0),
//        /**
//         * 第二包裹,
//         */
//        second(1),
//        /**
//         * 功能
//         */
//        function(2);
//        private final int val;
//
//        private PackageType(int val) {
//            this.val = val;
//        }
//
//        public int getVal() {
//            return val;
//        }
//    }
    public static enum PositionType {
        /**
         * 普通包裹内
         */
        itemPackage(0),
        /**
         * 身上
         */
        body(1);
        private final int val;

        private PositionType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static PositionType valueOf(int val) {
            for (PositionType p : values()) {
                if (p.getVal() == val) {
                    return p;
                }
            }
            return itemPackage;
        }
    }

    public static enum PriceType {
        /**
         * 游戏币
         */
        money(0),
        /**
         * 金子
         */
        gold(1);
        private final int val;

        private PriceType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static PriceType valueOf(int val) {
            for (PriceType p : values()) {
                if (p.getVal() == val) {
                    return p;
                }
            }
            return gold;
        }
    }
    public static enum HotType {
        /**
         * 普通
         */
        normal(0),
        /**
         * 打折
         */
        discount(1),
        /**
         * 热卖
         */
        hot(2),
        /**
         * 推荐
         */
        first(3),
        /**
         * 限时
         */
        limitTime(4),
        /**
         * 个人限购
         */
        limitNum(5),
        /**
         * 全区限购,抢购
         */
        limitNumWold(6);
        private final int val;

        private HotType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static HotType valueOf(int val) {
            for (HotType p : values()) {
                if (p.getVal() == val) {
                    return p;
                }
            }
            return normal;
        }
    }

    /**
     * *
     * 商城标签页
     */
    public static enum ShopTabType {
        /**
         * 所有
         */
        all(-1),
        
        /**
         * 普通第一个
         */
        first(0),
        /**
         * 副本开启前的商店
         */
        second(1),
        /**
         * 第二个
         */
        three(2);
        private final int val;

        private ShopTabType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static ShopTabType valueOf(int val) {
            for (ShopTabType p : values()) {
                if (p.getVal() == val) {
                    return p;
                }
            }
            return first;
        }
    }
    
   
    
}
