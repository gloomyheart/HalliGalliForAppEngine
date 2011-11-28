package com.mudchobo.game.halligalligame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.mudchobo.game.halligalligame.controllers.TestController;

public class GameData 
{
	private static final Logger log = Logger.getLogger(TestController.class.getName());
	
	private String[] cardPrefixArray = {"B", "L", "P", "S"};
	private List<HGUser> userList;
	private Stack<String> cardList;
	private int nowPlayer = 0;
	private boolean isStart = false;
	
	public GameData() 
	{
		userList = new ArrayList<HGUser>();
		cardList = new Stack<String>();
	}

	/**
	 * ���� ����
	 * @return start:����, notPeople:����̺����Ѱ�� 
	 */
	public String startGame()
	{
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
		if (userListSize > 2)
		{
			return "notPeople";
		}
		
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
		return "start";
	}

	public void stopGame()
	{
		// TODO �����ߴ�
		isStart = false;
	}
	
	public String flipCard(User user)
	{
		HGUser hgUser = userList.get(nowPlayer);
		if (hgUser.getUserId() == user.getUserId())
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
		hgUser.setNickName(user.getEmail());
		hgUser.setNickName(user.getNickname());
		hgUser.setUserId(user.getUserId());
		userList.add(hgUser);
	}

	public void removeUser(User user)
	{
		for (int i = 0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (user.getUserId() == hgUser.getUserId())
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
	
	public String setReady(User user, Boolean isReady)
	{
		for (int i =0; i < userList.size(); i++)
		{
			HGUser hgUser = userList.get(i);
			if (user.getUserId() == hgUser.getUserId())
			{
				hgUser.setIsReady(isReady);
				break;
			}
		}
		return "ok";
	}
	
	public int getUserListSize()
	{
		return userList.size();
	}
	
	public boolean isStart()
	{
		return isStart;
	}
	
	
}