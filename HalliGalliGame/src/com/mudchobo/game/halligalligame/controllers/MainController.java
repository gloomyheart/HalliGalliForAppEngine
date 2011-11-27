package com.mudchobo.game.halligalligame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
			model.addAttribute("loginUrl", userService.createLoginURL("/hg/main"));
		}
		else
		{
			model.addAttribute("isLogin", true);
			model.addAttribute("userName", userService.getCurrentUser().getNickname());
			model.addAttribute("logoutUrl", userService.createLogoutURL("/hg/main"));
		}
		return "index";
	}
	
	@RequestMapping(value="main/{roomNumber}", method=RequestMethod.GET)
	public String main(@PathVariable("roomNumber")Long roomNumber, Model model)
	{
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn())
		{
			model.addAttribute("isLogin", false);
			model.addAttribute("loginUrl", userService.createLoginURL("/hg/main"));
			return "index";
		}
		
		// 해당 방에 사람이 꽉찼는지 체크
		if (gameService.isFullAtRoom(roomNumber))
		{
			
		}
		
		// 해당 방번호로 채널생성
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(prefix + roomNumber);
		model.addAttribute("token", token);
		
		model.addAttribute("userName", userService.getCurrentUser().getNickname());
		model.addAttribute("logoutUrl", userService.createLogoutURL("/hg/main"));
		model.addAttribute("roomNumber", roomNumber);
		
		return "main";
	}
}
