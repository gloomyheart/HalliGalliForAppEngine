package com.mudchobo.game.halligalligame.domain;

import java.util.EmptyStackException;
import java.util.Stack;

import com.google.appengine.api.users.User;

public class HGUser {

	private int roomNumber;
	private User user;
	private Boolean isReady = false;
	private Stack<String> cardList;
	private Boolean isDead;
	private String clientId;
	
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
	
	public void addCard(String card) 
	{
		cardList.push(card);
	}
	
	public String getCard()
	{
		return cardList.pop();
	}
	
	public int getCardCount()
	{
		return cardList.size();
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String channelId) {
		this.clientId = channelId;
	}
}
