package com.game.core.config;

import com.lgame.util.json.JsonTool;
import com.lgame.util.load.xml.XmlApi;
import com.logger.log.SystemLogger;
import com.mongodb.util.JSON;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/19.
 */
public class GameSwitchManager {
    private int switchValue;
    private static GameSwitchManager ourInstance = new GameSwitchManager();

    public static GameSwitchManager getInstance() {
        return ourInstance;
    }

    private GameSwitchManager() {
    }

    public List<GameSwitch> getSwiths(){
        String[] arrays = SwithConfig.getInstance().getDetail().split(",");
        List<GameSwitch> switches = new LinkedList<>();
        for(String str:arrays){
            switches.add(new GameSwitch(str));
        }
        return switches;
    }

    public List<GameSwitch> loadXml(String path){
        //  String path = PropertiesTool.getPropertiesPath("gameSwitch.xml");
        GameSwitch gameSwitch = XmlApi.readObjectFromXml(GameSwitch.class,path);
        if(gameSwitch == null){
            return null;
        }

        List<GameSwitch> switches = gameSwitch.getSwitchList();
        return switches;
    }

    public void load(String path ){
        int switchVaule = 0;
        List<GameSwitch> switches = getSwiths();// loadXml(path);
        if(switches == null|| switches.isEmpty()){
            SystemLogger.error(this.getClass(),"load GameSwitch faild");
            return;
        }

        for(GameSwitch sw:switches){
            if(!sw.isOpen()){
                continue;
            }else if(GameSwitch.Type.getType(sw.getSwitchId()) == null){
                SystemLogger.warn(this.getClass(),"cant find switchId:"+sw.getSwitchId());
                continue;
            }
            switchVaule = switchVaule | 1<<sw.getSwitchId();
        }

        switchValue = switchVaule;
        System.out.println(JsonTool.getJsonFromBean(switches));
    }

    public boolean isOpen(GameSwitch.Type type){
        return (switchValue&type.getVal()) == type.getVal();
    }

    public boolean isOpen(GameSwitch.Type type,int roleId){
        if(!isOpen(type)){
            return false;
        }

        int userSwith = 0;
        return (userSwith&type.getVal()) == type.getVal();
    }
}
