/** ���� js�Լ� **/

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
		// TODO ������Ȳ �׸���
		var userList = m.data.data;
		for (var i=0; i<userList.length; i++){
			if (userList[i].userId == userId && i == 0){
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