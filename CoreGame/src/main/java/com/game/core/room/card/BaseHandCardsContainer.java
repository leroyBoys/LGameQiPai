package com.game.core.room.card;

import java.util.ArrayList;
import java.util.List;

/***
 * 手牌容器的通用接口,积分，计算条件等都可以在这里陈列
 * 
 * @author Administrator
 * 
 */
public abstract class BaseHandCardsContainer {
	private List<Integer> hands = new ArrayList<>();

	/** 取得手牌 */
	public List<Integer> getHandCards(){
		return hands;
	}

	/** 设置手牌 */
	public void setHandCards(List<Integer> list){
		hands = list;
	}

	/** 添加一张手牌*/
	public void addHandCards(int card){
		hands.add(card);
	}

	/** 添加多张手牌 */
	public void addHandCards(List<Integer> list){
		hands.addAll(list);
	}

	/** 清空手里的牌 */
	public void cleanHands(){
		hands.clear();
	}
}
