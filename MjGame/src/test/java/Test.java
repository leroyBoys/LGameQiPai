import com.game.room.util.MJTool;
import com.lgame.util.json.JsonTool;
import com.lgame.util.json.JsonUtil;
import com.logger.type.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.module.EffectData.Type.num;

/**
 * Created by Administrator on 2017/4/2.
 */
public class Test {


    public void go() {
        List<Integer> list = new ArrayList<>();
        list.add(1);;
        list.add(2);;
        list.add(3);
        list.add(1);

        int num = 1;
        int card = 15;
        int[] cardCounts = new int[]{0, 8, 4, 0, 0};

        Map<String,String> cardNumMap = (Map<String, String>) JsonUtil.getBeanFromJson("{\"34\":\"1\",\"16\":\"2\",\"32\":\"1\",\"18\":\"1\",\"36\":\"2\",\"37\":\"1\",\"25\":\"1\",\"43\":\"1\",\"41\":\"1\",\"12\":\"1\",\"14\":\"1\",\"31\":\"2\",\"15\":\"2\"}",Map.class);

        String curNumStr = cardNumMap.get("15");
        System.out.println("curs:"+curNumStr);
        if(curNumStr == null){
            System.out.println("==empty");
            return;
        }

        int curNum = Integer.valueOf(curNumStr);
        cardCounts[curNum]--;
        curNum = curNum-num;
        if(curNum > 0){
            cardCounts[curNum]++;
            cardNumMap.put(card+"",""+curNum);
        }else {
            cardNumMap.remove(card);
        }

        System.out.println(Arrays.toString(cardCounts));

        System.out.println(JsonTool.getJsonFromBean(cardNumMap));
    }




    @org.junit.Test
    public void thres(){
        go();
    }

}
