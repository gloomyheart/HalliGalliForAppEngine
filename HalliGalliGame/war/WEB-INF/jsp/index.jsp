<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<script type="text/javascript" src="/js/lib/jquery-1.7.1.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#btnEnter").click(function(){
					var val = $("#inputRoomNumber").val();
					if (val == ""){
						alert("방번호를 입력하세요.");
						return;
					}
					location.href = "/hg/main/" + val;
				});
			});
		</script>
	</head>
	<body>
		<c:choose>
			<c:when test="${isLogin == true}">
				<div class="userName">
					${userName}
					<a href="${logoutUrl}"><input type="button" value="로그아웃" /></a>
				</div>
				<div class="divRoom">
					방번호 : <input id="inputRoomNumber" type="input" name="roomNumber" />
					<input id="btnEnter" type="button" value="입장" />
				</div>
			</c:when>
			<c:otherwise>
				<a href="${loginUrl}"><input type="submit" value="로그인" /></a>
			</c:otherwise>
		</c:choose>
    </body>  
	</body>
</html>