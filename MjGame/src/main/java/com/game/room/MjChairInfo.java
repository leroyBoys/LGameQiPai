package com.game.room;

import com.game.core.room.BaseChairInfo;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/21.
 */
public class MjChairInfo extends BaseChairInfo<MjHandCardsContainer> {
    /** 数值 */
    private int yapaoNum;
    /** 过牌的对应值，用于过手碰杠胡  */
    private int passCard;

    public MjChairInfo(int uid, MjTable baseTableVo) {
        super(baseTableVo);
        this.setId(uid);
    }

    @Override
    protected MjHandCardsContainer initHands() {
        return new MjHandCardsContainer();
    }

    @Override
    public void resetStatus() {

    }

    @Override
    public void clean() {
        yapaoNum = 0;
        resetPassCard();
        getHandsContainer().cleanHands();
    }

    public int getYapaoNum() {
        return yapaoNum;
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
