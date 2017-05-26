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
    /** 过牌的对应值，用于过手碰杠胡  */
    private int passCard;

    public MjChairInfo(int uid, MjTable baseTableVo) {
        super(baseTableVo,MjChairStatus.Idle);
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
        resetPassCard();
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

    public int getPassCard() {
        return passCard;
    }

    public void setPassCard(int passCard) {
        this.passCard = passCard;
    }

    /**
     * 是否可以吃碰杠胡
     * @return
     */
    public boolean isCanDo(){
        return passCard == 0;
    }

    public void resetPassCard(){
        if(passCard > 0){
            passCard = 0;
        }
    }

    public void setYapaoNum(int yapaoNum) {
        this.yapaoNum = yapaoNum<0 || yapaoNum>4 ?1:yapaoNum;
    }
}
