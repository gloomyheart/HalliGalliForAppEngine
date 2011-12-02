package com.mudchobo.game.halligalligame.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.org.json.JSONException;
import com.mudchobo.game.halligalligame.domain.GameData;
import com.mudchobo.game.halligalligame.domain.HGUser;

/**
 * @author mudchobo
 *
 */
@Service
public class GameService {
	
	/**
	 * ���ӵ����� ���
	 */
	private List<GameData> gameDataList;
	
	/**
	 * ���� ����� Ŭ���̾�Ʈ ���
	 */
	Map<String, String> clientList;
	
	/**
	 * ���ӵ� �������
	 */
	Map<String, HGUser> userList;
	
	public GameService() 
	{
		clientList = new HashMap<String, String>();
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
	public boolean connect(String clientId)
	{
		// ������Ͽ� �ִ��� Ȯ�� �� ������ false(�ߺ����� ����)
		HGUser hgUser = (HGUser) userList.get(clientId);
		if (hgUser != null)
		{
			return false;
		}
		// ��������� ��Ͽ� �߰�
		hgUser = new HGUser();
		hgUser.setClientId(clientId);
		userList.put(clientId, hgUser);
		
		return true;
	}
	
	/**
	 * channel���� �� ������ ��û ��
	 * @param roomNumber
	 * @param user
	 * @param clientId
	 * @return
	 */
	public boolean enterRoom(int roomNumber, User user, String clientId)
	{
		HGUser hgUser = (HGUser) userList.get(clientId);
		if (hgUser == null)
		{
			return false;
		}
		hgUser.setRoomNumber(roomNumber);
		hgUser.setUser(user);
		
		// �ش���ȣ�� �����߰�
		GameData gameData = gameDataList.get(roomNumber);
		try {
			gameData.addUser(user);
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
		HGUser hgUser = (HGUser) userList.get(clientId);
		int roomNumber = hgUser.getRoomNumber();
		User user = hgUser.getUser();
		
		// �ش�濡 ��������
		GameData gameData = gameDataList.get(roomNumber);
		try {
			gameData.removeUser(user);
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
		try {
			gameData.setReady(user, isReady);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���� ����
	 * @param user
	 * @param roomNumber
	 */
	public void startGame(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		try {
			gameData.startGame();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ī�� ������
	 * @param user
	 * @param roomNumber
	 */
	public void openCard(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		try {
			gameData.openCard(user);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ġ��
	 * @param user
	 * @param roomNumber
	 */
	public void ringBell(User user, int roomNumber)
	{
		GameData gameData = gameDataList.get(roomNumber);
		try {
			gameData.ringBell(user);
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
