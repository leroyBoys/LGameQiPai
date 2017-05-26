package com.game.core.room;

import com.game.core.room.interfaces.ItemConsume;
import com.game.manager.OnlineManager;
import com.game.socket.module.UserVistor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/27.
 */
public class ItemConsumeService implements ItemConsume {
    private static final ItemConsumeService intance = new ItemConsumeService();
    private ItemConsumeService(){}

    private Set<Integer> whiteFilter = new HashSet<>(10);

    static final ItemConsumeService getIntance(){
        return intance;
    }

    @Override
    public boolean checkCard(int roleId, int needCardCount) {
        if(roleId == 0 || whiteFilter.contains(roleId)){
            return true;
        }

        UserVistor vistor = OnlineManager.getIntance().getRoleId(roleId);
        if(vistor != null && vistor.getGameRole() != null){
            return vistor.getGameRole().getCard()-needCardCount > 0;
        }

        return false;
    }

    @Override
    public void removeCard(int roleId, int needCardCount) {
        if(roleId == 0 || whiteFilter.contains(roleId)){
            return;
        }

        UserVistor vistor = OnlineManager.getIntance().getRoleId(roleId);
        if(vistor != null && vistor.getGameRole() != null){
            vistor.getGameRole().setCard(Math.max(0, vistor.getGameRole().getCard()-needCardCount));
            return;
        }

        ///添加

        //DBServiceManager.getInstance().getGameRedis()
    }

    public Set<Integer> getWhiteFilter() {
        return whiteFilter;
    }

    public void setWhiteFilter(Set<Integer> whiteFilter) {
        this.whiteFilter = whiteFilter;
    }
}
