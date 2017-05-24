package com.game.core.config;

import com.game.core.room.BaseTableVo;
import com.game.core.room.SuperCreateNew;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.List;

public interface IOptPlugin<A extends BaseTableVo,OP> extends SuperCreateNew {
	public IOptPlugin createNew();

	public void setPlugin(PluginGen plugin);

	public PluginGen getPlugin();

	/** 执行这个插件的操作 */
	public boolean doOperation(A table,Response response,int roleId, OP op) ;

}
