package com.game.room.util;

import com.game.room.GroupCard;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/25.
 */
public class MJTool {
    /** 四风 */
    public static final List<Integer> SIFENGPOOL = getList(41,41,41,41,42,42,42,42,43,43,43,43,44,44,44,44);
    /** 中发白 */
    public static final List<Integer> ZHONGFABAI = getList(45,45,45,45,46,46,46,46,47,47,47,47);

    public static final List<Integer> SIFENGZHONGFABAI = getList(1,41,41,41,42,42,42,42,43,43,43,43,44,44,44,44,45,45,45,45,46,46,46,46,47,47,47,47);

    public static final List<Integer> getList(Integer... cards){
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

    public static int[] toCardArray(List<Integer> handCards,int extraLength){
        int lengthSize = handCards.size()+extraLength;

        int[] cardsTemp = new int[lengthSize];
        int i = 0;
        for (int card:handCards) {
            cardsTemp[i++] = card;
        }
        return cardsTemp;
    }

    public static void main(String[] args) {
        List<int[]> hucardMap = new ArrayList<>();
        hucardMap.add(new int[]{11,11,14,14,21,21,22,22,26,26,27,27,29,29});//七对
        hucardMap.add(new int[]{11,11,11,11,21,21,21,21,26,26,26,26,29,29});
        hucardMap.add(new int[]{11,11,11,21,21,21,22,22,22,27,27,27,29,29});
        hucardMap.add(new int[]{11,12,13,14,15,16,17,18,19,21,22,23,24,24});
        hucardMap.add(new int[]{14,15,16,16,16,16,17,18,21,22,23,24,25,26});
        hucardMap.add(new int[]{14,15,15,16,16,17,17,17,21,22,23,24,25,26});
        hucardMap.add(new int[]{14,14,15,15,15,16,16,16,21,21,21,22,22,22});
        hucardMap.add(new int[]{11,21,22,23,24,24,24,25,26,27,12,13,14,11});

        List<List<Integer>> hucardMaps= new ArrayList<>();
        for(int[] cards :hucardMap){
            List<Integer> temp = new LinkedList<>();
            for(int car :cards){
                temp.add(car);
            }
            hucardMaps.add(temp);
        }


        long ss = System.currentTimeMillis();
        for(int i = 0;i<10000;i++){

//            for(int[] cards :hucardMap){
//                isSimpleHu(cards,null);
//             //   System.out.println(Arrays.toString(cards)+isSimpleHu(cards,null));
//            }
            for(List<Integer> cards :hucardMaps){
                //isHu(getCardsByType(cards));
                //  System.out.println(Arrays.toString(cards.toArray())+isHu(getCardsByType(cards,0)));
                System.out.println(Arrays.toString(cards.toArray())+isHuMustHavThree(checkThreeAndGetCardsByType(cards,0)));
            }
        }

        System.out.println("==>"+(System.currentTimeMillis()-ss)+"ms");
    }

    public static int[][] getCardsByType(List<Integer> cards,int extraCard){
        int[][] cardTmp = new int[5][];//0为存放的为总数量
        for(Integer card:cards){
            fillArray(cardTmp,card);
        }

        if(extraCard>0){
            fillArray(cardTmp,extraCard);
        }
        return cardTmp;
    }

    private static void fillArray(int[][] cardTmp,int card){
        int type = card/10;
        if(cardTmp[type] == null){
            cardTmp[type] = new int[10];
        }
        cardTmp[type][0]++;
        cardTmp[type][card%10]++;
    }

    /**
     * 检测至少有刻字，如果没有则返回null
     * @param cards
     * @return
     */
    public static int[][] checkThreeAndGetCardsByType(List<Integer> cards,int extraCard){
        int[][] cardTmp = new int[5][];//0为存放的为总数量
        boolean isHasThree = false;
        for(Integer card:cards){
            if(fillArray(cardTmp,card,isHasThree)){
                isHasThree = true;
            }
        }

        if(extraCard>0){
            if(fillArray(cardTmp,extraCard,isHasThree)){
                isHasThree = true;
            }
        }

        if(!isHasThree){
            return null;
        }
        return cardTmp;
    }

    private static boolean fillArray(int[][] cardTmp,int card,boolean isHasThree){
        int type = card/10;
        if(cardTmp[type] == null){
            cardTmp[type] = new int[10];
        }
        cardTmp[type][0]++;
        if(++cardTmp[type][card%10]>2 && !isHasThree){
            return true;
        }
        return false;
    }


    private static boolean checkMatch(int[][] cards){
        int count;
        int n3Count = 0;//满足3n+2的个数
        for (int i = 1;i<cards.length;i++){//检验是否满足3n+2
            if(cards[i]==null){
                continue;
            }
            count = cards[i][0];
            if(count == 0){
                continue;
            }

            count = count%3;
            if(count == 1){
                return false;
            }

            if(count == 2){
                if(n3Count > 0){
                    return false;
                }
                n3Count++;
                continue;
            }
        }

        if(n3Count != 1){
            return false;
        }

        return true;
    }

    public static boolean isQiDui(int[][] cards){
        int count;
        for (int i = 1;i<cards.length;i++){
            if(cards[i] == null){
                continue;
            }
            count = cards[i][0];
            if(count == 0){
                continue;
            }

            count = count%2;
            if(count != 0){
                return false;
            }

            for(int j=0,length=cards[i].length;j<length;j++){
                if(j == 0 || cards[i][j] == 0){
                    continue;
                }
                if(cards[i][j]%2 == 0){
                    continue;
                }

                return false;
            }
        }

        return true;
    }

    public static boolean isHu(int[][] cards){
        if(cards == null || !checkMatch(cards)){
            return false;
        }

        HuData huData = new HuData();
        return huCheck(cards,huData);
    }

    private static boolean huCheck(int[][] cards, HuData huData){
        int count;
        for (int i = 1;i<cards.length;i++){//检验是否满足顺子，刻字
            if(cards[i] == null){
                continue;
            }
            count = cards[i][0];
            if(count == 0){
                continue;
            }

            count = count%3;
            if(count == 0){
                if(i > 3){
                    if(!removeThreeOnly(cards[i],huData)){//东西南北中发白，春夏秋冬，梅兰竹菊
                        return false;
                    }
                }else if(!removeThree(cards[i],huData)){
                    return false;
                }
                continue;
            }

            if(!checkRight(cards[i],huData)){
                return false;
            }
        }

        return true;
    }

    /**
     * 至少含有一个刻字（三张一样的牌）
     * @param cards
     * @return
     */
    public static boolean isHuMustHavThree(int[][] cards){
        if(cards == null || !checkMatch(cards)){
            return false;
        }

        HuData huData = new HuData();
        return huCheck(cards,huData)&&huData.isContanThree();
    }

    private static boolean checkRightFirstCheckThree(int[] cards,HuData huData) {
        int length = cards.length;
        boolean isMatch = false;
        int firstTwo = 0;
        for(int j=0;j<length;j++){
            if(j == 0 || cards[j] < 2){
                continue;
            }else if(cards[j] < 3){
                if(firstTwo!=0){
                    firstTwo = j;
                }
                continue;
            }

            int[] array = Arrays.copyOf(cards,cards.length);
            array[j] = array[j]-3;
            array[0] = array[0]-3;

            for(int i = firstTwo;i < array.length;i++){
                if(cards[i] < 3){
                    continue;
                }
            }

            if(removeLink(getNewInt(cards,j),huData)){
                isMatch = true;
                continue;
            }
        }

        if(!isMatch){
            return false;
        }

        return true;
    }

    /**
     * 3n+2是否满足，一对，其他是顺子或者刻字的情况
     * @param cards
     * @return
     */
    private static boolean checkRight(int[] cards,HuData huData){
        int length = cards.length;
        boolean isMatch = false;
        for(int j=0;j<length;j++){
            if(j == 0 || cards[j] == 0){
                continue;
            }else if(cards[j] > 1){
                if(removeThree(getNewInt(cards,j),huData)){
                    isMatch = true;
                    continue;
                }
            }
        }

        if(!isMatch){
            return false;
        }

        return true;
    }

    private static int[] getNewInt(int[] array,int i){
        int[] newArray = new int[10];
        for(int j=0;j<array.length;j++){
            if(j == 0 || array[j] == 0){
                continue;
            }else if(i == j){
                newArray[j] = array[j]-2;
            }else {
                newArray[j] = array[j];
            }
        }
        newArray[0] = array[0]-2;
        return newArray;
    }

    /**
     * 移除顺子
     * @param cards
     * @return
     */
    private static boolean removeLink(int[] cards,HuData huData){
        //   System.out.println("=======removeLink====>"+Arrays.toString(cards));
        //先判断顺子
        for(int i=0;i<cards.length;i++){
            if(i==0 || cards[i] == 0){
                continue;
            }

            if(i<8){
                if(cards[i+1] ==0 || cards[i+2] ==0){
                    return false;
                }
                cards[0] = cards[0]-3;
                cards[i]--;
                cards[i+1]--;
                cards[i+2]--;

                if(cards[0] == 0){
                    return true;
                }

                if(removeThree(cards,huData)){
                    huData.addLink();
                    return true;
                }
                return false;
            }

            if(cards[i] < 3){
                return false;
            }

            return removeThreeOnly(cards,huData);
        }

        return true;
    }

    private static boolean removeThree(int[] cards,HuData huData){
        //    System.out.println("=======removeThree====>"+Arrays.toString(cards));
        //刻字
        for(int i=0;i<cards.length;i++){
            if(i==0 || cards[i] == 0){
                continue;
            }

            if(cards[i] > 2){
                cards[0] = cards[0]-3;
                cards[i] = cards[i]-3;

                if(cards[0] == 0){
                    return true;
                }
                if(removeThree(cards,huData)){
                    huData.addThree();
                    return true;
                }

            }

            return removeLink(cards,huData);
        }

        return true;
    }

    private static boolean removeThreeOnly(int[] cards,HuData huData){
        //    System.out.println("=======removeThree====>"+Arrays.toString(cards));
        //刻字
        for(int i=0;i<cards.length;i++){
            if(i==0 || cards[i] == 0){
                continue;
            }

            if(cards[i] < 3){
                return false;
            }
            cards[0] = cards[0]-3;
            cards[i] = cards[i]-3;
            if(cards[0] == 0){
                return true;
            }

            if(removeThreeOnly(cards,huData)){
                huData.addThree();
                return true;
            }
            return false;
        }

        return false;
    }

    private static boolean checkRemoveThree(int[] cards,HuData huData){
        //    System.out.println("=======checkRemoveThree====>"+Arrays.toString(cards));
        //刻字
        int[] array = Arrays.copyOf(cards,cards.length);
        for(int i=0;i<array.length;i++){
            if(i==0 || array[i] < 3){
                continue;
            }

            array[0] = array[0]-3;
            array[i] = array[i]-3;
            if(removeLink(array,huData)){
                huData.addThree();
                return true;
            }
            return false;
        }
        return false;
    }

    @Deprecated
    /** 移除对应个数牌，失败返回null */
    public static int[] ArrayRemove(int[] cards, int ocard, int countLimit) {
        int count = 0;
        int[] reCards = new int[cards.length - countLimit];
        int index = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == ocard && count < countLimit) {
                count++;
                continue;
            }
            if (index < reCards.length)
                reCards[index++] = cards[i];
            else {
                return null;
            }
        }
        if (count != countLimit)
            return null;
        return reCards;
    }

    public static class HuData{
        public final static int THREE = 1;//刻字（三个一样的）
        public final static int LINK = THREE<<1;//顺子
        private int blockId;//胡牌的组成（对，刻字，四张和顺子）

        private void add(final int target){
            if((blockId&target) == target){
                return;
            }

            blockId |= target;
        }


        public void addThree(){
            add(THREE);
        }

        public void addLink(){
            add(LINK);
        }

        public boolean isContanThree(){
            return (blockId&THREE) == THREE;
        }

        public int getBlockId() {
            return blockId;
        }
    }
}
