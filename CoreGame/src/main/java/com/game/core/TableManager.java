package com.game.core;

import com.game.core.room.BaseTableVo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/19.
 */
public class TableManager implements Runnable,CheckOutStatus {
    private final static TableManager tableManager = new TableManager();
    private Map<Integer,BaseTableVo> tableMap = new ConcurrentHashMap<>(10);

    /**  table的守护线程检测  */
    private ConcurrentHashMap<Integer,Integer> tasks = new ConcurrentHashMap<>();
    private LinkedBlockingQueue<Integer> taskQeque = new LinkedBlockingQueue(5000);

    public static TableManager getInstance(){
        return tableManager;
    }

    private TableManager(){
        new Thread(this).start();
    }

    public   <T extends BaseTableVo> void addTable(T t){
        tableMap.put(t.getId(),t);
    }

    public void destroyTable(int tableId){
        tableMap.remove(tableId);
    }

    public <T extends BaseTableVo> T getTable(int tableId){
        return (T) tableMap.get(tableId);
    }

    /**
     *  检测守护线程
     * @param tableId
     */
    public void trigger(int tableId){
        System.out.println("===trigger=>"+tableId);
        if(tasks.putIfAbsent(tableId,1) != null){
            return;
        }

        if(!tableMap.containsKey(tableId)){
            tasks.remove(tableId);
            return;
        }
        System.out.println("===trigger succ=>"+tableId);
        try {
            taskQeque.put(tableId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {

            BaseTableVo baseTableVo;
            while (true){
                int tableId = taskQeque.take();
                baseTableVo = tableMap.get(tableId);
                if(baseTableVo != null){
                    baseTableVo.trigger();
                }

                tasks.remove(tableId);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
       System.out.println("tasks:"+tasks.size()+" taskQeque:"+taskQeque.size()+"  tableMap:"+tableMap.size());
    }
}
