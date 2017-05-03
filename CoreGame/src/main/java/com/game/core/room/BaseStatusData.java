package com.game.core.room;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class BaseStatusData implements SuperCreateNew{
    private boolean isOver;

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public BaseStatusData  createNew(){
        return new BaseStatusData();
    }


    public static class DefaultStatusData extends BaseStatusData {
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

        public int getDoneSize(){
            return doneUids.size();
        }

        public DefaultStatusData createNew(){
            return new DefaultStatusData();
        }
    }

}
