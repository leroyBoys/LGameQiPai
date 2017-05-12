import com.lgame.util.comm.StringTool;
import com.mysql.impl.SqlPool;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/2.
 */
public class Test {


    public void go() {
        String url = "redis://@119.254.166.136:6379";
        String[] firstArray = url.split("//");
        String[] secondArray = firstArray[1].split("@");
        int db = 0;
        if(!secondArray[0].trim().isEmpty()){
            db = Integer.valueOf(secondArray[0]);
        }

        String[] threeArray = secondArray[1].split(":");
        String host = threeArray[0];
        int port = Integer.valueOf(threeArray[1].split("/")[0]);

        String password = null;
        if(threeArray[1].split("/").length>1){
            password = StringTool.trimToNull(threeArray[1].split("/")[1]);
        }

        System.out.println("db:"+db+"  ip:"+host+"  port:"+port+"  pwd:"+password);
    }

    @org.junit.Test
    public void thres(){

        go();
    }
}
