package com.mudchobo.game.halligalligame.domain;

import java.util.EmptyStackException;
import java.util.Stack;

import com.google.appengine.api.users.User;

public class HGUser {

	private User user;
	private Boolean isReady;
	private Stack<String> cardList;
	private Boolean isDead;

	public int countCard(){
		return cardList.size();
	}
	public String flipCard() {
		String card = "";
		try
		{
			card = cardList.pop();
		}
		catch (EmptyStackException e)
		{
			isDead = true;
		}
		return card;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Boolean getIsReady() {
		return isReady;
	}
	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}
	public Stack<String> getCardList() {
		return cardList;
	}
	public void setCardList(Stack<String> cardList) {
		this.cardList = cardList;
	}
	public Boolean getIsDead() {
		return isDead;
	}
	public void setIsDead(Boolean isDead) {
		this.isDead = isDead;
	}
	
}
