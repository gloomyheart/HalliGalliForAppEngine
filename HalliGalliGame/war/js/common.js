/** ���� js�Լ� **/

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
		// TODO ������Ȳ �׸���
		for (var i=0; i<m.userList.length; i++){
			if (m.userList[i].userId == userId && i == 0){
				isKing = true;
			} else {
				isKing = false;
			}
			// TODO �׸���
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
// ���ʿ��� ��
function connectRoom(){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/connect?roomNumber=" + roomNumber);
	xhr.send();
}
// ä�� �޼��� ������
function sendChat(msg){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/chat?roomNumber=" + roomNumber + "&msg=" + encodeURIComponent(msg));
	xhr.send();
}

// �����û ������
function sendReady(isReady){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/ready?roomNumber=" + roomNumber + "&isReady=" + isReady);
	xhr.send();
}

// ���ۿ�û ������
function sendStart(){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/start?roomNumber=" + roomNumber);
	xhr.send();
} 