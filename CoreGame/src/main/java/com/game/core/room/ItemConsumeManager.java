package com.game.core.room;

import com.game.core.room.interfaces.ItemConsume;
import com.game.manager.OnlineManager;
import com.game.socket.module.UserVistor;

/**
 * Created by Administrator on 2017/5/27.
 */
public class ItemConsumeManager implements ItemConsume {
    protected ItemConsumeService itemConsumeService = ItemConsumeService.getIntance();
    private static final ItemConsumeManager intance = new ItemConsumeManager();
    private ItemConsumeManager(){}

    public static final ItemConsumeManager getIntance(){
        return intance;
    }

    @Override
    public boolean checkCard(int roleId, int needCardCount) {
        return itemConsumeService.checkCard(roleId,needCardCount);
    }

    @Override
    public void removeCard(int roleId, int needCardCount) {
        itemConsumeService.removeCard(roleId,needCardCount);
    }

    public void setItemConsumeService(ItemConsumeService itemConsumeService) {
        this.itemConsumeService = itemConsumeService;
    }
}
