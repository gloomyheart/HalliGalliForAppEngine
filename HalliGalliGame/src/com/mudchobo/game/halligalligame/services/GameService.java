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
	
	public void initGameData(int roomNumber)
	{
		
	}
	
	public boolean isFullAtRoom(int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		if (gameData.getUserList().size() <= 4)
		{
			return true;
		}
		return false;
	}
	
	public void addUser(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		gameData.addUser(user);
	}
	
	public void setReady(User user, Boolean isReady, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		gameData.setReady(user, isReady);
	}
}
