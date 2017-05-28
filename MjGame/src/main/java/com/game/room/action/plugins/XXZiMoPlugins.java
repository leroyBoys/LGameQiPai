package com.game.room.action.plugins;

import com.game.core.constant.GameConst;
import com.game.core.room.calculator.PayDetail;
import com.game.room.MjTable;
import com.game.room.action.basePlugins.ZiMoPlugins;
import com.game.room.status.StepGameStatusData;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/12.
 */
public class XXZiMoPlugins extends ZiMoPlugins {

    protected void pay(MjTable table, StepGameStatusData stepGameStatusData){
        PayDetail pay = payment(table,stepGameStatusData);
        if((table.getType() & GameConst.XXMjType.ZIMO )== GameConst.XXMjType.ZIMO){
            pay.setRate(0);
        }
    }
}
