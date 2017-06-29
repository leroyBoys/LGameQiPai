package com.game.room.action.basePlugins;

import com.game.core.room.BaseChairInfo;
import com.game.core.room.calculator.Calculator;
import com.game.room.MjCardPoolEngine;
import com.game.room.MjHandCardsContainer;
import com.game.room.MjTable;
import com.game.room.action.HuAction;
import com.game.room.action.SuperGameStatusData;
import com.game.room.status.StepGameStatusData;
import com.game.room.util.MJTool;
import com.lsocket.message.Response;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/6/2.
 */
public abstract class IPluginHuCheck extends AbstractActionPlugin<MjTable> implements IPluginCheckCanExecuteAction<MjTable,StepGameStatusData> {
    /**
     * 用于重叠胡牌的计算优先级
     * @return
     */
    public abstract int getWeight();

    /**
     *
     * @param stepGameStatusData
     * @param chair
     * @param card
     * @param hands 手牌
     * @return
     */
    @Deprecated
    @Override
    public final boolean checkExecute(StepGameStatusData stepGameStatusData,BaseChairInfo chair, int card, Object hands) {
        return false;
    }

    public boolean checkExecute(HuAction.HuType huType,int[][] cardsType,BaseChairInfo chair){
        return huType == HuAction.HuType.PINGHU;
    }

    /**
     * 默认：如果自动胡牌的话则直接依次计算所有胡牌的分数，否则提示下一个人是否胡牌
     *      如果是能胡多次的话（血流成河），胡牌之后检查下家摸牌，否则，游戏结束
     * @param table
     * @param stepGameStatusData
     */
    @Override
    public void createCanExecuteAction(MjTable table, StepGameStatusData stepGameStatusData) {
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
    public void record(MjTable table,StepGameStatusData stepGameStatusData){
        if(stepGameStatusData.getUid() != stepGameStatusData.getFromId()){//点炮次数
            table.getCalculator().addRecord(stepGameStatusData.getUid(), Calculator.RecordType.dianPaoCount,1);
        }else {
            table.getCalculator().addRecord(stepGameStatusData.getUid(), Calculator.RecordType.ziMoCount,1);
        }
    }

    @Override
    public final boolean doOperation(MjTable table, Response response, int roleId, StepGameStatusData stepGameStatusData) {
        if (stepGameStatusData.getiOptPlugin().getPlugin().getSubType() != this.getPlugin().getSubType()) {
            return false;
        }

        BaseChairInfo info = table.getChairByUid(stepGameStatusData.getUid());
        int card = 0;
        if(stepGameStatusData.getUid() != stepGameStatusData.getFromId()){//点炮
            MjCardPoolEngine poolEngine = table.getCardPool();
            LinkedList<Integer> outCards = (LinkedList<Integer>) poolEngine.getOutPool(poolEngine.getLastPlayUid());
            card = outCards.removeLast();
        }else {
            card = info.getHandsContainer().getHandCards().remove(info.getHandsContainer().getHandCards().size()-1);
        }

        record(table,stepGameStatusData);
        ((MjHandCardsContainer)info.getHandsContainer()).addHu(card);
        createNextBankId(table,roleId);
        payment(table,stepGameStatusData);
        createCanExecuteAction(table,stepGameStatusData);
        return true;
    }

    /**
     * 下一个庄家id修改
     * @param table
     * @param roleId
     */
    protected void createNextBankId(MjTable table,int roleId){
        table.setNextBankerUid(roleId);
    }

    @Override
    public final int chickMatch(MjTable table, List<Integer> card, StepGameStatusData stepData) {
        return 1;
    }
}
