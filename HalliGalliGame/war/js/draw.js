var ctx;
var width;
var height;
var W = 300;
var H = 400;
var cardWidth;
var cardHeight;
var cardBack = new Image(); cardBack.src = "/image/CardBack.jpg";
var b1 = new Image(); b1.src = "/image/b1.jpg";
var b2 = new Image(); b2.src = "/image/b2.jpg";
var b3 = new Image(); b3.src = "/image/b3.jpg";
var b4 = new Image(); b4.src = "/image/b4.jpg";
var b5 = new Image(); b5.src = "/image/b5.jpg";
var l1 = new Image(); l1.src = "/image/l1.jpg";
var l2 = new Image(); l2.src = "/image/l2.jpg";
var l3 = new Image(); l3.src = "/image/l3.jpg";
var l4 = new Image(); l4.src = "/image/l4.jpg";
var l5 = new Image(); l5.src = "/image/l5.jpg";
var p1 = new Image(); p1.src = "/image/p1.jpg";
var p2 = new Image(); p2.src = "/image/p2.jpg";
var p3 = new Image(); p3.src = "/image/p3.jpg";
var p4 = new Image(); p4.src = "/image/p4.jpg";
var p5 = new Image(); p5.src = "/image/p5.jpg";
var s1 = new Image(); s1.src = "/image/s1.jpg";
var s2 = new Image(); s2.src = "/image/s2.jpg";
var s3 = new Image(); s3.src = "/image/s3.jpg";
var s4 = new Image(); s4.src = "/image/s4.jpg";
var s5 = new Image(); s5.src = "/image/s5.jpg";

function drawBoard(){
	ctx.clearRect(0, 0, width, height);
	ctx.fillStyle = "white";
	ctx.fillRect(0, 0, width, height);
	
	drawCardList();
	drawUserList();
}

function drawUserList(){
	var x = [0, 160*width/W, 0, 160*width/W];
	var y = [0, 0, 200*height/H, 200*height/H];
	for (var i=0; i<4;i++){
		nickName = "not";
		if (userList.length > i){
			nickName = userList[i].nickName;
		}
		ctx.fillStyle = "#000000";
		ctx.font = "normal 15px dotum";
		ctx.textBaseline = "top";
		ctx.fillText(nickName, x[i], y[i]);
	}
}

function drawCardList(){
	var x = [0, 160*width/W, 0, 160*width/W];
	var y = [25*height/H, 25*height/H, 225*height/H, 225*height/H];
	// 고정된 카드뒷면 그리기
	for (var i=0; i<4; i++){
		ctx.drawImage(cardBack, x[i], y[i], cardWidth, cardHeight);
	}
	
	ctx.fillStyle = "#000000";
	ctx.font = "normal 15px dotum";
	ctx.textBaseline = "top";
	
	// 뒤집어진 카드 그리기
	x = [70*width/W, 230*width/W, 70*width/W, 230*width/W];
	y = [25*height/H, 25*height/H, 225*height/H, 225*height/H];
	var xx = [0, 160*width/W, 0, 160*width/W];
	var yy = [175*height/H, 175*height/H, 375*height/H, 375*height/H];
	for (var i=0; i<4; i++){
		if (openedCardList.length <= i){
			ctx.drawImage(cardBack, x[i], y[i], cardWidth, cardHeight);
		} else {
			if (openedCardList[i].openedCard == ""){
				ctx.drawImage(cardBack, x[i], y[i], cardWidth, cardHeight);
			} else {
				ctx.drawImage(selectCard(openedCardList[i].openedCard), x[i], y[i], cardWidth, cardHeight);
			}
			ctx.fillText(openedCardList[i].count, xx[i], yy[i]);
		}
	}
}

function selectCard(card){
	if (card == "B1"){
		return b1;
	}else if (card == "B2"){
		return b2;
	} else if (card == "B3"){
		return b3;
	} else if (card == "B4"){
		return b4;
	} else if (card == "B5"){
		return b5;
	} else if (card == "L1"){
		return l1;
	} else if (card == "L2"){
		return l2;
	} else if (card == "L3"){
		return l3;
	} else if (card == "L4"){
		return l4;
	} else if (card == "L5"){
		return l5;
	} else if (card == "P1"){
		return p1;
	} else if (card == "P2"){
		return p2;
	} else if (card == "P3"){
		return p3;
	} else if (card == "P4"){
		return p4;
	} else if (card == "P5"){
		return p5;
	} else if (card == "S1"){
		return s1;
	} else if (card == "S2"){
		return s2;
	} else if (card == "S3"){
		return s3;
	} else if (card == "S4"){
		return s4;
	} else if (card == "S5"){
		return s5;
	}
	return "";
}
