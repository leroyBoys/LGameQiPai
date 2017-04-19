/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.db;

/**
 *
 * @author leroy
 */
public class UserSkill {

    private int uid;
    private int skillId;//技能id
    private int skillLevel;//技能等级
    private int usedSkillPonit;//已使用的技能点
    private int virtualLv;//虚拟等级,有效期到达时候恢复
    private long endTime = -1;//有效期：-1无限

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    /**
     * 获得当前显示的等级
     * @return 
     */
    public int getShowLevel(){
        return Math.max(getSkillLevel(), getVirtualLv());
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getVirtualLv() {
        if(endTime < 0){
            return getSkillLevel();
        }
        if(System.currentTimeMillis() < endTime){
             return virtualLv;
        }
        return 0;
    }

    public void setVirtualLv(int virtualLv) {
        this.virtualLv = virtualLv;
    }


    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getUsedSkillPonit() {
        return usedSkillPonit;
    }

    public void setUsedSkillPonit(int usedSkillPonit) {
        this.usedSkillPonit = usedSkillPonit;
    }

}
