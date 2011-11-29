package com.mudchobo.game.halligalligame.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.appengine.api.users.User;
import com.mudchobo.game.halligalligame.domain.GameData;

@Service
public class GameService {
	
	private List<GameData> gameDataList;
	public GameService() 
	{
		gameDataList = new ArrayList<GameData>();
		for (int i = 0; i < 200; i++)
		{
			gameDataList.add(new GameData(i));
		}
	}
	
	/**
	 * 방에 4명이 다 찼는지 확인. 다 찼다면 입장못하게 하기 위함.
	 * @param roomNumber
	 * @return
	 */
	public boolean isFullAtRoom(int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		if (gameData.getUserListSize() >= 4)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 유저가 접속 시
	 * @param user
	 * @param roomNumber
	 */
	public void connect(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		gameData.addUser(user);
	}
	
	/**
	 * 유저 접속 끊을 시
	 * @param user
	 * @param roomNumber
	 */
	public void disconnect(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		gameData.removeUser(user);
	}
	
	/**
	 * 유저가 레디요청 시 레디시킴
	 * @param user
	 * @param isReady
	 * @param roomNumber
	 */
	public void setReady(User user, Boolean isReady, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		gameData.setReady(user, isReady);
	}
	
	/**
	 * 게임 시작
	 * @param user
	 * @param roomNumber
	 */
	public String startGame(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		if (gameData.isAllReady())
		{
			return gameData.startGame();
		}
		return "false";
	}
	
	
	/**
	 * 카드리스트 얻기
	 * @return
	 */
	public List<String> getCurrentCardList(int roomNumber) 
	{
		GameData gameData = gameDataList.get(roomNumber);
		return gameData.getOpenedCardList();
	}
	
	/**
	 * 카드 뒤집기
	 * @param user
	 * @param roomNumber
	 */
	public void openCard(User user, int roomNumber)
	{
		
	}
	
	/**
	 * 종치기
	 * @param user
	 * @param roomNumber
	 */
	public void ringBell(User user, int roomNumber)
	{
		
	}
	
	/**
	 * 채팅요청
	 * @param user
	 * @param roomNumber
	 * @param msg 
	 */
	public void chat(User user, int roomNumber, String msg)
	{
		GameData gameData = gameDataList.get(roomNumber);
		gameData.chat(user, msg);
	}
}
