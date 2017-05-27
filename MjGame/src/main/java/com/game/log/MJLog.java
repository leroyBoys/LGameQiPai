package com.game.log;

import com.game.core.room.BaseChairInfo;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjTable;
import com.game.room.action.SuperGameStatusData;
import com.lgame.util.json.JsonTool;
import com.logger.type.LogType;
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
        playLog.info("step:"+ table.getStep()+"  roleId:"+roleId+"  "+playerName+">"+card+" handSize:"+hands.size()+">>"+ Arrays.toString(hands.toArray()));

        playLogDetail.info("step:"+ table.getStep()+"  roleId:"+roleId+"  "+playerName+">"+card+" handSize:"+hands.size()+">>"+ Arrays.toString(hands.toArray())+
                "auto:"+autoCache.toJson()+" remains cardPools:"+table.getCardPool().getRemainCount());
    }

    public static void canDoActions(MjTable table){
        SuperGameStatusData gameStatusData = table.getStatusData();
        playLogDetail.info(gameStatusData.toJson());
    }
}
