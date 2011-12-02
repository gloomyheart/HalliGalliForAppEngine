/** 공용 js함수 **/

var isKing = false;
var isReady = false;
var number = 0;

function onOpen(){
	$(".divResult ul").prepend("<li>open!</li>");
	connectRoom();
};

function onMessage(m){
	console.log(m.data);
	
	var m = eval(m.data);
	if (m.data.result == "chat"){
		$(".divResult ul").prepend(m.data.msg);
	}
	else if (m.data.result == "userList"){
		$(".divResult ul").prepend(m.userList);
		// TODO 접속현황 그리기
		var userList = m.data.data;
		for (var i=0; i<userList.length; i++){
			if (userList[i].userId == userId && i == 0){
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