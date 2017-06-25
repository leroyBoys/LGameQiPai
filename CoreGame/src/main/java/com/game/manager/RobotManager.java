package com.game.manager;

import com.game.core.TableManager;
import com.game.socket.module.GameRole;
import com.game.socket.module.RobotVistor;
import com.game.socket.module.UserVistor;
import com.lgame.util.comm.RandomTool;
import com.module.db.RoleInfo;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Administrator on 2017/2/24.
 */
public class RobotManager {
    private final static RobotManager robotManager = new RobotManager();
    private ConcurrentLinkedDeque<Integer> robots = new ConcurrentLinkedDeque<>();
    private ConcurrentHashMap<Integer,Integer> robotUseds = new ConcurrentHashMap<>();//uid-roomid

    private RobotManager(){
        for(int i = 1;i<10000;i++){
            robots.add(i);
        }
    }

    public static RobotManager getInstance(){
        return robotManager;
    }

    public void GCRobot(){
        if(robotUseds.isEmpty()){
            return;
        }

        Iterator<Map.Entry<Integer, Integer>> it = robotUseds.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, Integer> robotDatas=it.next();
            if(TableManager.getInstance().getTable(robotDatas.getValue()) != null){
                if(TableManager.getInstance().getTable(robotDatas.getValue()).getChairByUid(robotDatas.getKey()) != null){
                    continue;
                }
            }
            it.remove();
            robots.add(robotDatas.getKey());
        }
    }


    public UserVistor getRobot(){
        if(robots.isEmpty()){
            return null;
        }

        int playerId = robots.poll();
        String name = "robot"+playerId;
        UserVistor rb = new RobotVistor(playerId);
        RoleInfo info = new RoleInfo();
        info.setUserSex(1);
        info.setId(playerId);
        info.setUserAlise(name);
        info.setHeadImage("o");

        rb.setRoleInfo(info);

        GameRole gameRole = new GameRole(playerId,0, RandomTool.Next(100)+5);
        rb.setGameRole(gameRole);

        rb.setIp("192.168.1."+(new Random().nextInt(120)+1),99);
        return rb;
    }

}
