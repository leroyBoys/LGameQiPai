package com.game.room.action.basePlugins;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class HuPlugins extends IPluginHuCheck{
    /**
     * 用于重叠胡牌的计算优先级
     * @return
     */
    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public HuPlugins createNew() {
        return new HuPlugins();
    }
}
