/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.config;

import com.lgame.util.load.config.BaseXml;
import com.module.EffectData;
import com.module.ItemData;

import java.util.List;

/**
 *
 * @author leroy
 */
public class StaticSkillUnit implements BaseXml {

    private int id;
    private int skillId;//技能等id
    private int level;//技能等级
    private int openLevel;//解锁等级
    private String desc;//技能描述
    private int needUsedMagic;//使用消耗魔法值
    private int needUsedBlood;//使用消耗血量值
    private List<EffectData> effects;//技能特效
    private int  openNeedSkillPoint;//开启/升级需要技能点
    
    private List<Integer> openNeedCompleteAchieve;//开启/升级需要完成成就列表
    
    private List<ItemData> openNeedItem;//开启/升级条件

    public static String getUniqueId(int skillId,int level){
        return skillId +"_"+level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOpenLevel() {
        return openLevel;
    }

    public void setOpenLevel(int openLevel) {
        this.openLevel = openLevel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNeedUsedMagic() {
        return needUsedMagic;
    }

    public void setNeedUsedMagic(int needUsedMagic) {
        this.needUsedMagic = needUsedMagic;
    }

    public int getNeedUsedBlood() {
        return needUsedBlood;
    }

    public void setNeedUsedBlood(int needUsedBlood) {
        this.needUsedBlood = needUsedBlood;
    }

    public List<EffectData> getEffects() {
        return effects;
    }

    public void setEffects(List<EffectData> effects) {
        this.effects = effects;
    }

    public int getOpenNeedSkillPoint() {
        return openNeedSkillPoint;
    }

    public void setOpenNeedSkillPoint(int openNeedSkillPoint) {
        this.openNeedSkillPoint = openNeedSkillPoint;
    }

    public List<Integer> getOpenNeedCompleteAchieve() {
        return openNeedCompleteAchieve;
    }

    public void setOpenNeedCompleteAchieve(List<Integer> openNeedCompleteAchieve) {
        this.openNeedCompleteAchieve = openNeedCompleteAchieve;
    }

    public List<ItemData> getOpenNeedItem() {
        return openNeedItem;
    }

    public void setOpenNeedItem(List<ItemData> openNeedItem) {
        this.openNeedItem = openNeedItem;
    }

    @Override
    public boolean isTheSame(Object obj) {
        return false;
    }
}
