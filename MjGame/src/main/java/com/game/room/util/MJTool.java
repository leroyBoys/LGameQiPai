package com.game.room.util;

import com.game.room.GroupCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/25.
 */
public class MJTool {
    /** 四风 */
    public static final List<Integer> SIFENGPOOL = getList(41,41,41,41,42,42,42,42,43,43,43,43,44,44,44,44);
    /** 中发白 */
    public static final List<Integer> ZHONGFABAI = getList(45,45,45,45,46,46,46,46,47,47,47,47);

    private static final List<Integer> getList(Integer... cards){
        List<Integer> list = new LinkedList<>();
        Collections.addAll(list,cards);
        return list;
    }

    /***
     * 清一色（包含玩条筒，东西南北和中发白）
     * @param handCards
     * @param groupList
     * @return
     */
    public static boolean oneCorlor(List<Integer> handCards, List<GroupCard> groupList) {
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

    /**
     * 清一色（只有万条筒）
     * @param handCards
     * @param groupList
     * @return
     */
    public static boolean oneCorlorSimple(List<Integer> handCards, List<GroupCard> groupList) {
        int temp = 0;
        for (Integer c : handCards) {
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
            if (temp != cg.getCards().get(0) / 10)
                return false;
        }
        return true;
    }


}
