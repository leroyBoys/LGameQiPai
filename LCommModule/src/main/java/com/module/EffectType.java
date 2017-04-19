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
public enum EffectType {
    /**
     * 向对手喷颜料增加遮盖物面积
     */
    otherPanintSplatter(1),
    /**
     * 使对手题目倒置
     */
    otherTitleInversion(2),
    /**
     * 冰冻对手答案(使对方无法回答)
     */
    freeOtherAnswer(3),
    /**
     * 屏蔽对手答案(需要玩家摇晃手机才可以解锁答题)
     */
    hiddleOtherAnswer(4),
    /**
     * 使对手答案翻转躲藏
     */
    otherFlipDodge(5),
    /**
     * 屏蔽自己的错误答案
     */
    hideErrorAndswer(6),
    /**
     * 开局后倒计时停止
     */
    stopTimeBack(7),
    /**
     * 添加反弹技能效果buffer(对以后被击中反弹效果)
     */
    reboundSkill(8),
    /**
     * 场外求助（一定几率回答正确）
     */
    findHelp(9),
    /**
     * 无敌效果,清除已有干扰技能
     */
    immune(10),
    /**
     * 疲劳(使对方下次使用技能的时候法力消耗增加)
     */
    speedOtherMagicUsed(11),
    /**
     * 完美躲避
     */
    perfectEscape(12),
    /**
     * 伤害连接(即给自己添加一个反弹伤害)
     */
    hurtSameOther(13),
    /**
     * 恢复自己法力值
     */
    recoveryMagicToSelf(14),
    /**
     * 恢复血量
     */
    recoveryBloodToSelf(15),
    /**
     * 增强下一次的攻击力
     */
    strongNextAttack(16)
    
    ;
    private final int val;

    private EffectType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public EffectType valueofVal(int val) {
        for (EffectType object : EffectType.values()) {
            if (object.val == val) {
                return object;
            }
        }
        return otherPanintSplatter;
    }
}
