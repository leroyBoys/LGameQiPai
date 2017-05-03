package com.game.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/21.
 */
public class TablePluginManager {
    private static TablePluginManager ourInstance = new TablePluginManager();

    public static TablePluginManager getInstance() {
        return ourInstance;
    }

    /**  游戏id-插件类型-插件集合 */
    private final Map<Integer,Map<Integer,ArrayList<IOptPlugin>>> optPluginMap = new HashMap<>();

    private TablePluginManager() {
    }

    public ArrayList<IOptPlugin> getOptPlugin(int gameId,int actionType){
        return optPluginMap.get(gameId).get(actionType);
    }

    public IOptPlugin getOneOptPlugin(int gameId,int actionType){
        return optPluginMap.get(gameId).get(actionType).get(0);
    }

    public IOptPlugin createOptPlugin(int pluginId) {
        if(pluginId == -1){
            return null;
        }

        String classpath = "";
        try {
            Class clazz = Class.forName(PluginsPath.getPluginsPath(pluginId) + "."
                    + classpath);
            return  (IOptPlugin) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public enum PluginsPath{
        mj(1,""),
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
