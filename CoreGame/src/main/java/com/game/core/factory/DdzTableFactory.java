package com.game.core.factory;

import com.game.core.room.ddz.DDzTable;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class DdzTableFactory implements TableProducer<DDzTable> {
    @Override
    public DDzTable create(int tableId,int ownerId) {
        return new DDzTable(ownerId,3,tableId);
    }
}
