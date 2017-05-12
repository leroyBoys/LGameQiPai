package com.game.util;

import java.util.*;

/**
 * 斗牛核心算法
 * ABCDEF
 * 顺序值大小和花色对比权重：speial
 * num:实际值（1-13）
 * A:500*5+花色*100+num[2600,2913]
 * B:500*4+花色*100+num[2100,2513]
 * C:500*3+花色*100+num[1600,1913]
 * D:500*2+花色*100+num[1100,1413]
 * E:500*1+花色*100+num[600,913]
 * F:500*0+花色*100+num[100,413]
 *
 *  牛一到牛九
 * n*10000+speial[10000,93000]
 *
 * 牛牛
 * 100000+speial[100000,103000]
 * 银牛
 * 110000+speial[110000,113000]
 *  金牛
 * 120000+speial[120000,123000]
 * 炸弹
 *  130000+炸弹实际数值*3000+speial[130000,200000]
 *小牛
 *500000+speial[500000,503000]
 *
 * 小牛>炸弹>金牛>银牛>牛牛>1-9牛>其他
 * Created by leroy:656515489@qq.com
 * 2017/5/12.
 */
public class DouNiuManager {
    private static DouNiuManager ourInstance = new DouNiuManager();

    public static DouNiuManager getInstance() {
        return ourInstance;
    }

    private DouNiuManager() {
        init();
    }

    /** 余数-NiuCard */
    private final Map<Integer,List<StaticNiuCard>> niuNumMap = new HashMap<>();
    /** 类型 - niuType */
    private final Map<Integer,NiuType> NiuTypeMap = new HashMap<>();

    public NiuCard getNiuCard(List<Integer> cards){
        int byteCard = 0;
        int sum = 0;
        boolean isHavShi = false;//是否有小于等于10
        Map<Integer,Integer> map = new HashMap<>(5);//key:>=10 都为10--- value:num
        Map<Integer,Integer> realMap = new HashMap<>(5);

        int specialNum = 0;
        int i = cards.size();
        for(Integer card:cards){
            int cv = card%100;
            Integer num = realMap.get(cv);
            if(num == null){
                num = 0;
            }
            realMap.put(cv,num+1);

            if(cv <= 10){
                isHavShi = true;
            }

            specialNum += i--*500+getValue(card/100)*100+cv;
            int c = Math.min(10,cv);
            num = map.get(c);
            if(num == null){
                num = 0;
            }
            map.put(c,num+1);

            byteCard |= 1<<c;
            sum+=c;
        }

        NiuCard niuCard = new NiuCard();

        int sumNum = sum%10;//sum的余数
        if(sum < 10){//小牛
            specialNum += 500000;
            niuCard.setSpecialNum(specialNum);
            niuCard.setNiuType(NiuType.XiaoNiu);
            return niuCard;
        }

        if(realMap.size() <= 2){//查询是否是炸弹
            int four = 0;
            for(Map.Entry<Integer,Integer> entry:realMap.entrySet()){
                if(entry.getValue() != 1 && entry.getValue() != 4){
                    break;
                }else if(entry.getValue() == 4){
                    four = entry.getKey();
                }
            }

            if(four != 0){
                specialNum += 130000+four*3000;
                niuCard.setSpecialNum(specialNum);
                niuCard.setNiuType(NiuType.BoomNiu);
                return niuCard;
            }
        }

        if(sumNum == 0){
            if(!isHavShi){//金牛
                specialNum += 120000;
                niuCard.setSpecialNum(specialNum);
                niuCard.setNiuType(NiuType.JinNiu);
                return niuCard;
            }

            Integer tenNums = map.get(10);
            if(tenNums != null && tenNums == cards.size()){//银牛
                specialNum += 110000;
                niuCard.setSpecialNum(specialNum);
                niuCard.setNiuType(NiuType.YinNiu);
                return niuCard;
            }
        }

        niuCard.setSpecialNum(specialNum);
        if(sumNum % 2 == 0){
            Integer nums = map.get(sumNum/2);
            if(nums != null && nums >= 2){//有牛
            }else{
                StaticNiuCard staticNiuCard = getNiuType(sumNum,byteCard);
                if(staticNiuCard == null){//无牛
                    return niuCard;
                }
            }

            if(sumNum == 0){//牛牛
                specialNum += 100000;
                niuCard.setSpecialNum(specialNum);
                niuCard.setNiuType(NiuType.NiuNiu);
                return niuCard;
            }
        }else {
            StaticNiuCard staticNiuCard = getNiuType(sumNum,byteCard);
            if(staticNiuCard == null){//无牛
                return niuCard;
            }
        }

        specialNum += sumNum*10000;
        niuCard.setSpecialNum(specialNum);
        niuCard.setNiuType(NiuType.getNiuType(sumNum));
        return niuCard;

    }

