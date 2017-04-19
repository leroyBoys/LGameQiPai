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
public enum BuffType {
    /**
     * 反弹技能效果buffer
     */
    reboundSkill(1),
    /**
     * 无敌效果,清除干扰技能
     */
    immune(2),
    /**
     * 疲劳(使用技能的时候法力消耗增加)
     */
    speedMagicUsed(3),
    /**
     * 完美躲避
     */
    perfectEscape(4),
    /**
     * 伤害连接(即给自己添加一个反弹伤害)
     */
    hurtSameOther(5),
    /**
     * 增强下一次的攻击力
     */
    strongAttack(6);
    private final int val;

    private BuffType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public BuffType valueofVal(int val) {
        for (BuffType object : BuffType.values()) {
            if (object.val == val) {
                return object;
            }
        }
        return reboundSkill;
    }
}
