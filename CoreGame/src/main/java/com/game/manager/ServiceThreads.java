package com.game.manager;

import com.lgame.util.thread.BaseThreadPools;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/5/25.
 */
public class ServiceThreads extends BaseThreadPools implements ServiceThreadPool {
    public ServiceThreads(int min,int max){
        super(min,max, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void execute(Runnable runnable) {
        super.execute(runnable);
    }
}