    private StaticNiuCard getNiuType(int target,int byteCard){
        List<StaticNiuCard> staticNiuCards = niuNumMap.get(target);
        if(staticNiuCards == null){
            return null;
        }

        for(StaticNiuCard niuCard:staticNiuCards){
            if((niuCard.getByteNum()&byteCard) == niuCard.getByteNum()){
                System.out.println(target+"---"+niuCard.getNumber()+"---->"+ Arrays.toString(niuCard.getCards()));
                return niuCard;
            }
        }

        return null;
    }

    private int getValue(int color){
        if(color == 1){
            return 4;
        }else if(color == 2){
            return 3;
        }else if(color == 3){
            return 2;
        }else if(color == 4){
            return 1;
        }
        return 1;
    }

    private void init() {
        int[] numSources = new int[]{0,1,2,3,4,5,6,7,8,9};//余数
        int[] sources = new int[]{1,2,3,4,5,6,7,8,9,10};
        for(int target:numSources){
            List<StaticNiuCard> niuCards = niuNumMap.get(target);
            if(niuCards == null){
                niuCards = new LinkedList<>();
                niuNumMap.put(target,niuCards);
            }

            int i = 0;
            while (i!=sources.length){
                int j = i+1;
                while (j != sources.length){
                    if(target == (sources[i]+sources[j])%10){
                        niuCards.add(new StaticNiuCard(new int[]{sources[i],sources[j]},1<<sources[i]|1<<sources[j],(sources[i]+sources[j])%10));
                        break;
                    }
                    j++;
                }
                i++;
            }

        }


        for(NiuType niuType: NiuType.values()){
            NiuTypeMap.put(niuType.getNiu(),niuType);
        }
    }

    public final static class StaticNiuCard{
        /** 二进制 */
        private final int byteNum;
        /** 余数 */
        private final int number;

        private final int[] cards;

        public StaticNiuCard(int[] cards,int byteNum, int number) {
            this.byteNum = byteNum;
            this.number = number;
            this.cards = cards;
        }

        public int getByteNum() {
            return byteNum;
        }

        public int[] getCards() {
            return cards;
        }

        public int getNumber() {
            return number;
        }
    }

    public final static class NiuCard{
        /** 权重值 */
        private int specialNum;
        private NiuType niuType = NiuType.Null;

        public int getSpecialNum() {
            return specialNum;
        }

        public void setSpecialNum(int specialNum) {
            this.specialNum = specialNum;
        }

        public NiuType getNiuType() {
            return niuType;
        }

        public void setNiuType(NiuType niuType) {
            this.niuType = niuType;
        }
    }

    public enum NiuType{
        Null(0,1),
        Niu1(1,2),
        Niu2(2,2),
        Niu3(3,2),
        Niu4(4,2),
        Niu5(5,2),
        Niu6(6,2),
        Niu7(7,2),
        Niu8(8,2),
        /**
         * 金牛：五张十以上的花牌组成的“斗牛”
         */
        JinNiu(10,2),
        /**
         * 银牛：十和十以上的花牌组成的“斗牛”
         */
        YinNiu(11,2),
        /**
         * 五小：5张牌牌点总数小于或者等于10
         */
        XiaoNiu(12,2),
        /**
         * 炸弹
         */
        BoomNiu(13,2),
        NiuNiu(14,2),
        ;

        private final int niu;
        private final int rate;
        NiuType(int niu,int rate){
            this.niu = niu;
            this.rate = rate;
        }

        public static final NiuType getNiuType(int niu){
            return DouNiuManager.getInstance().NiuTypeMap.get(niu);
        }

        public int getNiu() {
            return niu;
        }

        public int getRate() {
            return rate;
        }
    }
}
