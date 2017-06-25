package com.game.admin;

import com.game.Handler.GameCommCmd;
import com.game.core.TableManager;
import com.game.core.chat.ChatChannelManager;
import com.game.core.chat.ChatFilter;
import com.game.core.room.BaseTableVo;
import com.game.core.room.ddz.DdzStatus;
import com.game.manager.RobotManager;
import com.game.socket.module.UserVistor;
import com.lgame.util.comm.StringTool;
import com.lsocket.handler.CmdModule;
import com.lsocket.manager.CMDManager;
import com.lsocket.message.Request;
import com.lsocket.message.Response;
import com.module.ChannelType;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/19.
 */
public class AdminCmdFilter implements ChatFilter {
    private static final char cmdPrefix = '!';
    private static final String cmdDiffChar = ":";
    private static final String cmdDiffParamterChar = StringTool.SIGN4;
    private static AdminCmdFilter ourInstance = new AdminCmdFilter();
    public static AdminCmdFilter getInstance() {
        return ourInstance;
    }
    private AdminCmdFilter() {
    }

    public void load(){
    }

    private boolean isAdmin(ChannelType type,String content){
        if(ChannelType.room != type){
            return false;
        }

        if(StringTool.isEmpty(content) || content.length() < 2 || content.charAt(0) != cmdPrefix){
            return false;
        }

        return true;
    }

    @Override
    public boolean sendMsg(ChannelType type, UserVistor vistor, int receiveUid, String content, int isActionEffect) {
        if(!isAdmin(type,content)){
            return false;
        }

        content = content.substring(1);
        String[] cmdStrArray = content.split(cmdDiffChar);
        String cmd = cmdStrArray[0];

        AdminCmdHandler.Handler adminHandler = AdminCmdHandler.Handler.valueOf(cmd);
        if(adminHandler == null){
            return false;
        }

        adminHandler.getDispatch().dispatch(cmdStrArray.length>1?cmdStrArray[1]:null,vistor);
        return true;
    }


    @Override
    public boolean sendSystemMsg(ChannelType type, int receiveUid, String msg) {
        return false;
    }
    @Override
    public boolean sendSystemMsg(int uid, String msg) {
        return false;
    }

    private void sendMsg(int roleId,String content){
        ChatChannelManager.getInstance().sendSystemMsg(roleId,content);
    }

    public void ready(String content, UserVistor vistor) {
        int tableId = vistor.getGameRole().getRoomId();
        BaseTableVo tableVo = TableManager.getInstance().getTable(tableId);
        if(tableVo == null || tableVo.getStatus().getValue() != 0){
            sendMsg(vistor.getRoleId(),"not exit tableId:"+tableId);
            return;
        }

        for(int i = 0;i<tableVo.getChairs().length;i++){
            if(tableVo.getChairs()[i] == null || tableVo.getStatusData().contains(tableVo.getChairs()[i].getId())){
                continue;
            }

            DdzStatus.Idle.getAction().doAction(tableVo,null,tableVo.getChairs()[i].getId(),null);
        }

    }

    public void robot(String content, UserVistor vistor) {
        String[] array = content.split(cmdDiffParamterChar);
        int num = array.length == 0?10:Integer.valueOf(array[0]);
        int tableId = array.length < 2?0:Integer.valueOf(array[1]);

        if(tableId == 0){
            tableId = vistor.getGameRole().getRoomId();
        }
        BaseTableVo tableVo = TableManager.getInstance().getTable(tableId);
        if(tableVo == null){
            sendMsg(vistor.getRoleId(),"not exit tableId:"+tableId);
            return;
        }
        addRobot(num,tableVo);
    }

    public void addRobot(int num,BaseTableVo tableVo){
        num = Math.min(num,tableVo.getChairs().length);

        final CmdModule enteryFunction =CMDManager.getIntance().getCmdModule(GameCommCmd.ENTER_GAME.getModule(),GameCommCmd.ENTER_GAME.getValue());
        Response response = Response.defaultResponse(GameCommCmd.ENTER_GAME.getModule(),GameCommCmd.ENTER_GAME.getValue());
        Request request = Request.valueOf(GameCommCmd.ENTER_GAME.getModule(),GameCommCmd.ENTER_GAME.getValue());

        NetGame.RPEnterRoom.Builder builder = NetGame.RPEnterRoom.newBuilder();
        builder.setRoomId(tableVo.getId());
        request.setObj(builder.build());
        for(int i = 0;i<tableVo.getChairs().length;i++){
            if(tableVo.getChairs()[i] != null){

              //  tableVo.getChairs()[i].setAuto(true);
                continue;
            }

            try {
                enteryFunction.invoke(RobotManager.getInstance().getRobot(),request,response);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            if(--num == 0){
                break;
            }
        }
    }

    public void round(String content, UserVistor vistor) {
    }

    public void resetCards(String content, UserVistor vistor) {
    }

    public void addCards(String content, UserVistor vistor) {
    }
}
