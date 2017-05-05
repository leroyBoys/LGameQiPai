package com.game.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/4.
 */
public class MethodCacheTime {
    private long TraceTime = 0l;
    public MethodCacheTime() {
        this.TraceTime = System.currentTimeMillis();
    }
    private static Map<String, DbTraceData> DbTrace = new ConcurrentHashMap();

    public static Map<String, DbTraceData> getDbTrace() {
        return DbTrace;
    }

    public static void setDbTrace(Map<String, DbTraceData> DbTrace) {
        MethodCacheTime.DbTrace = DbTrace;
    }

    public void dbTrace(String name) {
        long t = System.currentTimeMillis();
        if (!(DbTrace.containsKey(name))) {
            DbTrace.put(name, new DbTraceData(name));
        }
        DbTrace.get(name).trace(t - this.TraceTime);
    }
}
