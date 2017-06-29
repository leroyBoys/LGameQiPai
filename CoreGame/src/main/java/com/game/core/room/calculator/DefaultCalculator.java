package com.game.core.room.calculator;

import com.game.core.room.BaseTableVo;
import com.logger.type.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/27.
 */
public abstract class DefaultCalculator<T extends BaseTableVo> implements Calculator{
    protected Logger logger = LoggerFactory.getLogger(LogType.Calculator.getLogName());

    protected T room;
    ///玩家id--统计类型-内容///
    protected Map<Integer,Map<RecordType,Integer>> records = new HashMap<>();//总统计

    protected Map<Integer,Map<RecordType,Integer>> curRecords = new HashMap<>();//当前局的统计

    public DefaultCalculator(T room){
        this.room = room;
    }

    @Override
    public final void clear() {
        curRecords.clear();
        clearCache();
    }

    protected abstract void clearCache();

    @Override
    public void addRecord(int roleId, RecordType recordType, int count) {
        Map<RecordType,Integer> recordTypeIntegerMap = records.get(roleId);
        Integer countNum = null;

        if(recordTypeIntegerMap == null){
            recordTypeIntegerMap = new HashMap<>();
            records.put(roleId,recordTypeIntegerMap);
        }else {
            countNum = recordTypeIntegerMap.get(recordType);
        }

        recordTypeIntegerMap.put(recordType,countNum==null?count:countNum+count);
    }

    @Override
    public void setRecord(int roleId, RecordType recordType, int value) {
        Map<RecordType,Integer> recordTypeIntegerMap = records.get(roleId);
        if(recordTypeIntegerMap == null){
            recordTypeIntegerMap = new HashMap<>();
            records.put(roleId,recordTypeIntegerMap);
        }
        recordTypeIntegerMap.put(recordType,value);
    }

    @Override
    public void addCurRecord(int uid, RecordType recordType, int count) {
        Map<RecordType,Integer> recordTypeIntegerMap = curRecords.get(uid);
        Integer countNum = null;

        if(recordTypeIntegerMap == null){
            recordTypeIntegerMap = new HashMap<>();
            curRecords.put(uid,recordTypeIntegerMap);
        }else {
            countNum = recordTypeIntegerMap.get(recordType);
        }

        recordTypeIntegerMap.put(recordType,countNum==null?count:countNum+count);
    }

    @Override
    public void setCurRecord(int uid, RecordType recordType, int value) {
        Map<RecordType,Integer> recordTypeIntegerMap = curRecords.get(uid);
        if(recordTypeIntegerMap == null){
            recordTypeIntegerMap = new HashMap<>();
            curRecords.put(uid,recordTypeIntegerMap);
        }
        recordTypeIntegerMap.put(recordType,value);
    }

    public T getRoom() {
        return room;
    }

}
