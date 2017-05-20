package com.game.core.config;

import com.game.core.room.BaseTableVo;
import com.game.core.room.SuperCreateNew;
import com.lsocket.message.Response;
import com.module.net.NetGame;

public interface IOptPlugin<A extends BaseTableVo,OP> extends SuperCreateNew {
	public IOptPlugin createNew();

	public int getWeight();

	public void setPluginId(int pluginId);

	/** 执行这个插件的操作 */
	public Object doOperation(A table,Response response,int roleId, OP op) ;

}
