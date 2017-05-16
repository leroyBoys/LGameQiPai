package com.game.core.config;

import com.game.core.TableManager;
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

        List<PluginGen> tempList2 = (List<PluginGen>) resourceService.listAll(PluginGen.class);
        for(PluginGen sett:tempList2){
            if(!roomSettingMap.containsKey(sett.getGameId())){
                continue;
            }

            pluginGenMap.put(sett.getTempId(),sett);

            Map<Integer,ArrayList<IOptPlugin>> tempMap = optPluginMap.get(sett.getGameId());
            if(tempMap == null){
                tempMap = new HashMap<>();
                optPluginMap.put(sett.getGameId(),tempMap);
            }

            String[] actions = sett.getActionType().split(StringTool.SIGN4);
            for(String at:actions){
                int actionType = Integer.valueOf(StringTool.isNotNull(at)?at:"0");
                ArrayList<IOptPlugin> optPlugins = tempMap.get(actionType);
                if(optPlugins == null){
                    optPlugins = new ArrayList<>();
                    tempMap.put(actionType,optPlugins);
                }
                optPlugins.add(createOptPlugin(sett));
            }

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
            Class clazz = Class.forName(PluginsPath.getPluginsPath(pg.getGameId()) + "."
                    + pg.getPluginClass());
            IOptPlugin optPlugin =  (IOptPlugin) clazz.newInstance();
            optPlugin.setPluginId(pg.getTempId());
            return optPlugin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public enum PluginsPath{
        mj(1,"com.game.room.action.plugins"),
        ddz(2,"");
        private final int id;
        private  final String path;
        PluginsPath(int id,String path){
            this.id = id;
            this.path = path;
        }

        public static PluginsPath getPluginsPath(int id){
            for(PluginsPath pluginsPath:PluginsPath.values()){
                if(id == pluginsPath.id){
                    return pluginsPath;
                }
            }

            return null;
        }

        public String getPath() {
            return path;
        }
    }
}
