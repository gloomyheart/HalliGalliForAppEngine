/** 공용 js함수 **/

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

