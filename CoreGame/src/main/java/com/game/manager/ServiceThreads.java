package com.game.manager;

import com.lgame.util.thread.BaseThreadPools;

/**
 * Created by Administrator on 2017/5/25.
 */
public class ServiceThreads extends BaseThreadPools implements ServiceThreadPool {
    @Override
    public void execute(Runnable runnable) {
        super.execute(runnable);
    }
}
