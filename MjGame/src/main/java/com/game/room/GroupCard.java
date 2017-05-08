package com.game.room;

import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/8.
 */
public class GroupCard {
    private int type;
    private List<Integer> cards;

    public GroupCard(int type,List<Integer> cards){
        this.type = type;
        this.cards = cards;
    }

    public int getType() {
        return type;
    }

    public List<Integer> getCards() {
        return cards;
    }
}
