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
    private boolean isOver;

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
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
        return canDoActions;
    }

    public NetGame.NetOprateData.Builder getStatusDetail(BaseTableVo tableVo){
        NetGame.NetOprateData.Builder netOpers = NetGame.NetOprateData.newBuilder();
        return netOpers;
    }

    public static class DefaultStatusData extends BaseGameStateData {
        private Set<Integer> doneUids = new HashSet<>(4);

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

        public boolean contains(int uid){
            return doneUids.contains(uid);
        }

        public int getDoneSize(){
            return doneUids.size();
        }

        public DefaultStatusData createNew(){
            return new DefaultStatusData();
        }

        @Override
        public NetGame.NetOprateData.Builder getCanDoDatas(BaseTableVo tableVo,int roleId) {
            NetGame.NetOprateData.Builder canDoActions = super.getCanDoDatas(tableVo,roleId);
            if(!doneUids.contains(roleId)){
                return canDoActions;
            }

            return null;
        }

        @Override
        public NetGame.NetOprateData.Builder getStatusDetail(BaseTableVo tableVo) {
            NetGame.NetOprateData.Builder netOpers = super.getStatusDetail(tableVo);
            for(int i = 0;i<tableVo.getChairs().length;i++){
                if(tableVo.getChairs()[i] == null){
                    continue;
                }

                int id = tableVo.getChairs()[i].getId();
                NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
                netKvData.setK(id);
                netKvData.setV(doneUids.contains(id)?1:0);
                netOpers.addKvDatas(netKvData);
            }

            return netOpers;
        }

    }

    public static class SystemStatusData extends BaseGameStateData {

        public DefaultStatusData createNew(){
            return new DefaultStatusData();
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
