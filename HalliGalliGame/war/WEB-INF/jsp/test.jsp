<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<script type="text/javascript" src="/_ah/channel/jsapi"></script>
		<script type="text/javascript" src="/js/lib/jquery-1.7.1.min.js"></script>
		<script type="text/javascript">
			function sendMessage(path, msg){
				var xhr = new XMLHttpRequest();
				xhr.open("post", "/hg/channel/" + path + "?msg=" + msg);
				xhr.send();
			}
			
			$(document).ready(function(){
				var channel = new goog.appengine.Channel("${token}");
				var socket = channel.open();
				socket.onopen = function(){
					alert("open!");
					sendMessage("open", "");
				};
				socket.onmessage = function(m){
					console.log(m.data);
				};
				socket.onError = function(){
					alert("error!");
				};
				socket.onClose = function(){
					alert("close!");
				}
				
				$("#send_btn").click(function(){  
                    sendMessage("send", $("#message").val());  
                    $("#message").val("");  
                });  
                $("#message").keyup(function(e){  
                    if (e.keyCode == 13){  
                        sendMessage("send", $("#message").val());  
                        $("#message").val("");  
                    }  
                });  
			});
		</script>
	</head>
	<body>
		<div id="message_box" style="width:500px;height:300px;overflow:scroll;font-size:12px">  
			<ul></ul>  
        </div>  
        <input type="text" id="message" name="message" />  
        <input type="button" value="보내기" id="send_btn" name="send_btn" /><br />  
    </body>  
	</body>
</html>