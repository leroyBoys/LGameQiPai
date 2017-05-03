package com.game.core.room;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public interface SuperCreateNew {

    public SuperCreateNew createNew();

    public static final class CreateNewCache{
        private final static ConcurrentHashMap<String,SuperCreateNew> caches = new ConcurrentHashMap<>();

        public static  <T extends SuperCreateNew> T create(Class<T> c){
            try {
                T t = (T) caches.get(c.getName());
                if(t != null){
                    return t;
                }

                caches.putIfAbsent(c.getName(),c.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T) caches.get(c.getName());
        }


    }
}
