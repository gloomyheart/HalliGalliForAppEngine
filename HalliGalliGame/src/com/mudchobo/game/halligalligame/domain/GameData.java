package com.mudchobo.game.halligalligame.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.mudchobo.game.halligalligame.controllers.TestController;

public class GameData 
{
	private String prefix = "Channel";
	private static final Logger log = Logger.getLogger(TestController.class.getName());

	private int roomNumber = 0;
	private String[] cardPrefixArray = {"B", "L", "P", "S"};
	private List<HGUser> userList;
	private Stack<String> cardList;
	private int nowPlayer = 0;
	private boolean isStart = false;
	public GameData(int roomNumber) 
	{
		this.roomNumber = roomNumber;
		userList = new ArrayList<HGUser>();
		cardList = new Stack<String>();
	}

	/**
	 * ���� ����
	 * @return start:����, notPeople:����̺����Ѱ�� 
	 */
	public void startGame()
	{
		// ��δ� �����ߴ��� Ȯ��
		if (!isAllReady())
		{
			// ��� �������� ����.
			JSONObject jsonObject = new JSONObject();
			try 
			{
				jsonObject.put("result", "error");
				jsonObject.put("msg", "��� �غ� ���� �ʾҽ��ϴ�.");
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			sendToAll(jsonObject.toString());
			return;
		}
		// ���ۻ���ڴ� 0
		nowPlayer = 0;
		
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
			Stack<String> stack = new Stack<String>();
			for (int j = 0; j < (56 / userListSize); j++)
			{
				stack.push(cardList.pop());
			}
			userList.get(i).setCardList(stack);
		}
		isStart = true;
		
		JSONObject jsonObject = new JSONObject();
		try 
		{
			jsonObject.put("result", "start");
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		sendToAll(jsonObject.toString());
	}

	public void stopGame()
	{
		// TODO �����ߴ�
		isStart = false;
	}
	
	public String flipCard(User user)
	{
		HGUser hgUser = userList.get(nowPlayer);
		if (hgUser.getUser().getUserId() == user.getUserId())
		{
			String card = hgUser.flipCard();
			if (card == "")
			{
				return "not card";
			}
			cardList.push(card);
		}
		return "ok";
	}
	
	public void addUser(User user) 
	{
		HGUser hgUser = new HGUser();
		hgUser.setUser(user);
		userList.add(hgUser);
		
		// ������� ������
		sendUserList();
	}

	public void removeUser(User user)
	{
		for (int i = 0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (user.getUserId() == hgUser.getUser().getUserId())
			{
				userList.remove(i);
				break;
			}
		}
		if (isStart)
		{
			// �������̸� �����ߴ�
			stopGame();
		}
		
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
	
	public void setReady(User user, Boolean isReady)
	{
		for (int i =0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (user.getUserId() == hgUser.getUser().getUserId())
			{
				hgUser.setIsReady(isReady);
				break;
			}
		}
	}
	
	public int getUserListSize()
	{
		return userList.size();
	}
	
	public boolean isStart()
	{
		return isStart;
	}

	public List<String> getOpenedCardList() 
	{
		int end = 4;
		if (cardList.size() < 4)
		{
			
		}
		for (int i = 0; (i < cardList.size()) && (i < 4); i++)
		{
			
		}
		return null;
	}

	/**
	 * ������� ������
	 */
	public void sendUserList()
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
		try 
		{
			jsonObject.put("result", "userList");
			jsonObject.put("data", jsonArray);
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
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
			jsonObject.put("msg", msg);
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
			if (user.getUserId() == hgUser.getUser().getUserId())
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