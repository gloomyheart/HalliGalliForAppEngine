package com.mudchobo.game.halligalligame.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.appengine.api.users.User;
import com.mudchobo.game.halligalligame.domain.GameData;
import com.mudchobo.game.halligalligame.domain.HGUser;

@Service
public class GameService {
	
	/**
	 * 게임데이터 목록
	 */
	private List<GameData> gameDataList;
	
	/**
	 * 현재 사용자 접속 목록 
	 */
	private Map<String, HGUser> userList;
	
	public GameService() 
	{
		userList = new HashMap<String, HGUser>();
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
	 * 유저가 해당방에 접속 시
	 * @param user
	 * @param roomNumber
	 */
	public boolean connect(User user, int roomNumber, String clientId)
	{
		HGUser hgUser = userList.get(clientId);
		if (hgUser == null)
		{
			return false;
		}
		// 사용자접속 목록에 추가
		hgUser = new HGUser();
		hgUser.setUser(user);
		hgUser.setRoomNumber(roomNumber);
		userList.put(clientId, hgUser);
		
		// 해당방번호에 유저추가
		GameData gameData = gameDataList.get(roomNumber);
		gameData.addUser(user);
		
		return true;
	}
	
	/**
	 * 유저가 접속 끊을 시
	 * @param user
	 * @param roomNumber
	 */
	public void disconnect(String clientId)
	{
		// 사용자접속 목록에서 삭제
		HGUser hgUser = userList.get(clientId);
		int roomNumber = hgUser.getRoomNumber();
		User user = hgUser.getUser();
		
		// 해당방에 유저삭제
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
