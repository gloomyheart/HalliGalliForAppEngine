/** 공용 js함수 **/

// 메세지 보내기
function sendMessage(path, msg){
	var xhr = new XMLHttpRequest();
	xhr.open("post", "/hg/channel/" + path + "?msg=" + encodeURIcomponent(msg));
	xhr.send();
}

