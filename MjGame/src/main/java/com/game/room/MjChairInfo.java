package com.game.room;

import com.game.core.room.BaseChairInfo;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/21.
 */
public class MjChairInfo extends BaseChairInfo<MjChairStatus,MjHandCardsContainer> {
    private int score;
    /** 数值 */
    private int yapaoNum = -1;

    public MjChairInfo(int uid) {
        super(MjChairStatus.Idle);
        this.setId(uid);
    }

    @Override
    protected MjHandCardsContainer initHands() {
        return new MjHandCardsContainer();
    }

    @Override
    public void resetStatus() {
        this.status = MjChairStatus.Idle;
    }

    @Override
    public void clean() {
        yapaoNum = -1;
        getHandsContainer().cleanHands();
    }

    public boolean isCanYaPao() {
        return yapaoNum==-1;
    }

    public int getScore() {
        return score;
    }

    public int getYapaoNum() {
        return yapaoNum;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setYapaoNum(int yapaoNum) {
        this.yapaoNum = yapaoNum<0 || yapaoNum>4 ?1:yapaoNum;
    }
}
