package com.mudchobo.game.halligalligame.domain;

import java.util.EmptyStackException;
import java.util.Stack;

public class HGUser {
	private String userId;
	private String nickName;
	private String email;
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
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
