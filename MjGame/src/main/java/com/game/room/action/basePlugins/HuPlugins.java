package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.log.MJLog;
import com.game.room.MjAutoCacheHandContainer;
import com.game.room.MjTable;
import com.game.room.action.*;
import com.game.room.status.StepGameStatusData;
import com.game.room.util.MJTool;
import com.lsocket.message.Response;

import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/11.
 */
public class HuPlugins<T extends MjTable> extends AbstractActionPlugin<T> implements IPluginCheckCanExecuteAction<T,StepGameStatusData>{
    @Override
    public boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object parems) {
        HuAction.CheckHuType huType = (HuAction.CheckHuType) parems;
        if(card == 0){
            if(!MJTool.isSimpleHu(MJTool.toCardArray(chair.getHandsContainer().getHandCards(),0),((MjAutoCacheHandContainer)chair.getHandsContainer().getAutoCacheHands()).getCardNumMap())){
                return false;
            }
        }else {
            int[] cards = MJTool.toCardArray(chair.getHandsContainer().getHandCards(),1);
            cards[cards.length-1] = card;
            if(!MJTool.isSimpleHu(cards,null)){
                return false;
            }
        }

        SuperGameStatusData gameStatusData= (SuperGameStatusData) chair.getTableVo().getStatusData();
        gameStatusData.addCanDoDatas(chair.getTableVo().getStep(),new StepGameStatusData(HuAction.getInstance(),stepGameStatusData.getUid(),chair.getId(),card,this));
        return true;
    }


    @Override
    public void createCanExecuteAction(T table,StepGameStatusData stepGameStatusData) {
        SuperGameStatusData gameStatusData= table.getStatusData();
        if(gameStatusData.isEmpty()){
            if(!table.isHuAgain()){
                gameStatusData.gameOver(table);
                return;
            }

            gameStatusData.checkMo(table,table.nextUid(stepGameStatusData.getUid()));
        }else {
            if(table.isAutoHu()){
                gameStatusData.getFirst().setAuto(true);
            }
        }
    }

    @Override
    public HuPlugins createNew() {
        return new HuPlugins();
    }

    @Override
    public boolean doOperation(T table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        if (stepGameStatusData.getiOptPlugin().getPlugin().getSubType() != this.getPlugin().getSubType()) {
            return false;
        }
        table.setNextBankerUid(roleId);
        payment(table,stepGameStatusData);
        return true;
    }

    @Override
    public int chickMatch(T table,List<Integer> card, StepGameStatusData stepData) {
        return 1;
    }
}
