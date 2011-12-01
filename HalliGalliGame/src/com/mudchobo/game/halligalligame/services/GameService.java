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
	 * ���ӵ����� ���
	 */
	private List<GameData> gameDataList;
	
	/**
	 * ���� ����� ���� ��� 
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
	 * �濡 4���� �� á���� Ȯ��. �� á�ٸ� ������ϰ� �ϱ� ����.
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
	 * ������ �ش�濡 ���� ��
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
		// ��������� ��Ͽ� �߰�
		hgUser = new HGUser();
		hgUser.setUser(user);
		hgUser.setRoomNumber(roomNumber);
		userList.put(clientId, hgUser);
		
		// �ش���ȣ�� �����߰�
		GameData gameData = gameDataList.get(roomNumber);
		gameData.addUser(user);
		
		return true;
	}
	
	/**
	 * ������ ���� ���� ��
	 * @param user
	 * @param roomNumber
	 */
	public void disconnect(String clientId)
	{
		// ��������� ��Ͽ��� ����
		HGUser hgUser = userList.get(clientId);
		int roomNumber = hgUser.getRoomNumber();
		User user = hgUser.getUser();
		
		// �ش�濡 ��������
		GameData gameData = gameDataList.get(roomNumber);
		gameData.removeUser(user);
	}
	
	/**
	 * ������ �����û �� �����Ŵ
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
	 * ���� ����
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
	 * ī�帮��Ʈ ���
	 * @return
	 */
	public List<String> getCurrentCardList(int roomNumber) 
	{
		GameData gameData = gameDataList.get(roomNumber);
		return gameData.getOpenedCardList();
	}
	
	/**
	 * ī�� ������
	 * @param user
	 * @param roomNumber
	 */
	public void openCard(User user, int roomNumber)
	{
		
	}
	
	/**
	 * ��ġ��
	 * @param user
	 * @param roomNumber
	 */
	public void ringBell(User user, int roomNumber)
	{
		
	}
	
	/**
	 * ä�ÿ�û
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
