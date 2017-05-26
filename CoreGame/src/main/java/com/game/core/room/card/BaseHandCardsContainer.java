package com.game.core.room.card;

import java.util.ArrayList;
import java.util.List;

/***
 * 手牌容器的通用接口
 * 
 * @author Administrator
 * 
 */
public abstract class BaseHandCardsContainer {
	private List<Integer> hands = new ArrayList<>();

	protected abstract AutoCacheHandContainer getAutoCache();

	public final AutoCacheHandContainer getAutoCacheHands(){
		AutoCacheHandContainer autoCacheHandContainer = getAutoCache();
		if(autoCacheHandContainer != null){
			autoCacheHandContainer.check(hands);
		}

		return autoCacheHandContainer;
	}

	/** 取得手牌 */
	public List<Integer> getHandCards(){
		return hands;
	}

	/** 设置手牌 */
	public void setHandCards(List<Integer> list){
		hands = list;
		AutoCacheHandContainer autoCacheHandContainer = getAutoCache();
		if(autoCacheHandContainer != null){
			autoCacheHandContainer.clear();
			autoCacheHandContainer.reLoad(list);
		}
	}

	/** 添加一张手牌*/
	public void addHandCards(int card){
		hands.add(card);
		AutoCacheHandContainer autoCacheHandContainer = getAutoCache();
		if(autoCacheHandContainer != null){
			autoCacheHandContainer.addCard(card);
		}
	}

	/** 添加多张手牌 */
	public void addHandCards(List<Integer> list){
		hands.addAll(list);

		AutoCacheHandContainer autoCacheHandContainer = getAutoCache();
		if(autoCacheHandContainer != null){
			autoCacheHandContainer.addCard(list);
		}
	}

	/** 清空手里的牌 */
	public void cleanHands(){
		hands.clear();
	}
}
