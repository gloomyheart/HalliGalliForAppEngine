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
			gameDataList.add(new GameData());
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
		if (gameData.getUserListSize() <= 4)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 유저가 접속 시 유저를 추가시킴
	 * @param user
	 * @param roomNumber
	 */
	public void addUser(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		gameData.addUser(user);
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
}
