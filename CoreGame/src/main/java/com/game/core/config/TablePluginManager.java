package com.game.core.config;

import com.lgame.util.comm.StringTool;
import com.lgame.util.file.PropertiesTool;
import com.lgame.util.load.ResourceServiceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/21.
 */
public class TablePluginManager {
    private final String PLUGINPATH="com.game.room.action";
    private ResourceServiceImpl resourceService = null;
    private static TablePluginManager ourInstance = new TablePluginManager();

    public static TablePluginManager getInstance() {
        return ourInstance;
    }

    /**  游戏id-插件类型-插件集合 */
    private final Map<Integer,Map<Integer,ArrayList<IOptPlugin>>> optPluginMap = new HashMap<>();

    private final Map<Integer,PluginGen> pluginGenMap = new HashMap<>();

    private final Map<Integer,RoomSetting> roomSettingMap = new HashMap<>();
    private TablePluginManager() {
    }

    public void refresh(String roomSetName,String pluginsName){
        String path = PropertiesTool.getPropertiesPath(roomSetName);
        if(path == null){
            return;
        }

        if(resourceService == null){
            resourceService = ResourceServiceImpl.getInstance(new File(path).getParent());
        }

        List<RoomSetting> tempList = (List<RoomSetting>) resourceService.listAll(RoomSetting.class);
        for(RoomSetting sett:tempList){
            roomSettingMap.put(sett.getGameId(),sett);
        }

        List<PluginGen> pluginGens = (List<PluginGen>) resourceService.listAll(PluginGen.class);
        for(PluginGen sett:pluginGens){
            pluginGenMap.put(sett.getTempId(),sett);

            IOptPlugin optPlugin = createOptPlugin(sett);
            String[] gameIds = sett.getGameId().split(",");

            for(String gameIdStr:gameIds){
                Integer gameId = Integer.valueOf(gameIdStr);
                if(!roomSettingMap.containsKey(gameId)){
                    continue;
                }
                this.addOptPluginMap(optPluginMap,gameId,optPlugin);
            }

        }

        System.out.println("load suc!"+pluginGenMap.size());
    }

    private void addOptPluginMap(Map<Integer,Map<Integer,ArrayList<IOptPlugin>>> targetMap,Integer gameId, IOptPlugin optPlugin) {
        Map<Integer,ArrayList<IOptPlugin>> tempMap = targetMap.get(gameId);
        if(tempMap == null){
            tempMap = new HashMap<>();
            targetMap.put(gameId,tempMap);
        }

        String[] actions = optPlugin.getPlugin().getActionType().split(StringTool.SIGN4);
        for(String at:actions){
            int actionType = Integer.valueOf(StringTool.isNotNull(at)?at:"0");
            ArrayList<IOptPlugin> optPlugins = tempMap.get(actionType);
            if(optPlugins == null){
                optPlugins = new ArrayList<>();
                tempMap.put(actionType,optPlugins);
            }
            optPlugins.add(optPlugin);
        }
    }

    public ArrayList<IOptPlugin> getOptPlugin(int gameId,int actionType){
        return optPluginMap.get(gameId).get(actionType);
    }

    public IOptPlugin getOneOptPlugin(int gameId,int actionType){
        return optPluginMap.get(gameId).get(actionType).get(0);
    }

    public RoomSetting getRoomSetting(int gameId){
        return roomSettingMap.get(gameId);
    }

    public Map<Integer,RoomSetting> getRoomSettings(){
        return roomSettingMap;
    }

    public IOptPlugin createOptPlugin(PluginGen pg) {
        if(pg == null){
            return null;
        }

        try {
            Class clazz = Class.forName(PLUGINPATH + "." + pg.getPluginClass());
            IOptPlugin optPlugin =  (IOptPlugin) clazz.newInstance();
            optPlugin.setPlugin(pg);
            return optPlugin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
