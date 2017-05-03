package com.game.core.room.mj;

import com.game.core.room.BaseChairInfo;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/21.
 */
public class MjChairInfo extends BaseChairInfo<MjChairStatus,MjHandCardsContainer> {
    /** 数值均减一即0为初始值，1位不压跑 */
    private int yapaoNum = 0;

    public MjChairInfo(int uid) {
        super(MjChairStatus.Idle);
        this.setId(uid);
    }

    @Override
    protected MjHandCardsContainer initHands() {
        return new MjHandCardsContainer();
    }

    @Override
    public void clean() {
        yapaoNum = 0;
        this.status = MjChairStatus.Idle;
        getHandsContainer().cleanHands();
    }

    public boolean isCanYaPao() {
        return yapaoNum==0;
    }

    public void setYapaoNum(int yapaoNum) {
        this.yapaoNum = yapaoNum<1 || yapaoNum>5 ?1:yapaoNum;
    }
}
