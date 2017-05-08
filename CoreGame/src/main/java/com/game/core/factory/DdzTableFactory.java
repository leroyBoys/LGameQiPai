package com.game.core.factory;

import com.game.core.config.RoomSetting;
import com.game.core.room.ddz.DDzTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DdzTableFactory implements TableProducer<DDzTable> {
    private RoomSetting gen;
    @Override
    public DDzTable create(int tableId,int ownerId) {
        return new DDzTable(ownerId,gen.getPlayerNum(),tableId,gen.getGameId(),this);
    }

    @Override
    public void setRoomSetting(RoomSetting gen) {
        this.gen = gen;
    }

    @Override
    public RoomSetting getGen() {
        return gen;
    }
}
