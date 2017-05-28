package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.BaseTableVo;
import com.game.room.status.StepGameStatusData;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public interface IPluginCheckCanExecuteAction<T extends BaseTableVo,OP> {
    /***
     * 检测插件支持的行为是否可以生成
     * @param chair
     * @param v
     * @param parems
     * @return
     */
    public boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int v, Object parems);
    /***
     * 行为完成后，生成新的行为对象
     * @param room
     */
    public void createCanExecuteAction(T room,OP op);
}
