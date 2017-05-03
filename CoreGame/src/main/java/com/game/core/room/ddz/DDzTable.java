package com.game.core.room.ddz;

import com.game.core.UserVistor;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseStepHistory;
import com.game.core.room.BaseTableVo;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DDzTable extends BaseTableVo<DdzStatus,BaseChairInfo> {
    private int guildUid;

    public DDzTable(int ownerId,int maxSize,int id,int gameId) {
        super(ownerId,maxSize,id, DdzStatus.Idle,gameId);
    }

    @Override
    protected void initStatus() {
        allStatus = new DdzStatus[]{DdzStatus.Idle,DdzStatus.FaPai,DdzStatus.Score,DdzStatus.Game};
    }

    @Override
    protected void initChair(int maxSize) {

    }

    @Override
    public void cleanTableCache() {

    }

    @Override
    public BaseStepHistory getStepHistoryManager() {
        return null;
    }

    @Override
    public BaseChairInfo createChair(UserVistor visitor) {
        return null;
    }

    public int getGuildUid() {
        return guildUid;
    }

    public void setGuildUid(int guildUid) {
        this.guildUid = guildUid;
    }

}
