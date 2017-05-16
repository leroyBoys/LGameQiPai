import com.lgame.util.comm.StringTool;
import com.module.core.Comment;
import com.module.core.ResponseCode;
import com.mysql.impl.SqlPool;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/2.
 */
public class Test {


    public void go() {

        for (Field field : ResponseCode.Error.free_now.getClass().getFields()) {
            System.out.println("------>"+field.getName());
            Comment tip = field.getAnnotation(Comment.class);
        }

    }

    @org.junit.Test
    public void thres(){

        go();
    }
}
