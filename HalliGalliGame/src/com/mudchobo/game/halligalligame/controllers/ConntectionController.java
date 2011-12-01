package com.mudchobo.game.halligalligame.controllers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.mudchobo.game.halligalligame.services.GameService;

@Controller
public class ConntectionController 
{
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value="/_ah/channel/connected", method=RequestMethod.POST)
	public void connected(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		ChannelPresence channelPresence = channelService.parsePresence(req);
		System.out.println(channelPresence.clientId());
		
		boolean isConnect = gameService.connect(channelPresence.clientId());
		if (!isConnect)
		{
			// TODO 에러처리
		}
	}
	
	@RequestMapping(value="/_ah/channel/disconnected", method=RequestMethod.POST)
	public void disconnected(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		System.out.println("disconnected");
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		ChannelPresence channelPresence = channelService.parsePresence(req);
		System.out.println(channelPresence.clientId());
		gameService.disconnect(channelPresence.clientId());
	}
}
