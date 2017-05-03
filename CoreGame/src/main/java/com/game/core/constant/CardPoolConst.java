package com.game.core.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class CardPoolConst {
    public final List<Integer> MJ_Tiao = toList("21,22,23,24,25,26,27,28,29");
    public final List<Integer> MJ_Wan = toList("11,12,13,14,15,16,17,18,19");
    public final List<Integer> MJ_Tong = toList("31,32,33,34,35,36,37,38,39");
    public final List<Integer> MJ_Feng = toList("41,42,43,44");
    public final List<Integer> MJ_Fa = toList("45,46,47");


    public static List<Integer> toList(String str){
        List<Integer> ret = new ArrayList<>();
        String[] cards = str.split(",");
        for(String card:cards){
            ret.add(Integer.valueOf(card));
        }
        return ret;
    }
}
