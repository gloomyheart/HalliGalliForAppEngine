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
	private List<Stack<String>> cardList;
	
	public GameData() 
	{
		userList = new ArrayList<HGUser>();
		cardList = new ArrayList<Stack<String>>();
		initGameData();
	}

	public void initGameData()
	{
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
		
		// Ä«µå¼¯±â
		Random random = new Random();
		for (int i = 55; i > 0; i--)
		{
			String temp; int j = random.nextInt(56);
			temp = tempCardList[i];
			tempCardList[i] = tempCardList[j];
			tempCardList[j] = temp;
		}
		log.info(tempCardList[0]);
	}
	
	public List<HGUser> getUserList() 
	{
		return userList;
	}

	public void setUserList(List<HGUser> userList) 
	{
		this.userList = userList;
	}

	public void addUser(User user) 
	{
		HGUser hgUser = new HGUser();
		hgUser.setNickName(user.getEmail());
		hgUser.setNickName(user.getNickname());
		userList.add(hgUser);
	}

	public void setReady(User user, Boolean isReady) 
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
	}
}
