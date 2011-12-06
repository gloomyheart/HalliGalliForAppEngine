<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1"> 
		<link rel="stylesheet" href="/css/jquery.mobile-1.0.min.css" />
		<script type="text/javascript" src="/_ah/channel/jsapi"></script>
		<script type="text/javascript" src="/js/lib/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.mobile-1.0.min.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/js/draw.js"></script>
		<script type="text/javascript">
			var channel;
			var socket;
			var roomNumber = "${roomNumber}";
			var nickName = "${nickName}";
			var userId = "${userId}";
			var userList = [];
			var openedCardList = [];
			
			function init(){
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
			}
			
			$(document).ready(function(){
				
				setTimeout(init, 1000);
				
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
					sendOpenCard();
				});
				$("#btnRingBell").click(function(){
					sendRingBell();
				});
				$("#inputChat").keyup(function(e){
					if (e.keyCode == 13){
						var val = $(this).val();
						if (val == ""){
							return;
						}
						$("#inputChat").val("");
						sendChat(val);
					}
				});
				
				// 보드판 그리기
				width = $(window).width();
				if (width >= W){
					width = W;
				}
				height = width * H / W;
				$("#canvas").width(width);
				$("#canvas").height(height);
				var canvas = $("#canvas").get(0);
				ctx = canvas.getContext("2d");
				ctx.canvas.width = width;
				ctx.canvas.height = height;
				cardWidth = width*70/W;
				cardHeight = height*150/H;
				// 보드판그리기 타이머시작
				setInterval(drawBoard, 10);
			});
		</script>
	</head>
	<body>
		<div data-role="page">
			<div data-role="header">
				<h1>Halli Galli</h1>
			</div>
			<div data-role="content">
				<canvas id="canvas"></canvas>
				<fieldset class="ui-grid-c">
					<div class="ui-block-a"><a id="btnStart" href="#" data-role="button">시작</a></div>
					<div class="ui-block-b"><a id="btnReady" href="#" data-role="button">준비</a></div>	   
					<div class="ui-block-c"><a id="btnOpenCard" href="#" data-role="button">뒤집기</a></div>	   
					<div class="ui-block-d"><a id="btnRingBell" href="#" data-role="button">벨</a></div>	   
				</fieldset>
				<div>
					<input type="text" id="inputChat" />
				</div>
				<ul id="ulList" data-role="listview">
				</ul>
			</div>
		</div>
    </body>
</html>