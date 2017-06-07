package com.game.socket.control;

import com.game.core.service.UserService;
import com.game.manager.DBServiceManager;
import com.game.manager.OnlineManager;
import com.game.socket.module.UserVistor;
import com.logger.log.SystemLogger;
import com.lsocket.control.impl.CoreDispatcher;
import com.lsocket.handler.ModuleHandler;
import com.lsocket.message.Request;
import org.apache.mina.core.session.IdleStatus;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class CoreDispatcherRmote extends CoreDispatcher<UserVistor,Request> {

    public void session_closed(UserVistor visitor) {
        try{
           /* if(!visitor.isSelfOffLine()){
                return;
            }*/

            try{
                for(ModuleHandler moduleHandler: moduleHandlers.values()){
                    moduleHandler.session_closed(visitor);
                }
            }catch (Exception e){
                SystemLogger.error(this.getClass(),e);
            }

            if(visitor.getGameRole() != null){
                visitor.getGameRole().update();
            }

            UserService userService = DBServiceManager.getInstance().getUserService();
            if(visitor.getRoleInfo() != null){
                userService.offLine(visitor.getRoleInfo().getUid());
            }
        }catch (Exception e){
            SystemLogger.error(this.getClass(),e);
        }finally {
            OnlineManager.getIntance().removeFromOnlineList(visitor.getIoSession());
        }

    }

    public void sessionIdle(UserVistor visitor, IdleStatus idleStatus) {

    }
}
