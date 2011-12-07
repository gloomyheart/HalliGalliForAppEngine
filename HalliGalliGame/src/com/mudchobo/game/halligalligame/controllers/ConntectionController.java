package com.mudchobo.game.halligalligame.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
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
		
		gameService.connect(channelPresence.clientId());
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("result", "connect");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		channelService.sendMessage(new ChannelMessage(channelPresence.clientId(), jsonObject.toString()));
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
