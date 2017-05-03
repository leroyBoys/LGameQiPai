package com.game.core.factory;

import com.game.core.room.mj.MjTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class MjTableFactory implements TableProducer<MjTable> {
    @Override
    public MjTable create(int tableId,int ownerId) {
        return new MjTable(ownerId,4,tableId);
    }
}
