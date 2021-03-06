package com.game.room;

import com.game.core.constant.GameConst;
import com.game.core.room.card.AutoCacheHandContainer;
import com.game.core.room.card.BaseHandCardsContainer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/21.
 */
public class MjHandCardsContainer extends BaseHandCardsContainer {
    protected MjAutoCacheHandContainer autoCacheHandContainer = new MjAutoCacheHandContainer();

    /** type-groupCard */
    private List<GroupCard> chiGangList = new LinkedList<>();
    /** 碰 */
    private List<GroupCard> pengList = new LinkedList<>();

    /** 胡 */
    private LinkedList<Integer> huCards = new LinkedList<>();

    /**
     * 杠吃
     * @param type 子类
     * @param cards
     */
    public void addOutCard(int type,List<Integer> cards){
        pengList.add(new GroupCard(type,cards));
    }

    /**
     *  碰
     * @param cards
     */
    public void addPengCard(List<Integer> cards){
        pengList.add(new GroupCard(GameConst.MJ.ACTION_TYPE_PENG,cards));
    }

    /**
     * 胡
     * @param card
     */
    public void addHu(int card){
        huCards.add(card);
    }

    @Override
    public void cleanHands() {
        super.cleanHands();
        chiGangList.clear();
        huCards.clear();
        pengList.clear();
        autoCacheHandContainer.clear();
    }

    @Override
    protected AutoCacheHandContainer getAutoCache() {
        return autoCacheHandContainer;
    }

    public boolean removeCardFromHand(int card,int targeNum){
        int num = targeNum;
        Iterator<Integer> it = this.getHandCards().iterator();
        while (it.hasNext()) {
            if (it.next() == card && num > 0) {
                it.remove();
                if(--num == 0){
                    break;
                }
            }
        }

        autoCacheHandContainer.removeCard(card,targeNum);
        return num == 0;
    }


    /////////////////////////////////////////

    public List<GroupCard> getChiGangList() {
        return chiGangList;
    }

    public List<GroupCard> getPengList() {
        return pengList;
    }

    public LinkedList<Integer> getHuCards() {
        return huCards;
    }
}
