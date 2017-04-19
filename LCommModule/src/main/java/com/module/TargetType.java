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
public enum TargetType {
    /**
     * （最高）蹦跶高度（米）
     */
    ojumpHeight(1),
    /**
     * （一次）完美蹦极次数
     */
    operfectJumpCount(2),
    /**
     * （累计）完美蹦极次数
     */
    tperfectJumpCount(3),
    /**
     * 最高连击蹦极次数
     */
    odoubleJumpCount(4),
    /**
     * 单次破坏树枝
     */
    odestoryTree(5),
    /**
     * 累计破坏树枝
     */
    tdestoryTree(6),
    /**
     * 单次破坏刺球
     */
    oflyBall(7),
    /**
     * 累计破坏刺球
     */
    tflyBall(8),
    /**
     * 单次破坏弹簧蛇
     */
    odetoryTh(9),
    /**
     * 累计破坏弹簧蛇
     */
    tdetoryTh(10),
    /**
     * 技能升到满级
     */
    skillFull(11),
    /**
     * 所有装备升到满级
     */
    allEquipmentFullLevel(12),
    /**
     * 将所有人物开启
     */
    allRoleOpen(13),
    /**
     * 单次吃多少金币
     */
    oGetMoney(14),
    /**
     * 累计吃多少金币
     */
    tGetMoney(15),
    /**
     * 单次吃多少能量棒
     */
    oGetEnergy(16),
    /**
     * 累计吃多少能量棒
     */
    tGetEnergy(17),
    /**
     * 购买一次xx道具
     */
    buyItem(18),
    /**
     * 单次吃多少烂能量棒
     */
    oDestoryEnergy_lan(19),
    /**
     * 累计吃多少烂能量棒
     */
    tDestoryEnergy_lan(20),;
    private final int code;

    private TargetType(int code) {
        this.code = code;
    }

    public static TargetType valOf(int id) {
        for (TargetType type : TargetType.values()) {
            if (type.val() == id) {
                return type;
            }
        }
        return null;
    }

    public int val() {
        return code;
    }

}
