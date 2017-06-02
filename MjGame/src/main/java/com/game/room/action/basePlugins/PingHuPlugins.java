package com.game.room.action.basePlugins;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class PingHuPlugins extends IPluginHuCheck{
    /**
     *
     * @return
     */
    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public PingHuPlugins createNew() {
        return new PingHuPlugins();
    }
}
