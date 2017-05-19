package com.game.core.admin;

import com.game.socket.module.UserVistor;

/**
 * Created by Administrator on 2017/2/21.
 */
public interface AdminCmdHandler{
    public void dispatch(String content, UserVistor vistor);

    public enum Handler{
        Ready(new AdminCmdHandler() {
            @Override
            public void dispatch(String content, UserVistor vistor) {
                AdminCmdFilter.getInstance().ready(content,vistor);
            }
        });

        private final AdminCmdHandler dispatch;
        Handler(AdminCmdHandler dispatch){
            this.dispatch = dispatch;
        }

        public AdminCmdHandler getDispatch(){
            return dispatch;
        }
    }
}
