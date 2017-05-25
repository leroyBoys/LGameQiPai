package com.game.room.util;

import com.game.room.GroupCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/25.
 */
public class MJTool {

    /***
     * 清一色
     * @param handCards
     * @param groupList
     * @return
     */
    public boolean oneCorlor(List<Integer> handCards, ArrayList<GroupCard> groupList) {
        int temp = 0;
        for (Integer c : handCards) {
            if(c > 40){
                continue;
            }

            if (temp == 0) {
                temp = c / 10;
                continue;
            }
            if (c / 10 != temp)
                return false;
        }

        if(groupList == null || groupList.isEmpty()){
            return true;
        }

        for (GroupCard cg : groupList) {
            int card = 0;
            if(cg.getCards() != null && !cg.getCards().isEmpty()){
                card = cg.getCards().get(0);
            }

            if(card == 0 || card > 40){
                continue;
            }

            if (temp != card / 10)
                return false;
        }
        return true;
    }

}
