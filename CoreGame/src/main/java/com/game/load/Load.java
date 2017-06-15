package com.game.load;

import com.game.load.xml.ErrorCode;
import com.lgame.util.load.xml.XmlApi;
import org.apache.mina.core.session.IdleStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.module.core.ResponseCode.initCodeValues;

/**
 * Created by leroy:656515489@qq.com
 * 2017/6/15.
 */
public class Load {

    public static void main(String[] args){
       importCodeConfig();
    }


    public static void importCodeConfig(){
        Map<Integer,String> codeValueDescMap = initCodeValues();
        codeValueDescMap = new TreeMap<>(codeValueDescMap);

        ErrorCode errorCode = new ErrorCode();
        List<ErrorCode> errorCodes = new LinkedList<>();
        errorCode.setErrorCode(errorCodes);
        for(Map.Entry<Integer,String> codeEntry:codeValueDescMap.entrySet()){
            ErrorCode cur = new ErrorCode();
            cur.setCode(codeEntry.getKey());
            cur.setMessage(codeEntry.getValue());
            errorCodes.add(cur);
        }

        XmlApi.save(errorCode,"D://code.xml",true);
    }
}
