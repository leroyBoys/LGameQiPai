package com.game.core.config;

import com.game.core.room.BaseTableVo;
import com.game.core.room.SuperCreateNew;
import com.lsocket.message.Response;
import com.module.net.NetGame;

public interface IOptPlugin<A extends BaseTableVo> extends SuperCreateNew {
	public IOptPlugin createNew();
	/** 执行这个插件的操作 */
	public Object doOperation(A table, Response response, NetGame.NetOprateData oprateData) ;

}
