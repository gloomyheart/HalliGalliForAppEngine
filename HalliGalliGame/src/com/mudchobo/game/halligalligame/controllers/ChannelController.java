package com.mudchobo.game.halligalligame.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
		map.put("result", "ok");
		
		JSONObject object = new JSONObject(map);
		System.out.println(object.toString());
		res.addHeader("Content-Type", "application/json");
		res.getWriter().print(object.toString());
	}
	
	@RequestMapping(value="/start", method=RequestMethod.GET)
	public void start(
			@RequestParam("roomNumber") int roomNumber,
			HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		Map<String, String> map = new HashMap<String, String>();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		// 게임시작 요청
		String result = gameService.startGame(user, roomNumber);
		
		map.put("result", result);
		
		// 게임이 시작되었으면 진행
		if (result == "start")
		{
			List<String> currentCardList = gameService.getCurrentCardList(roomNumber);
		}
	}
}
