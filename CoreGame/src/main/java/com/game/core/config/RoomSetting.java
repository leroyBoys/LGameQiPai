package com.game.core.config;

import com.lgame.util.comm.StringTool;
import com.lgame.util.load.annotation.Id;
import com.lgame.util.load.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
@Resource(suffix="xls")
public class RoomSetting {
    /**主键*/
    @Id
    private int gameId = 0;
    /**参与人数*/
    private int playerNum = 0;
    /**棋牌库*/
    private String cardNumPool;
    /**初始手牌数*/
    private int initHandCardCount;
    private String roomFactory;

    /**总局数:消耗房卡数 如 4:3,8:5*/
    private String cardSet;

    private Map<Integer,Integer> cardSetMap = new HashMap<>();

    private short baseScore;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public String getCardNumPool() {
        return cardNumPool;
    }

    public void setCardNumPool(String cardNumPool) {
        this.cardNumPool = cardNumPool;
    }

    public int getInitHandCardCount() {
        return initHandCardCount;
    }

    public void setInitHandCardCount(int initHandCardCount) {
        this.initHandCardCount = initHandCardCount;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;

        if(cardSet == null){
            return;
        }
        String[] arrays = cardSet.split(StringTool.SIGN4);
        for(String strs:arrays){
            String[] cardsSetArray = strs.split(StringTool.SIGN3);
            cardSetMap.put(Integer.valueOf(cardsSetArray[0]),Integer.valueOf(cardsSetArray[1]));
        }

    }

    public short getBaseScore() {
        return baseScore;
    }

    public String getRoomFactory() {
        return roomFactory;
    }

    public void setRoomFactory(String roomFactory) {
        this.roomFactory = roomFactory;
    }

    public void setBaseScore(short baseScore) {
        this.baseScore = baseScore;
    }

    public Map<Integer, Integer> getCardSetMap() {
        return cardSetMap;
    }
}
