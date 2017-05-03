package com.game.core.factory;

import com.game.core.room.BaseTableVo;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public interface TableProducer<T extends BaseTableVo> {
    public T create(int tableId, int ownerId);
}
