package com.game.room.status;

import com.game.core.constant.GameConst;
import com.game.core.room.BaseGameStateData;
import com.game.core.room.BaseTableVo;
import com.game.room.MjChairInfo;
import com.module.net.NetGame;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/27.
 */
public class XXYaPaoStatusData extends BaseGameStateData{

    @Override
    public NetGame.NetOprateData.Builder getStatusDetail(BaseTableVo tableVo) {
        NetGame.NetOprateData.Builder yaPao = NetGame.NetOprateData.newBuilder();
        yaPao.setOtype(GameConst.MJ.ACTION_TYPE_YAPao);
        for(int i = 0;i<tableVo.getChairs().length;i++){
            if(tableVo.getChairs()[i] == null || !contains(tableVo.getChairs()[i].getId())){
                continue;
            }
            NetGame.NetKvData.Builder netKvData = NetGame.NetKvData.newBuilder();
            netKvData.setK(tableVo.getChairs()[i].getId());
            netKvData.setV(((MjChairInfo)tableVo.getChairs()[i]).getYapaoNum());
            yaPao.addKvDatas(netKvData);
        }
        return yaPao;
    }
}
