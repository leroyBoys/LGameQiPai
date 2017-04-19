/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.config.BaseXml;

/**
 * 技能
 * @author leroy
 */
public class StaticSkill  implements BaseXml {

    private int id;
    private String name;
    private String desc;
    private String icon;
    private SkillType skillType;
    private SkillUsedLimit skillUsedLimit;
    private SkillQuestLimit skillQuestLimit;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    public SkillUsedLimit getSkillUsedLimit() {
        return skillUsedLimit;
    }

    public void setSkillUsedLimit(SkillUsedLimit skillUsedLimit) {
        this.skillUsedLimit = skillUsedLimit;
    }

    public SkillQuestLimit getSkillQuestLimit() {
        return skillQuestLimit;
    }

    public void setSkillQuestLimit(SkillQuestLimit skillQuestLimit) {
        this.skillQuestLimit = skillQuestLimit;
    }

    @Override
    public boolean isTheSame(Object obj) {
        return false;
    }
    /**
     * 技能类型
     */
    public static enum SkillType {
        /**
         * 干扰
         */
        interfere(1),
        /**
         * 辅助
         */
        assist(2),
        /**
         * 特殊
         */
        special(3);
        private final int val;

        private SkillType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public SkillType valueofVal(int val) {
            for (SkillType object : SkillType.values()) {
                if (object.val == val) {
                    return object;
                }
            }
            return special;
        }
    }

    /**
     * 技能使用限制阶段
     */
    public static enum SkillUsedLimit {
        /**
         * 全部可用
         */
        all(1),
        /**
         * 题目出现前
         */
        beforequest(2),
        /**
         * 题目出现后，答案出现前
         */
        betweenquesanswert(3),
        /**
         * 答案出现后
         */
        afteranswer(4),;
        private final int val;

        private SkillUsedLimit(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public SkillUsedLimit valueofVal(int val) {
            for (SkillUsedLimit object : SkillUsedLimit.values()) {
                if (object.val == val) {
                    return object;
                }
            }
            return null;
        }
    }

    /**
     * 技能对题目类型的限制
     */
    public static enum SkillQuestLimit {
        /**
         * 全部
         */
        all(1),
        /**
         * 选择
         */
        choice(2),
        /**
         * 判断
         */
        judge(3),
        /**
         * 连线
         */
        line(4),
        /**
         * 填空
         */
        fillBlanks(5);
        private final int val;

        private SkillQuestLimit(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public SkillQuestLimit valueofVal(int val) {
            for (SkillQuestLimit object : SkillQuestLimit.values()) {
                if (object.val == val) {
                    return object;
                }
            }
            return all;
        }
    }
}
