package com.game.room.action.basePlugins;

import com.game.core.config.AbstractStagePlugin;
import com.game.core.config.IOptPlugin;
import com.game.room.MjChairInfo;
import com.game.room.MjTable;
import com.lsocket.message.Response;
import com.module.net.NetGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/24.
 */
public class FaPaiPlugins<T extends MjTable> extends AbstractStagePlugin<T> {
    @Override
    public IOptPlugin createNew() {
        return new FaPaiPlugins();
    }

    @Override
    public Object doOperation(T table, Response response,NetGame.NetOprateData oprateData) {
        table.getCardPool().shuffle();

        List<Integer> cardPool = table.getCardPool().getRemainCards();
        final int initHands = table.getTableProducer().getGen().getInitHandCardCount();

        for(int i = 0;i<table.getChairs().length;i++) {
            if (table.getChairs()[i] == null) {
                continue;
            }

            MjChairInfo p = table.getChairs()[i];
            final int curinitHands = initHands + (table.getBankId() == p.getId()?1:0);
            if (cardPool.size() < curinitHands) {
                return false;
            }
            ArrayList cards = new ArrayList<>();
            for (int j = 0; j < curinitHands; j++) {
                cards.add(cardPool.remove(0));
            }
            p.getHandsContainer().setHandCards(cards);
        }

        table.getStatusData().setOver(true);
        return true;
    }

}
