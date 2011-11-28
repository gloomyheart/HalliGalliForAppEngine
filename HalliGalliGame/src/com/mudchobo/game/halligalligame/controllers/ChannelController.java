package com.mudchobo.game.halligalligame.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.mudchobo.game.halligalligame.services.GameService;

@Controller
@RequestMapping("/channel")
public class ChannelController 
{
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value="/ready", method=RequestMethod.GET)
	public void setReady(
			@RequestParam("isReady") boolean isReady,
			@RequestParam("roomNumber") int roomNumber,
			HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		gameService.setReady(user, isReady, roomNumber);
		Map<String, String> map = new HashMap<String, String>();
		map.put("aa", "bb");
		map.put("cc", "dd");
		
		JSONObject object = new JSONObject(map);
		System.out.println(object.toString());
		res.addHeader("Content-Type", "application/json");
		res.getWriter().print(object.toString());
	}
	
	@RequestMapping(value="/start", method=RequestMethod.GET)
	public void start(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		
	}
}
