var image = document.getElementById("infoimg");
var text = document.getElementById("infotxt");

function homeinfo(){
	//text.innerHTML = "Home - takes you back to the start / home screen";
	image.src = "img/homesp.png";
}

function chatinfo(){
	//document.getElementById("infotxt").innerHTML = "Chat - takes you to the Chat Screen, for skype and such";
	image.src="img/chatsp.png";
}

function funinfo(){
	//document.getElementById("infotxt").innerHTML = "Fun - takes you to the Entertainment Screen, for games and the radio";
	image.src="img/funsp.png";
}

function helpinfo(){
	//document.getElementById("infotxt").innerHTML = "Help - takes you to the Help Screen, for the help guide and checking skype version"
	image.src="img/helpsp.png";
}

function hideinfo(){
	//document.getElementById("infotxt").innerHTML = "Show / Hide - the open eye will hide the other buttons"
	image.src="img/hidesp.png";
}

function showinfo(){
	//document.getElementById("infotxt").innerHTML = "Show / Hide - the closed eye will show the other buttons"
	image.src="img/showsp.png";
}