package com.game.room.action;

import com.game.core.config.IOptPlugin;
import com.game.core.config.TablePluginManager;
import com.game.core.room.BaseStatusData;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;

import java.util.ArrayList;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class GameStatusData extends BaseStatusData {

    public void moPai(MjTable table, int uid){
        table.setFocusIdex(table.getChairByUid(uid).getIdx());
        ArrayList<IOptPlugin> optPlugins = TablePluginManager.getInstance().getOptPlugin(table.getGameId(),1);
        for(int i= 0;i<optPlugins.size();i++){
            optPlugins.get(i).doOperation(table,null,null);
        }
    }

    protected boolean canCheckGang(MjChairInfo chairInfo,int card){
        return true;
    }

    public void checkGang(MjChairInfo chairInfo,int card) {
        if(!canCheckGang(chairInfo,card)){
            return;
        }


    }

    public void checkChi() {
    }

    public void checkPeng() {
    }

    public void checkHu() {
    }
}
