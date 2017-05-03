package com.game.room;

import com.game.socket.module.UserVistor;
import com.game.core.room.BaseStepHistory;
import com.game.core.room.BaseTableVo;
import com.lgame.util.comm.RandomTool;

import java.util.*;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MjTable extends BaseTableVo<MjStatus,MjChairInfo> {
    /** 庄家id */
    private int bankId;
    ////顺序东北西南->0-3

    private int nextBankerUid;

    public MjTable(int ownerId,int maxSize,int id,int gameId) {
        super(ownerId,maxSize,id, MjStatus.Idle,gameId);
    }

    @Override
    protected void initStatus() {
        allStatus = new MjStatus[]{MjStatus.Idle, MjStatus.Pao, MjStatus.FaPai, MjStatus.Game};
    }

    @Override
    protected void initChair(int maxSize) {
        chairs = new MjChairInfo[maxSize];
    }

    @Override
    public <History extends BaseStepHistory> History getStepHistoryManager() {
        return null;
    }

    @Override
    public void cleanTableCache() {

    }

    @Override
    public MjChairInfo createChair(UserVistor visitor) {
        MjChairInfo chairInfo = new MjChairInfo(visitor.getUid());
        chairInfo.setIp(visitor.getIp().getIp());
        return chairInfo;
    }

    public Map<Integer,Integer[]> randomChairPosition(int diceCount){

        List<Integer[]> playerScore = new ArrayList<>(chairs.length);
        Map<Integer,Integer[]> diceDetail = new HashMap<>(chairs.length);
        for(int i = 0;i<chairs.length;i++){
            int count = diceCount;
            Integer[] allCounts = new Integer[diceCount];
            int allCountVaule = 0;
            while (count-- > 0){
                allCounts[count] = RandomTool.Next(6)+1;
                allCountVaule+=allCounts[count];
            }

            playerScore.add(new Integer[]{chairs[i].getId(),allCountVaule});
            diceDetail.put(chairs[i].getId(),allCounts);
        }


        Collections.sort(playerScore, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o2[1] - o1[1] ;
            }
        });

        for(int i=0; i<chairs.length; i++){
            chairs[i] = getChairByUid(playerScore.get(i)[0]);
            chairs[i].setIdx(i);
        }

        return diceDetail;
    }




    /////////////////////////////////////////////////////////////
    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
        this.setFocusIdex(this.getChairByUid(bankId).getIdx());
    }

    public int getNextBankerUid() {
        return nextBankerUid;
    }

    public void setNextBankerUid(int nextBankerUid) {
        this.nextBankerUid = nextBankerUid;
    }
}
