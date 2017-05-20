package com.game.room;

import com.game.core.config.IOptPlugin;
import com.game.core.room.BaseStatusData;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class StepGameStatusData extends BaseStatusData {
    private final IOptPlugin iOptPlugin;
    private final int fromRoleId;
    private final int uid;
    private List<List<Integer>> dataList = new LinkedList<>();
    private int card;
    private Set<Integer> subTypes = new HashSet<>();
    private final int type;

    public StepGameStatusData(int type,int fromRoleId,int uid,IOptPlugin iOptPlugin){
        this.type = type;
        this.fromRoleId = fromRoleId;
        this.uid = uid;
        this.iOptPlugin = iOptPlugin;
    }

    public void addDatas(int card){
        this.card = card;
    }

    public void addDatas(int subTypeId,List<Integer> datas){
        if(subTypeId > 0){
            subTypes.add(subTypeId);
        }

        dataList.add(datas);
    }

    public boolean isRight(int subType,List<Integer> datas){
        if(!subTypes.contains(subType) || datas == null || datas.isEmpty()){
            return false;
        }

        for(List<Integer> list:dataList){
            if(list.size() != datas.size()){
                return false;
            }

            for(int i=0;i<list.size();i++){
                if(list.get(i) != datas.get(i)){
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public boolean isRight(int card){
        return this.card == card;
    }

    public IOptPlugin getiOptPlugin() {
        return iOptPlugin;
    }

    public int getFromRoleId() {
        return fromRoleId;
    }

    public int getType() {
        return type;
    }

    public int getUid() {
        return uid;
    }
}
