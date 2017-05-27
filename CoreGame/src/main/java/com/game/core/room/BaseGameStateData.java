package com.game.core.room;

import com.game.core.constant.GameConst;
import com.module.net.NetGame;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class BaseGameStateData implements SuperCreateNew{
    protected Set<Integer> doneUids = new HashSet<>(4);
    private boolean isOver;

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
    public boolean contains(int uid){
        return doneUids.contains(uid);
    }

    public BaseGameStateData createNew(){
        return new BaseGameStateData();
    }

    /**
     *
     * @param tableVo
     * @param roleId
     * @return
     */
    public NetGame.NetOprateData.Builder getCanDoDatas(BaseTableVo tableVo,int roleId){
        NetGame.NetOprateData.Builder canDoActions = NetGame.NetOprateData.newBuilder();
        canDoActions.setOtype(GameConst.MJ.ACTION_TYPE_CanDoActions);

        if(!contains(roleId)){
            NetGame.NetKvData.Builder kvData = NetGame.NetKvData.newBuilder();
            kvData.setK(tableVo.getStatus().getAction().getActionType());
            canDoActions.addKvDatas(kvData);
        }
        return canDoActions;
    }

    public NetGame.NetOprateData.Builder getStatusDetail(BaseTableVo tableVo){
        NetGame.NetOprateData.Builder netOpers = NetGame.NetOprateData.newBuilder();
        netOpers.setOtype(tableVo.getStatus().getAction().getActionType());
        for(int i = 0;i<tableVo.getChairs().length;i++){
            if(tableVo.getChairs()[i] == null || !doneUids.contains(tableVo.getChairs()[i].getId())){
                continue;
            }

            int id = tableVo.getChairs()[i].getId();
            NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
            netKvData.setK(id);
            netKvData.setV(1);
            netOpers.addKvDatas(netKvData);
        }

        return netOpers;
    }

    /**
     * 如果已有则返回-1；否则返回当前的数量
     * @param uid
     * @return
     */
    public synchronized int addDoneUid(int uid){
        if(doneUids.contains(uid)){
            return -1;
        }
        doneUids.add(uid);
        return getDoneSize();
    }

    public int getDoneSize(){
        return doneUids.size();
    }

    public static class SystemStatusData extends BaseGameStateData {

        public SystemStatusData createNew(){
            return new SystemStatusData();
        }

        @Override
        public NetGame.NetOprateData.Builder getCanDoDatas(BaseTableVo tableVo,int roleId) {
            return null;
        }

        @Override
        public NetGame.NetOprateData.Builder getStatusDetail(BaseTableVo tableVo) {
            return null;
        }

    }
}
