package com.mudchobo.game.halligalligame.controllers;

import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@Controller
@RequestMapping("/test")
public class TestController 
{
	private static final Logger log = Logger.getLogger(TestController.class.getName());
	
	private String channelName = "test";
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String test(Model model)
	{
		log.info("test!");
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(channelName);
		model.addAttribute("token", token);
		return "test";
	}
	
	@RequestMapping(value="/open", method=RequestMethod.POST)
	@ResponseBody
	public String openChat()
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(channelName, "open"));
		return "";
	}
	
	@RequestMapping(value="/send", method=RequestMethod.POST)
	public String sendChat(@RequestParam("msg") String msg)
	{
		log.info("sendChat!");
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(channelName, msg));
		return "";
	}
}
