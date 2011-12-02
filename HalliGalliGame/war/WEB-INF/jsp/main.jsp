<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="/css/jquery.mobile-1.0.min.css" />
		<script type="text/javascript" src="/_ah/channel/jsapi"></script>
		<script type="text/javascript" src="/js/lib/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.mobile-1.0.min.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript">
			var channel;
			var socket;
			var roomNumber = "${roomNumber}";
			var nickName = "${nickName}";
			var userId = "${userId}";
			
			$(document).ready(function(){
				// 채널소켓
				channel = new goog.appengine.Channel("${token}");
				socket = channel.open();
				socket.onopen = onOpen;
				socket.onmessage = onMessage;
				socket.onError = function(){
					alert("error!");
					location.href = "/hg/index";
				};
				socket.onClose = function(){
					alert("close!");
					location.href = "/hg/index";
				}
				
				// 버튼이벤트
				$("#btnReady").click(function(){
					var requestValue = true;
					if (isReady){
						requestValue = false;
					}
					sendReady(requestValue);
				});
				$("#btnStart").click(function(){
					sendStart();
				});
				$("#btnOpenCard").click(function(){
					
				});
				$("#btnRing").click(function(){
					
				});
				$("#btnChat").click(function(){
					var val = $("#inputChat").val();
					if (val == ""){
						return;
					}
					sendChat(val);
				});
				$("#inputChat").keyup(function(e){
					if (e.keyCode == 13){
						var val = $(this).val();
						if (val == ""){
							return;
						}
						sendChat(val);
					}
				});
			});
		</script>
	</head>
	<body>
		<div data-role="page">
			<div data-role="header">
				<h1>Halli Galli</h1>
			</div>
			<div data-role="content">
				<canvas></canvas>
				<div data-role="controlgroup" data-type="horizontal">
					<a id="btnStart" href="#" data-role="button">시작</a>
					<a id="btnReady" href="#" data-role="button">준비</a>
					<a id="btnOpenCard" href="#" data-role="button">뒤집기</a>
					<a id="btnRing" href="#" data-role="button">벨</a>
				</div>
			</div>
		</div>
		<input type="text" id="inputChat" />
		<input type="button" id="btnChat" value="보내기"/>
		내용 : <div class="divResult"><ul></ul></div>
    </body>
</html>