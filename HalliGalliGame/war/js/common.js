/** ���� js�Լ� **/

// �޼��� ������
function sendMessage(path, msg){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/" + path + "?msg=" + encodeURIcomponent(msg));
	xhr.send();
}

