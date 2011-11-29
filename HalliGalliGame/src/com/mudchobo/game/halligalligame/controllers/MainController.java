package com.mudchobo.game.halligalligame.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.mudchobo.game.halligalligame.services.GameService;

@Controller
public class MainController 
{
	private String prefix = "Channel";
	
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(Model model)
	{
		UserService userService = UserServiceFactory.getUserService();
		System.out.println("isLogin = " + userService.isUserLoggedIn());
		if (!userService.isUserLoggedIn()) 
		{
			model.addAttribute("isLogin", false);
			model.addAttribute("loginUrl", userService.createLoginURL("/hg/index"));
		}
		else
		{
			model.addAttribute("isLogin", true);
			model.addAttribute("userName", userService.getCurrentUser().getNickname());
			model.addAttribute("logoutUrl", userService.createLogoutURL("/hg/index"));
		}
		return "index";
	}
	
	@RequestMapping(value="main/{roomNumber}", method=RequestMethod.GET)
	public synchronized String main(@PathVariable("roomNumber")int roomNumber, Model model)
	{
		UserService userService = UserServiceFactory.getUserService();
		
		// �α��� ���� �ʾҰų� �濡 ��á���� �ʱ�ȭ������
		if (!userService.isUserLoggedIn() || gameService.isFullAtRoom(roomNumber))
		{
			model.addAttribute("isLogin", false);
			model.addAttribute("loginUrl", userService.createLoginURL("/hg/main"));
			return "index";
		}
		
		// �ش� ���ȣ�� �������̵��������� ä�λ���
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(prefix + roomNumber + userService.getCurrentUser().getUserId());
		
		// �ش� �濡 �����߰�
		gameService.connect(userService.getCurrentUser(), roomNumber);
		
		model.addAttribute("token", token);
		model.addAttribute("userName", userService.getCurrentUser().getNickname());
		model.addAttribute("logoutUrl", userService.createLogoutURL("/hg/main"));
		model.addAttribute("roomNumber", roomNumber);
		
		return "main";
	}
}
