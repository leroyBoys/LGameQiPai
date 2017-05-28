package com.game.log;

import com.game.core.room.BaseChairInfo;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjTable;
import com.game.room.action.SuperGameStatusData;
import com.lgame.util.json.JsonTool;
import com.logger.type.LogType;
import com.module.net.NetGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */
public class MJLog {
    protected static  final Logger playLogDetail = LoggerFactory.getLogger(LogType.Play.getLogName()+"d");
    protected static  final Logger playLog = LoggerFactory.getLogger(LogType.Play.getLogName());
    public static void play(String playerName,Object card,int roleId, MjTable table){
        BaseChairInfo info = table.getChairByUid(roleId);
        MjAutoCacheHandContainer autoCache = (MjAutoCacheHandContainer) info.getHandsContainer().getAutoCacheHands();

        List<Integer> hands = info.getHandsContainer().getHandCards();
        playLogDetail.info("step:"+ table.getStep()+"  roleId:"+roleId+"  "+playerName+">"+card+" handSize:"+hands.size()+">>"+ Arrays.toString(hands.toArray()));

        playLog.info("step:"+ table.getStep()+"  roleId:"+roleId+"  "+playerName+">"+card+" handSize:"+hands.size()+">>"+ Arrays.toString(hands.toArray())+
                "auto:"+autoCache.toJson());
    }

    public static void requset(NetGame.NetOprateData netOprateData, int roleId, MjTable table){
        SuperGameStatusData gameStatusData = table.getStatusData();
        playLogDetail.info("request===>step:"+ table.getStep()+"  roleId:"+roleId+"  "+gameStatusData.getFirst().getAction().getClass().getSimpleName()+">"+(netOprateData==null?"":netOprateData.toString()));
        playLogDetail.info("        所有可以操作的列表===>"+gameStatusData.toJson());
    }

    public static void canDoActions(MjTable table){
        try {
            SuperGameStatusData gameStatusData = table.getStatusData();
            playLogDetail.info(gameStatusData.toJson());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void canDoActions2(String tip,int step,SuperGameStatusData gameStatusData){
        try {
            playLogDetail.info("add:=step:"+step+"="+tip+"=>"+gameStatusData.toJson());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void huCheck(String tip,int roleId,MjTable table){
        BaseChairInfo info = table.getChairByUid(roleId);
        MjAutoCacheHandContainer autoCache = (MjAutoCacheHandContainer) info.getHandsContainer().getAutoCacheHands();

        List<Integer> hands = info.getHandsContainer().getHandCards();

        playLog.info("==hucheck:hands"+Arrays.toString(hands.toArray())+"auto:"+autoCache.toJson()+"  "+tip);
    }

}
