package com.mudchobo.game.halligalligame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class GameData 
{
	private String prefix = "Channel";

	private int roomNumber = 0;
	private String[] cardPrefixArray = {"B", "L", "P", "S"};
	private List<HGUser> userList;
	private Stack<String> cardList;
	private List<Stack<String>> openedCardList;
	private int nowPlayer = 0;
	private boolean isStart = false;
	public GameData(int roomNumber) 
	{
		this.roomNumber = roomNumber;
		userList = new ArrayList<HGUser>();
	}

	/**
	 * ���� ����
	 */
	public void startGame(User user) throws JSONException
	{
		JSONObject jsonObject = new JSONObject();
		
		// ��δ� �����ߴ��� Ȯ��
		if (!isAllReady())
		{
			// ��� �������� ����.
			jsonObject.put("result", "error");
			jsonObject.put("msg", "��� �غ� ���� �ʾҽ��ϴ�.");
			sendMessage(user, jsonObject.toString());
			return;
		}
		// ���ۻ���ڴ� 0
		nowPlayer = 0;
		cardList = new Stack<String>();
		openedCardList = new ArrayList<Stack<String>>();
		for (int i = 0; i < userList.size(); i++)
		{
			openedCardList.add(new Stack<String>());
		}
		
		// ī����� �� �ʱ�ȭ
		String[] tempCardList = new String[56];
		for (int i = 0; i < 56; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				for (int one = 0; one < 5; one++)
				{
					tempCardList[i] = cardPrefixArray[j] + 1;i++;
				}
				for (int two = 0; two < 3; two++)
				{
					tempCardList[i] = cardPrefixArray[j] + 2;i++;
				}
				for (int three = 0; three < 3; three++)
				{
					tempCardList[i] = cardPrefixArray[j] + 3;i++;
				}
				tempCardList[i] = cardPrefixArray[j] + 4;i++;
				tempCardList[i] = cardPrefixArray[j] + 4;i++;
				tempCardList[i] = cardPrefixArray[j] + 5;i++;
			}
		}
		
		// ī�弯��
		Random random = new Random();
		for (int i = 55; i > 0; i--)
		{
			String temp; int j = random.nextInt(56);
			temp = tempCardList[i];
			tempCardList[i] = tempCardList[j];
			tempCardList[j] = temp;
		}
		// ī�彺�ÿ� �ֱ�
		for (int i = 0; i < 56; i++)
		{
			cardList.push(tempCardList[i]);
		}
		
		int userListSize = getUserListSize();
		// Ŭ���̾�Ʈ���� ī�峪���ֱ�
		for (int i = 0; i < userListSize; i++)
		{
			HGUser hgUser = userList.get(i);
			Stack<String> stack = new Stack<String>();
			for (int j = 0; j < (56 / userListSize); j++)
			{
				stack.push(cardList.pop());
			}
			hgUser.setCardList(stack);
			hgUser.setIsDead(false);
		}
		isStart = true;
		
		jsonObject.put("result", "start");
		jsonObject.put("msg", "���ӽ���!");
		sendToAll(jsonObject.toString());
	}

	public void stopGame() throws JSONException
	{
		// �����ߴ�
		isStart = false;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "stopGame");
		sendToAll(jsonObject.toString());
		sendUserList();
		for (int i = 0; i < userList.size(); i++)
		{
			userList.get(i).setIsReady(false);
		}
	}
	
	public void openCard(User user) throws JSONException
	{
		if (!isStart){
			return;
		}
		HGUser hgUser = userList.get(nowPlayer);
		if (hgUser.getUser().getUserId().equals(user.getUserId()))
		{
			String card = hgUser.flipCard();
			// �ش� ������� ī�尡 ���� ���� ��� ����ó��
			if (hgUser.getCardCount() == 0)
			{
				hgUser.setIsDead(true);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("result", "dead");
				jsonObject.put("userId", hgUser.getUser().getUserId());
				sendToAll(jsonObject.toString());
			}
			
			openedCardList.get(nowPlayer).push(card);
			nowPlayer++;
			if (userList.size() <= nowPlayer)
			{
				nowPlayer = 0;
			}
			sendOpenedCardList();
		}
		else
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "error");
			jsonObject.put("msg", "��� ������ �ƴմϴ�.");
			sendMessage(user, jsonObject.toString());
		}
		
		// �̰���� üũ
		checkWin();
	}
	
	public void ringBell(User user) throws JSONException
	{
		if (!isStart){
			return;
		}
		// ���� 5�� �´��� Ȯ��.
		int totalCount = 0;
		for (int i = 0; i < openedCardList.size(); i++)
		{
			Stack<String> stack = openedCardList.get(i);
			String openedCard = "";
			if (stack.size() != 0)
			{
				openedCard = stack.get(stack.size() - 1);
				totalCount += Integer.parseInt(openedCard.substring(1));
			}
		}
		
		// �ش����� ã��
		HGUser hgUser = new HGUser();
		for (int i = 0; i < userList.size(); i++)
		{
			HGUser tempHGUser = userList.get(i); 
			if (user.getUserId().equals(tempHGUser.getUser().getUserId()))
			hgUser = tempHGUser;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "ringBell");
		// ���� 5�̸� ����. ���µ� ī�带 �ڽ��� �� ������.
		if (totalCount == 5)
		{
			for (int i = 0; i < openedCardList.size(); i++)
			{
				Stack<String> stack = openedCardList.get(i);
				for (; stack.size() != 0;)
				{
					hgUser.addCard(stack.pop());
				}
			}
			jsonObject.put("msg", user.getNickname() + "���� ��ġ�⿡ �����߽��ϴ�.");
		}
		// �� �߸�ħ�� �˸���, �ڽ��� ī�带 ����ڵ鿡�� ���徿 �����ش�.
		else
		{
			for (int i = 0; i < userList.size(); i++)
			{
				HGUser anotherHGUser = userList.get(i);
				if (!user.getUserId().equals(anotherHGUser.getUser().getUserId()))
				{
					anotherHGUser.addCard(hgUser.getCard());
				}
			}
			jsonObject.put("msg", user.getNickname() + "���� ��ġ�⿡ �����߽��ϴ�.");
		}
		sendToAll(jsonObject.toString());
		
		sendOpenedCardList();
		// �̰���� üũ
		checkWin();
	}
	
	public void addUser(User user) throws JSONException 
	{
		// ���� ������ �ִ��� Ȯ�� �� ������ ����ó��
		for (int i = 0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (hgUser.getUser().getUserId().equals(user.getUserId()))
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error", "exist");
				sendToAll(jsonObject.toString());
				return;
			}
		}
		HGUser hgUser = new HGUser();
		hgUser.setUser(user);
		userList.add(hgUser);
		// ������� ������
		sendUserList();
	}

	public void removeUser(User user) throws JSONException
	{
		for (int i = 0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (user.getUserId().equals(hgUser.getUser().getUserId()))
			{
				userList.remove(i);
				break;
			}
		}
		// �������̸� �����ߴ�
		stopGame();
		
		// ������� ������
		sendUserList();
	}
	
	public boolean isAllReady()
	{
		if (userList.size() <= 1)
		{
			return false;
		}
		for (int i = 1; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (!hgUser.getIsReady())
			{
				return false;
			}
		}
		return true;
	}

	private boolean checkWin() throws JSONException 
	{
		int deadCount = 0;
		HGUser winHGUser = null;
		for (int i = 0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (!hgUser.getIsDead())
			{
				winHGUser = hgUser;
			}
			else
			{
				deadCount++;
			}
		}
		if (deadCount == userList.size() - 1)
		{
			// �ش� ����ڰ� �̱�
			isStart = false;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "win");
			jsonObject.put("winner", winHGUser.getUser().getNickname() + "���� �̰���ϴ�.");
			sendToAll(jsonObject.toString());
			sendUserList();
			return true;
		}
		return false;
	}
	
	public void setReady(User user, Boolean isReady) throws JSONException
	{
		System.out.println("setReady = " + user.getUserId() + " " + isReady);
		for (int i =0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (user.getUserId().equals(hgUser.getUser().getUserId()))
			{
				System.out.println("isReady = " + isReady);
				hgUser.setIsReady(isReady);
				break;
			}
		}
		sendUserList();
	}
	
	public int getUserListSize()
	{
		return userList.size();
	}
	
	public boolean isStart()
	{
		return isStart;
	}

	public void sendOpenedCardList() throws JSONException
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "openCard");
		jsonObject.put("next", userList.get(nowPlayer).getUser().getNickname());
		JSONArray jsonArray = new JSONArray();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < userList.size(); i++)
		{
			Stack<String> stack = openedCardList.get(i);
			String openedCard = "";
			if (stack.size() != 0)
			{
				openedCard = stack.get(stack.size() - 1);
			}
			map.put("openedCard", openedCard);
			map.put("count", userList.get(i).getCardCount());
			jsonArray.put(map);
		}
		jsonObject.put("openedCardList", jsonArray);
		sendToAll(jsonObject.toString());
	}
	
	/**
	 * ������� ������
	 */
	public void sendUserList() throws JSONException
	{
		// ������Ϻ�����
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < userList.size(); i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			boolean isKing = false;
			if (i == 0){
				isKing = true;
			}
			HGUser hgUser = userList.get(i);
			map.put("isKing", isKing);
			map.put("isReady", hgUser.getIsReady());
			map.put("nickName", hgUser.getUser().getNickname());
			map.put("userId", hgUser.getUser().getUserId());
			jsonArray.put(map);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "userList");
		jsonObject.put("data", jsonArray);
		sendToAll(jsonObject.toString());
	}

	/**
	 * ä�ø޼��� ����
	 * @param user
	 * @param msg
	 */
	public void chat(User user, String msg) 
	{
		JSONObject jsonObject = new JSONObject();
		try 
		{
			jsonObject.put("result", "chat");
			jsonObject.put("msg", user.getNickname() + ":" + msg);
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		sendToAll(jsonObject.toString());
	}
	
	public void sendToAll(String msg)
	{
		for (int i = 0; i < userList.size(); i++)
		{
			User user = userList.get(i).getUser();
			sendMessage(user, msg);
		}
	}
	
	public void sendWithoutMe(HGUser hgUser, String msg)
	{
		for (int i = 0; i < userList.size(); i++)
		{
			User user = userList.get(i).getUser();
			if (user.getUserId().equals(hgUser.getUser().getUserId()))
			{
				continue;
			}
			sendMessage(user, msg);
		}
	}
	
	public void sendMessage(User user, String msg)
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(prefix + roomNumber + user.getUserId(), msg));
	}
}