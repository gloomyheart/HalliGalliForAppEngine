/** ���� js�Լ� **/

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

