package com.game.core;

import com.game.core.config.RoomSetting;
import com.game.core.config.TablePluginManager;
import com.game.core.factory.TableProducer;
import com.game.core.room.BaseTableVo;
import com.lgame.util.PrintTool;
import com.lgame.util.comm.RandomTool;
import com.lgame.util.file.FileTool;
import com.lgame.util.file.PropertiesTool;
import com.lgame.util.load.ResourceServiceImpl;
import com.lgame.util.load.properties.PropertiesHelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/20.
 */
public class TableFactory  implements Runnable{
    private final static TableFactory factory = new TableFactory();

    private Map<Integer,TableProducer> tableFactory = new ConcurrentHashMap<>(10);

    private final int minTableId = 10000000;
    private final int maxTableId = 90000000;
    private final int queMaxCount = 200;
    private LinkedBlockingQueue<Integer> tableIdPool = new LinkedBlockingQueue(queMaxCount);
    private LinkedBlockingQueue<Integer> goodTableIdPool = new LinkedBlockingQueue();
    /** tableId-是否是好号码 */
    private ConcurrentHashMap<Integer,Boolean> curTablePoolMap = new ConcurrentHashMap<>(10);


    private TableFactory() {
        try {
            RoomSetting gen ;
            for(Map.Entry<Integer,RoomSetting> genGame:TablePluginManager.getInstance().getRoomSettings().entrySet()){
                gen  = genGame.getValue();

                PrintTool.info("begin load TableFactory:"+gen.getRoomFactory());

                TableProducer tableProducer = (TableProducer) Class.forName(gen.getRoomFactory()).newInstance();
                tableProducer.setRoomSetting(gen);
                tableFactory.put(gen.getGameId(),tableProducer );
                PrintTool.info("end load TableFactory:"+gen.getRoomFactory()+"  suc!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }


    public static TableFactory getInstance() {
        return factory;
    }

    /**
     * 初始化比较好的数字
     * @param goodNum
     */
    public void initGoodNum(String goodNum){
        if(goodNum == null || goodNum.trim().isEmpty()){
            return;
        }

        try {
            String[] goodNumArray = goodNum.split(",");
            for(int i = 0;i<goodNumArray.length;i++){
                goodTableIdPool.put(Integer.valueOf(goodNumArray[i]));
                curTablePoolMap.put(Integer.valueOf(goodNumArray[i]),true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public <T extends BaseTableVo> T createTable(int ownerId, int gameId){
        TableProducer tableProducer =  tableFactory.get(gameId);
        if(tableProducer == null){
            return null;
        }
        BaseTableVo table = tableProducer.create(0,ownerId);
        return (T)table;
    }

    public void produceNewTableId(BaseTableVo table){
        int tableId = 0;
        try {
            tableId = tableIdPool.take();
        } catch (Exception e) {
            e.printStackTrace();
            tableId = createSpecialTable(e);
        }finally {
        }
        table.setId(tableId);
        TableManager.getInstance().addTable(table);
        System.out.println("===create from pool:"+table.getId());
    }

    private int createSpecialTable(Exception e){
        System.out.println("==>error：房间id创建池为空，创建过慢或者算法出错");
        return RandomTool.getRandom().nextInt(10000)+maxTableId;
    }

    public void produceGoodTableId(BaseTableVo table){
        Integer goodNum = goodTableIdPool.poll();
        if(goodNum == null){
            produceNewTableId(table);
            table.setGoodId(false);
            return;
        }
        table.setId(goodNum);
        table.setGoodId(true);
        TableManager.getInstance().addTable(table);
        System.out.println("===create from pool:"+table.getId());
    }

    @Override
    public void run() {
        try {

            while (true){
                tableIdPool.put(poolFindNewTableId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int poolFindNewTableId(){
        int randoZone = maxTableId - minTableId;

        int newTableId;
        while (true){
            newTableId = RandomTool.getRandom().nextInt(randoZone)+minTableId;
            if(curTablePoolMap.containsKey(newTableId)){
                continue;
            }

            if(TableManager.getInstance().getTable(newTableId) != null){
                continue;
            }

           // System.out.println("===create to pool:"+newTableId);
            return newTableId;
        }
    }
}
