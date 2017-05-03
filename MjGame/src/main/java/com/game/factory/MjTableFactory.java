package com.game.factory;


import com.game.core.config.RoomSetting;
import com.game.core.factory.TableProducer;
import com.game.room.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MjTableFactory implements TableProducer<MjTable> {
    private RoomSetting gen;

    @Override
    public MjTable create(int tableId,int ownerId) {
        return new MjTable(ownerId,gen.getPlayerNum(),tableId,gen.getGameId());
    }

    @Override
    public void setRoomSetting(RoomSetting gen) {
        this.gen = gen;
    }
}
