package com.game.core.room.card;

import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/24.
 */
public class AutoCacheHandContainer {
    protected volatile boolean isChange = true;

    public void clear() {
        isChange = true;
    }

    public void reLoad(List<Integer> hands){

        isChange = false;
    }

    public void check(List<Integer> hands) {
        if(isChange){
            reLoad(hands);
        }
    }
}
