/** 공용 js함수 **/

var isKing = false;
var isReady = false;
var number = 0;

function(){
	$(".divResult ul").prepend("<li>open!</li>");
	connectRoom();
};

function onMessage(m){
	var m = eval(m);
	if (m.data.result == "chat"){
		$(".divResult").prepend(m.msg);
	}
	else if (m.data.result == "userList"){
		$(".divResult").prepend(m.userList);
		// TODO 접속현황 그리기
		for (var i=0; i<m.userList.length; i++){
			if (m.userList[i].userId == userId && i == 0){
				isKing = true;
			} else {
				isKing = false;
			}
			// TODO 그리기
		}
		
		if (isKing){
			$("#btnStart").show();
			$("#btnReady").hide();
		} else{
			$("#btnStart").hide();
			$("#btnReady").show();
		}
	}
}
// 최초연결 시
function connectRoom(){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/connect?roomNumber=" + roomNumber);
	xhr.send();
}
// 채팅 메세지 보내기
function sendChat(msg){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/chat?roomNumber=" + roomNumber + "&msg=" + encodeURIComponent(msg));
	xhr.send();
}

// 레디요청 보내기
function sendReady(isReady){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/ready?roomNumber=" + roomNumber + "&isReady=" + isReady);
	xhr.send();
}

// 시작요청 보내기
function sendStart(){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/start?roomNumber=" + roomNumber);
	xhr.send();
} 