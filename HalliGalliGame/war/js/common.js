/** 공용 js함수 **/

var isKing = false;
var isReady = false;
var number = 0;

function onOpen(){
	$(".divResult ul").prepend("<li>open!</li>");
	disableAllButton();
	connectRoom();
};

function onMessage(m){
	console.log(m.data);
	
	var r = eval("(" + m.data + ")");
	if (r.result == "chat"){
		$(".divResult ul").prepend(r.msg);
	}
	else if (r.result == "userList"){
		$(".divResult ul").prepend(m.data);
		userList = r.data;
		setUserList();
	}
	else if (r.result == "start"){
		$("#btnStart").addClass("ui-disabled");
		$("#btnReady").addClass("ui-disabled");
		$("#btnBell").removeClass("ui-disabled");
		$("#btnOpenCard").removeClass("ui-disabled");
		$("#ulList").prepend("<li>" + r.msg + "</li>").listview('refresh');
	}
	else if (r.result == "openCard"){
		openedCardList = r.openedCardList;
	}
	else if (r.result == "ringBell"){
		alert(r.msg);
		$("#ulList").prepend("<li>" + r.msg + "</li>").listview('refresh');
	}
	else if (r.result == "win"){
		alert(r.winner);
		$("#ulList").prepend("<li>" + r.winner + "</li>").listview('refresh');
	}
	else if (r.result == "error"){
		$("#ulList").prepend("<li>" + r.msg + "</li>").listview('refresh');
	}
}

function setUserList(){
	isKing = false;
	for (var i=0; i<userList.length; i++){
		if (userList[i].userId == userId && i == 0){
			isKing = true;
		} 
	}
	if (isKing){
		$("#btnStart").removeClass("ui-disabled");
		$("#btnReady").addClass("ui-disabled");
	} else{
		$("#btnStart").addClass("ui-disabled");
		$("#btnReady").removeClass("ui-disabled");
	}
	drawUserList();
}

function disableAllButton(){
	$("#btnStart").removeClass("ui-disabled");
	$("#btnReady").removeClass("ui-disabled");
	$("#btnBell").removeClass("ui-disabled");
	$("#btnOpenCard").removeClass("ui-disabled");
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

// 카드뒤집기
function sendOpenCard(){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/openCard?roomNumber=" + roomNumber);
	xhr.send();
}

// 종치기
function sendRingBell(){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/ringBell?roomNumber=" + roomNumber);
	xhr.send();
}