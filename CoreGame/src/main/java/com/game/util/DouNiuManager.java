package com.game.util;

import java.util.*;

import com.lgame.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 斗牛核心算法(只比较最大牌型，如果最大牌型一样就只比较最大牌（最大牌一样比较花色）(同花顺，顺子，炸弹，葫芦除外))
 *  顺子，A23开头最小，..12 13 A结束最大，炸弹和葫芦比较四张和三张牌的大小
 * 牛牛：即牛十，例如235 55(含N个10+m个花牌)
 * 炸弹：有四张一样点数的牌，剩余一张随意
 * 五花牛：五张牌全部由JQK组成，
 * 五小牛：五张牌都小于5，并且加起来小于10
 * 同花顺>炸弹>五花牛>五小牛>葫芦>同花>顺子>牛牛>有牛>没牛
 * Created by leroy:656515489@qq.com
 * 2017/5/12.
 */
public class DouNiuManager {
    private static DouNiuManager ourInstance = new DouNiuManager();
    private final Logger logger = LoggerFactory.getLogger("play");

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
        Collections.sort(cards, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2%100-o1%100;
            }
        });

        int byteCard = 0;
        int sum = 0;
        Map<Integer,Integer> map = new HashMap<>(5);//key:>=10 都为10--- value:num
        Map<Integer,Integer> realMap = new HashMap<>(5);

        int specialNum = 0;
        int lastColor = 0;
        boolean isSameColor = true;
        int max = 0;
        int min = 99;

        Set<Integer> noSingleSet = new HashSet<>(2);

        boolean isHavA = false;//是否包含1（1不参与最大最小值）
        for(Integer card:cards){
            int cv = card%100;

            if(cv == 1){
                isHavA = true;
            }else {
                max = Math.max(max,cv);
                min = Math.min(min,cv);
            }
            int color = card/100;
            Integer num = realMap.get(cv);
            if(num == null){
                num = 0;
            }
            realMap.put(cv,num+1);

            if(specialNum == 0){
                specialNum = cv*10+getValue(color);
            }

            if(isSameColor){
                if(lastColor != 0 && lastColor != color){
                    isSameColor = false;
                }else {
                    lastColor = color;
                }
            }
            int c = Math.min(10,cv);

            num = map.get(c);
            if(num == null){
                num = 0;
            }else if(c < 10){
                noSingleSet.add(c);
            }
            map.put(c,num+1);

            byteCard |= 1<<c;
            sum+=c;
        }

        LinkedList<NiuCard> niuTypes = new LinkedList<>();
        StaticNiuCard staticNiuCard = null;

        int niuTypeValue = 0;
        if(realMap.size() == 2){//查询是否是炸弹
            int maxCount = 0;
            for(Map.Entry<Integer,Integer> entry:realMap.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount = entry.getValue();
                    niuTypeValue = entry.getKey();
                }
            }

            if(maxCount == 4){
                niuTypes.add(new NiuCard(NiuType.BoomNiu,niuTypeValue));
            }else {
                niuTypes.add(new NiuCard(NiuType.HuLu,niuTypeValue));
            }
        }else if(realMap.size() == 5){
            boolean isLink = false;
            if(isHavA){
                if(max - min == 3){
                    if(max == 13){
                        niuTypeValue = 14;
                        isLink = true;
                    }else if(min == 2){
                        niuTypeValue = max;
                        isLink = true;
                    }
                }
            }else if(max - min == 4){
                niuTypeValue = max;
                isLink = true;
            }

            if(isLink){
                if(isSameColor){
                    niuTypes.add(new NiuCard(NiuType.TongHuaShun,niuTypeValue));
                }else {
                    niuTypes.add(new NiuCard(NiuType.Link,niuTypeValue));
                }
            }else if(isSameColor){
                niuTypes.add(new NiuCard(NiuType.SameColor,0));
            }
        }

        boolean isHavNiu = false;
        int sumNum = sum%10;//sum的余数
        if(sum < 10){//小牛
            niuTypes.add(new NiuCard(NiuType.XiaoNiu,0));
        }

        if(sumNum == 0){

            if(min > 10){//五花牛
                niuTypes.add(new NiuCard(NiuType.JinNiu,0));
                isHavNiu = true;
            }

            Integer tenNums = map.get(10);
            if(tenNums != null){//牛牛
                if(tenNums == cards.size() || (tenNums == 2 || tenNums == 3)){
                    niuTypes.add(new NiuCard(NiuType.NiuNiu,0));
                    isHavNiu = true;
                }else if(tenNums == 1){
                    if(noSingleSet.contains(5)){
                        niuTypes.add(new NiuCard(NiuType.NiuNiu,0));
                        isHavNiu = true;
                    }else {
                        staticNiuCard = getNiuType(sumNum,byteCard);
                        if(staticNiuCard != null){//牛
                            niuTypes.add(new NiuCard(NiuType.NiuNiu,0));
                            isHavNiu = true;
                        }
                    }
                }
            }else {
                if(noSingleSet.contains(5)){
                    niuTypes.add(new NiuCard(NiuType.NiuNiu,0));
                    isHavNiu = true;
                }else {
                    staticNiuCard = getNiuType(sumNum,byteCard);
                    if(staticNiuCard != null){//牛
                        niuTypes.add(new NiuCard(NiuType.NiuNiu,0));
                        isHavNiu = true;
                    }
                }
            }
        }else if(sumNum % 2 == 0){
            int targetCard = sumNum/2;
            Integer nums = map.get(targetCard);
            if(nums != null && nums >= 2){//有牛
                isHavNiu = true;
            }else{
                targetCard = (sumNum+10)/2;
                nums = map.get(targetCard);
                if(nums != null && nums >= 2){//有牛
                    isHavNiu = true;
                }else {
                    staticNiuCard = getNiuType(sumNum,byteCard);
                    if(staticNiuCard != null){//牛
                        isHavNiu = true;
                    }
                }
            }
        }else {
            staticNiuCard = getNiuType(sumNum,byteCard);
            if(staticNiuCard != null){//牛
                isHavNiu = true;
            }
        }

        if(isHavNiu && sumNum > 0){
            niuTypes.add(new NiuCard(NiuType.getNiuType(sumNum),0));
        }

        NiuType maxNiuType = NiuType.Null;
        if(!niuTypes.isEmpty()){
            if(niuTypes.size() != 1){
                Collections.sort(niuTypes, new Comparator<NiuCard>() {
                    @Override
                    public int compare(NiuCard o1, NiuCard o2) {
                        return o2.getNiuType().getTypeNum() - o1.getNiuType().getTypeNum();
                    }
                });
            }

            maxNiuType = niuTypes.getFirst().getNiuType();
            specialNum += maxNiuType.getTypeNum()*10000+(1000+niuTypes.getFirst().getSpecialNum());
        }

    /*    String log = "cards:"+Json.toJson(cards)+" niuTypes:"+ Json.toJson(maxNiuType)+"  maxNiuType:"+maxNiuType+" specialNum:"+specialNum+" isHavNiu:"+isHavNiu;
        System.out.println(log);
        logger.info(log);*/


        NiuCard niuCard = new NiuCard();
        niuCard.setSpecialNum(specialNum);
        niuCard.setNiuType(maxNiuType);
        niuCard.setHavNiu(isHavNiu);
        return niuCard;
    }


    private StaticNiuCard getNiuType(int target,int byteCard){
        List<StaticNiuCard> staticNiuCards = niuNumMap.get(target);
        if(staticNiuCards == null){
            return null;
        }

        for(StaticNiuCard niuCard:staticNiuCards){
            if((niuCard.getByteNum()&byteCard) == niuCard.getByteNum()){
                //    System.out.println(target+"---"+niuCard.getNumber()+"---->"+ Arrays.toString(niuCard.getCards()));
                return niuCard;
            }
        }

        return null;
    }

    /**
     * 黑桃>红桃>梅花>方片
     * @param color
     * @return
     */
    private int getValue(int color){
        if(color == 1){//黑桃
            return 4;
        }else if(color == 2){//方砖
            return 1;
        }else if(color == 3){//梅花
            return 2;
        }else if(color == 4){//红条
            return 3;
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
        private NiuType niuType = NiuType.Null;//最大牌型
        private boolean isHavNiu;//是否有牛

        public NiuCard() {
        }

        public NiuCard(NiuType niuType,int specialNum) {
            this.specialNum = specialNum;
            this.niuType = niuType;
        }

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

        public boolean isHavNiu() {
            return isHavNiu;
        }

        public void setHavNiu(boolean havNiu) {
            isHavNiu = havNiu;
        }
    }

    public enum NiuType{
        Null(0,0,1,1),
        Niu1(1,1,1,1),
        Niu2(2,2,1,2),
        Niu3(3,3,1,3),
        Niu4(4,4,1,4),
        Niu5(5,5,1,5),
        Niu6(6,6,1,6),
        Niu7(7,7,2,7),
        Niu8(8,8,2,8),
        Niu9(9,9,2,9),
        /**
         * 牛牛
         */
        NiuNiu(10,10,3,10),
        /**
         * 金牛(五花牛)：五张十以上的花牌组成的“斗牛”
         */
        JinNiu(11,15,5,19),
        /**
         * 五小：5张牌牌点总数小于或者等于10
         */
        XiaoNiu(12,14,5,18),
        /**
         * 炸弹
         */
        BoomNiu(13,19,5,20),
        /**
         * 顺子
         */
        Link(14,11,5,15),
        /**
         * 同花
         */
        SameColor(15,12,5,16),
        /**
         * 葫芦
         */
        HuLu(16,13,5,17),
        /**
         * 同花顺
         */
        TongHuaShun(17,20,5,25)
        ;

        private final int niu;
        private final int typeNum;//类型权重
        private final int rate;
        private final int fKRate;//疯狂斗牛倍数
        NiuType(int niu,int typeNum,int rate,int fKRate){
            this.niu = niu;
            this.typeNum = typeNum;
            this.rate = rate;
            this.fKRate = fKRate;
        }

        public static final NiuType getNiuType(int niu){
            return DouNiuManager.getInstance().NiuTypeMap.get(niu);
        }

        public int getNiu() {
            return niu;
        }

        public int getfKRate() {
            return fKRate;
        }

        public int getTypeNum() {
            return typeNum;
        }

        public int getRate() {
            return rate;
        }
    }

    public static void main(String[] args){
        List<Integer> cars = new ArrayList<>();
        Collections.addAll(cars,new Integer[]{105, 102, 108, 103,106});
        NiuCard niuCard = DouNiuManager.getInstance().getNiuCard(cars);

/*
        List<Integer> cars2 = new ArrayList<>();
        Collections.addAll(cars2,new Integer[]{108, 402, 310, 405, 405});
        NiuCard niuCard2 = DouNiuManager.getInstance().getNiuCard(cars2);
        System.out.println(niuCard.getSpecialNum() > niuCard2.getSpecialNum());*/
    }
}
