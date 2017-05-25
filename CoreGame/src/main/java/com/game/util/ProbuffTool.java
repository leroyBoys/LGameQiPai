package com.game.util;

import com.game.core.constant.GameConst;
import com.module.net.NetGame;

import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/25.
 */
public class ProbuffTool {

    public static final NetGame.NetOprateData.Builder getTurnData(int uid,int card,int timeOut){
        NetGame.NetOprateData.Builder retOperaData = getNetOperateData(GameConst.ACTION_TYPE_TURN,uid,card);
        retOperaData.setFlag(timeOut);
        return retOperaData;
    }

    public static final NetGame.NetOprateData.Builder getNetOperateData(int otype,int uid,int dval){
        NetGame.NetOprateData.Builder retOperaData = NetGame.NetOprateData.newBuilder();
        retOperaData.setOtype(otype);
        retOperaData.setUid(uid);
        retOperaData.setDval(dval);
        return retOperaData;
    }

    public static final NetGame.NetOprateData.Builder getNetOperateData(int otype,int uid,int dval,List<Integer> list){
        NetGame.NetOprateData.Builder retOperaData = getNetOperateData(otype,uid,dval);
        if(list != null){
            retOperaData.addAllDlist(list);
        }
        return retOperaData;
    }

}
