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
	
	@RequestMapping(value="/connect", method=RequestMethod.POST)
	public void connect(
			@RequestParam("roomNumber") int roomNumber,
			HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		User user = getUser();
		
		String clientId = "Channel" + roomNumber + user.getUserId();
		// 해당 방에 유저추가
		gameService.enterRoom(roomNumber, user, clientId);
	}
	
	@RequestMapping(value="/ready", method=RequestMethod.POST)
	public void setReady(
			@RequestParam("isReady") boolean isReady,
			@RequestParam("roomNumber") int roomNumber,
			HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		User user = getUser();
		gameService.setReady(user, isReady, roomNumber);
	}
	
	@RequestMapping(value="/start", method=RequestMethod.POST)
	public void start(
			@RequestParam("roomNumber") int roomNumber,
			HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		User user = getUser();
		// 게임시작 요청
		gameService.startGame(user, roomNumber);
	}
	
	@RequestMapping(value="/chat", method=RequestMethod.POST)
	public void chat(
			@RequestParam("roomNumber") int roomNumber,
			@RequestParam("msg") String msg,
			HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		User user = getUser();
		gameService.chat(user, roomNumber, msg);
	}
	
	@RequestMapping(value="/openCard", method=RequestMethod.POST)
	public void openCard(
			@RequestParam("roomNumber") int roomNumber) throws IOException
	{
		User user = getUser();
		gameService.openCard(user, roomNumber);
	}
	
	@RequestMapping(value="/ringBell", method=RequestMethod.POST)
	public void ringBell(
			@RequestParam("roomNumber") int roomNumber) throws IOException
	{
		User user = getUser();
		gameService.ringBell(user, roomNumber);
	}
	
	private User getUser()
	{
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
}

