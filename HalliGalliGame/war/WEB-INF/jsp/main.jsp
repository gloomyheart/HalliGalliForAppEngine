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
			
			$(document).ready(function(){
				// 채널소켓
				channel = new goog.appengine.Channel("${token}");
				socket = channel.open();
				socket.onopen = function(){
					connectRoom();
				};
				socket.onmessage = function(m){
					console.log(m.data);
				};
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
					
				});
				$("#btnStart").click(function(){
					
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
		<input type="button" id="btnReady" value="준비"/>
		<input type="button" id="btnStart" value="시작"/>
		<input type="button" id="btnOpenCard" value="뒤집기"/>
		<input type="button" id="btnRing" value="벨"/>
		<input type="text" id="inputChat" />
		<input type="button" id="btnChat" value="보내기"/>
		내용 : <div class="divResult"></div>
    </body>
</html>