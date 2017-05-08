package com.game.core.room.ddz;

import com.game.core.factory.TableProducer;
import com.game.socket.module.UserVistor;
import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseStepHistory;
import com.game.core.room.BaseTableVo;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DDzTable extends BaseTableVo<DdzStatus,BaseChairInfo> {
    private int guildUid;

    public DDzTable(int ownerId, int maxSize, int id, int gameId, TableProducer tableProducer) {
        super(ownerId,maxSize,id, DdzStatus.Idle,gameId,tableProducer);
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
    public NetGame.NetExtraData.Builder getExtra() {
        return null;
    }

    @Override
    protected NetGame.NetExtraData getOtherNetExtraData(BaseChairInfo baseChairInfo) {
        return null;
    }

    @Override
    protected NetGame.NetExtraData getSelfNetExtraData(BaseChairInfo baseChairInfo) {
        return null;
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
