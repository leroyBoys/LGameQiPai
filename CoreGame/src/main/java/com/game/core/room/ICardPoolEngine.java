package com.game.core.room;

import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/25.
 */
public interface ICardPoolEngine<C> {
    /**
     * 扩展的初始化方法
     */
    public abstract void init();
    /***
     * 洗牌
     */
    public void shuffle();

    public int getRemainCount();

    public List<C> getRemainCards();

    /**
     * 总张数
     * @return
     */
    public int getAllSize();
}
