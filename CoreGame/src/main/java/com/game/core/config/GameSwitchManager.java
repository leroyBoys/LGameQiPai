package com.game.core.config;

import com.lgame.util.load.xml.XmlApi;
import com.logger.log.SystemLogger;

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

    public void load(String path ){
      //  String path = PropertiesTool.getPropertiesPath("gameSwitch.xml");
        GameSwitch gameSwitch = XmlApi.readObjectFromXml(GameSwitch.class,path);
        if(gameSwitch == null){
            SystemLogger.error(this.getClass(),"load GameSwitch faild");
            return;
        }

        int switchVaule = 0;
        List<GameSwitch> switches = gameSwitch.getSwitchList();
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
