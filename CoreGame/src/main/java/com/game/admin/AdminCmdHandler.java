package com.game.admin;

import com.game.socket.module.UserVistor;

/**
 * Created by Administrator on 2017/2/21.
 */
public interface AdminCmdHandler{
    public void dispatch(String content, UserVistor vistor);

    public enum Handler{
        ready(new AdminCmdHandler() {
            @Override
            public void dispatch(String content, UserVistor vistor) {
                AdminCmdFilter.getInstance().ready(content,vistor);
            }
        }),
        robot(new AdminCmdHandler() {
            @Override
            public void dispatch(String content, UserVistor vistor) {
                AdminCmdFilter.getInstance().robot(content,vistor);
            }
        }),
        /** 设置轮数 */
        round(new AdminCmdHandler() {
            @Override
            public void dispatch(String content, UserVistor vistor) {
                AdminCmdFilter.getInstance().round(content,vistor);
            }
        }),

        /** 设置特殊牌型 */
        cs(new AdminCmdHandler() {
            @Override
            public void dispatch(String content, UserVistor vistor) {
                AdminCmdFilter.getInstance().resetCards(content,vistor);
            }
        }),

        /** 添加牌 */
        add(new AdminCmdHandler() {
            @Override
            public void dispatch(String content, UserVistor vistor) {
                AdminCmdFilter.getInstance().addCards(content,vistor);
            }
        }),
        ;

        private final AdminCmdHandler dispatch;
        Handler(AdminCmdHandler dispatch){
            this.dispatch = dispatch;
        }

        public AdminCmdHandler getDispatch(){
            return dispatch;
        }
    }
}
