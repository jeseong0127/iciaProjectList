<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="resources/css/mainStyle.css">
<link rel="stylesheet" href="resources/css/calendar.css">
<script type="text/javascript" src="resources/js/js.js"></script>
<script>
let teamid; // 선택한 팀id


//멤버 추가
function addfriends(){
	let sendJsonData = [];
	sendJsonData.push({});
	let clientData = JSON.stringify(sendJsonData);
	postAjaxTeam('schedule/addFriends',clientData,"getFriendsList");
}

//멤버 추가할 친구 리스트
function getFriendsList(jsonData){
	let backpop = document.getElementById("backpop");
	let frontpop = document.getElementById("frontpop");
	
	backpop.style.display = "block";
	frontpop.style.display = "block";
	
	let friendsList = document.getElementById("frontpop");
	let friendsData = "";
	
	// 팀원 추가 했을 때 친구리스트 가져오는 경우
	if(teamid !=null){
		for(i=0; i < jsonData.length; i++){
			friendsData += "<input type='checkbox' name ='tdetails' value='"+jsonData[i].mbid+"'/>" + jsonData[i].mbname +"("+ jsonData[i].mbid +")"+ "<br>";
		}
		friendsData += "<input type='button' value='추가하기' onClick='checkFriends()'/>" + "<br>";
		
	// 친구 버튼 클릭했을 때 친구리스트 가져오는 경우
	}else{
		friendsData += "<div>[친구목록]</div>";
		for(i=0; i < jsonData.length; i++){
			friendsData += jsonData[i].mbname +"("+ jsonData[i].mbid +")"+ "<br>";
		}
		friendsData += "<br>" + "<input type='button'onClick='friendSearch_box()' value='친구검색'/>";
		friendsData += "<div id='friendSearch'></div>";
		friendsData += "<div id='friendResult'></div>";
		friendsData += "<div>[요청함]</div>";
		friendsData += "<div id='friendState'></div>";
	}
	
	friendsList.innerHTML = friendsData + "<br>" + "<span onClick='getFriendsListOff()'>닫기</span>";
}
function getFriendsListOff(){
	backpop.style.display = "none";
	frontpop.style.display = "none";
}

//친구요청상태
function addFriendState(){
	let sendJsonData = [];
	sendJsonData.push({});
	let clientData = JSON.stringify(sendJsonData);
	postAjaxTeam('schedule/addFriendState',clientData,"getFriendState");
}

//친구요청상태 리스트
function getFriendState(jsonData){
	let stateList = document.getElementById("friendState");
	let text = "";
	for(i=0; i < jsonData.length; i++){
		text += jsonData[i].mbid2 + "<br>";
	}
	stateList.innerHTML = text;
}


// check한 친구들에게 팀초대 보내기 
function checkFriends(){
	let sendJsonData = [];
	let sendJsonData2 = [];
	let checkbox = document.getElementsByName("tdetails");
	
	for(i=0; i < checkbox.length; i++){
		if(checkbox[i].checked == true){
			sendJsonData2.push({mbid:checkbox[i].value}); //[ {mbid="예은"},{mbid="jseoeng"} ]
		}
	}
	sendJsonData.push({tid:teamid,tdetails:sendJsonData2}); //[ {  tdetails:[{mbid="예은"},{mbid="jseoeng"}] } ]
	let clientData = JSON.stringify(sendJsonData);
	alert(clientData);
	postAjax('schedule/addFriendlist',clientData,"clear");
}

// 초대장 전송
function clear(jsonData){
	let text="";
	for(i=0; i < jsonData.length; i++){
		text += jsonData[i].mbid + " , ";
	}
	alert(text+"에게 초대장을 전송하였습니다.");
}


// 친구 검색 박스
function friendSearch_box(){
	let Search = document.getElementById("friendSearch");
	
	Search.innerHTML = "<input type='text' name='search'/>"+ "<input type='button' name='search_btn' value='검색' onClick='friendSearch()'/>";
	
}

// 친구 검색
function friendSearch(ucode){
	let userid = document.getElementsByName("search")[0];
	let sendJsonData = [];
	sendJsonData.push({word:userid.value,mbid:ucode});
	let clientData = JSON.stringify(sendJsonData);
	
	postAjax('schedule/searchFriends',clientData,"friendSearch_list");
}


// 친구리스트 뽑아오기
function friendSearch_list(jsonData){
	let result = document.getElementById("friendResult");
	let text="";
	
	if(jsonData != ""){
		for(i=0; i < jsonData.length; i++){
			text += "<input type='checkbox' name='invite' value="+jsonData[i].mbid+">"+"친구아이디 : " + jsonData[i].mbid +" 친구이름 : "+ jsonData[i].mbname + "<br>";
		}
		text += "<div id='inviteFriends'></div><br>"+"<input type='button' value='친구요청' onClick='friendRequest()' />"
	}else{
		text += "검색 결과가 없습니다. "+ "<br>" + "<input type='email' name='friendEmail' >" + "<br>" + "<input type='button' onClick='sendFriendEmail()' value = '이메일로 초대장 전송하기'>"
	}
	result.innerHTML = text;
}

// 회원인 사람 친구 요청 전송
function friendRequest(){
	/*
	let sendJsonData = [];
	let sendJsonData2 = [];
	let checkBox = document.getElementsByName("invite");
	
	for(i=0; i < checkBox.length; i++){
		if(checkBox[i].checked == true){
			sendJsonData2.push({uCode:checkBox[i].value});
		}
	}
	sendJsonData.push({mbid2:sendJsonData2});
	let clientData = JSON.stringify(sendJsonData);
	postAjax('schedule/inviteFriends',clientData,"friendRequestList");
	*/
	
	let sendJsonData = [];
	let checkBox = document.getElementsByName("invite");
	
	for(i=0; i < checkBox.length; i++){
		if(checkBox[i].checked == true){
			sendJsonData.push({mbid2:checkBox[i].value});
		}
	}
	let clientData = JSON.stringify(sendJsonData);
	postAjax('schedule/inviteFriends',clientData,"friendRequestList");
}

// 초대보낸 친구 알림
function friendRequestList(Data){
	let result = document.getElementById("inviteFriends");
	result.innerText = JSON.parse(JSON.stringify(Data)).mbid2;
}

// 친구초대 메일 전송
function sendFriendEmail(){
	let friendEmail = document.getElementsByName("friendEmail")[0].value;
	
	let sendJsonData = [];
	sendJsonData.push({umail:friendEmail});
	let clientData = JSON.stringify(sendJsonData);
	
	postAjax('schedule/sendFriendEmail',clientData,"resultSendEmail");
}

function resultSendEmail(message){
	// alert(message) -> object Object;
	// JSON.stringify(message) -> {"message":"je_seong2@naver.com님에게 친구요청 메일을 보냈습니다."}
	alert(JSON.parse(JSON.stringify(message)).message); // je_seong2@naver.com님에게 친구요청 메일을 보냈습니다.
	
}

let count = 0;
function picture(){
	let div = document.getElementById("pictureBox");
	let sendDiv = document.getElementById("pictureBoxbtn");
	
	if(count < 3){
		div.innerHTML += "<div class='pictureAddBox'><input multiple='multiple' type='file' name='files' value='찾아보기'/><input type='button' value='삭제' onClick ='pictureBoxRemove(this)'/></div>";
	}
	
	if(count < 3){
		count++;
	}
}
function pictureBoxRemove(child){
	let parent = child.parentNode;
	parent.parentNode.removeChild(parent);
	count--;
	if(count == 0){
		let div2 = document.getElementById("pictureSend");
		div2.remove();
	}
}

function sendFictures() {
	let files = document.getElementsByName("files");
	
	let form = document.createElement("form");
	
	form.action = "pictures";
	form.method = "post";
	form.enctype= "multipart/form-data";
	
	for(i=files.length-1; -1 < i; i--){
		form.appendChild(files[i]);
	}
	
	document.body.appendChild(form);
	
	form.submit();
}


function getTeamList(id){
	let sendJsonData = [];
	sendJsonData.push({mbid:id});
	let clientData = JSON.stringify(sendJsonData);
	postAjax('schedule/teamList',clientData,'teamList');
}

function teamList(data){
	let list = document.getElementById("getTeamList");
	let text = "<select name='tid'>";
	for(i=0; i < data.length; i++){
		text += "<option value='"+data[i].tid+"'>"+data[i].tname+"</option>";
	}
	text += "</select>";
	
	list.innerHTML = text;
	
}

function sendSchedule(){
	
	let tid = document.getElementsByName("tid")[0];
	//alert(tid.options[tid.selectedIndex].value);
	let title = document.getElementsByName("title")[0];
	
	let location = document.getElementsByName("location")[0];
	let contents = document.getElementsByName("contents")[0];
	let open = document.getElementsByName("open")[0];
	let loop = document.getElementsByName("loop")[0];
	let files = document.getElementsByName("files");
	
	let chDate = document.getElementById("chDate").innerText;
	
	let date = makeInput("hidden","date",chDate);
	
	let f = document.createElement("form");
	
	f.action = "sendSchedule";
	f.method = "post";
	
	f.appendChild(tid);
	f.appendChild(title);
	f.appendChild(date);
	f.appendChild(location);
	f.appendChild(contents);
	f.appendChild(open);
	f.appendChild(loop);
	
	for(i=files.length-1; -1 < i; i--){
		f.appendChild(files[i]);
	}
	
	document.body.appendChild(f);
	
	f.submit();
	
}


</script>

</head>
<!--  <body onLoad="callTeamList()">-->
<body onload="getTeamList('${uCode}')">
	
<div class = "wideZone" >
	
	<div id="backpop" style="display:none;">
	</div>
	
	<div id="frontpop" style="display:none;">
	</div>
	
	
	<div class="header">
		<div class="header_title">
			<span class="header_title_text"> Team5 Schedule </span>
		</div>
	</div>
	
	<div id='picture' style="text-align:center;">
	<img src="resources/images/${img}" style="height:100px; width:100px;"/>
	</div>
	
	<div class="user">

		<div class="user_box">
			<div>
				${uCode} 님 <!--${umail}--> :: 	<span onClick="logOut('${uCode}')">로그아웃</span>
			</div>
		</div>

	</div>

	<div class="schedule">
		<div class="select_box">
			<br>
			<br>
			<a href="teamAdd">팀관리&nbsp;&nbsp;/</a>
			<span onClick="addfriends(),addFriendState()">친구</span>
		</div>
	</div>
	
	
	
	<div class = "sideForm">
		<div style="width: 100%; height: 50px;" class="infoBlank">
	
		<input type="button" class="sideAddBtn" value = "add Event+" onclick="modalOpen()" />
		</div>
		<div id="sideInfo" class="infoBlank">
			<!-- 해당일의 일정 리스트 표시  -->
		</div>
		<div id="getSchedule">
		</div>
	</div>
	<div class = "MngForm">
	    <div class="calendar">
	        <div class="header">
	            <div class="year-month"></div>
	            <div class="nav">
	                <button class="nav-btn go-prev" onclick="prevMonth()">&lt;</button>
	                <button class="nav-btn go-today" onclick="goToday()">Today</button>
	                <button class="nav-btn go-next" onclick="nextMonth()">&gt;</button>
	            </div>
	        </div>
	        <div class="main">
	            <div class="days">
	                <div class="day">일</div>
	                <div class="day">월</div>
	                <div class="day">화</div>
	                <div class="day">수</div>
	                <div class="day">목</div>
	                <div class="day">금</div>
	                <div class="day">토</div>
	            </div>
	            <div class="dates"></div>
	        </div>
	    </div>
	</div>
	
	<div id="modal" class="modal-overlay">
      <div class="modal-window">
      	<div class="close-area" onClick="modalClose()">X</div>
      	
      	
      		팀 : 
      	<div id="getTeamList">
		</div>
		
		<!--	날짜 :
		  <input type="date" name = "date"/> -->
		
		<div>
			제목 : <input type="text" name="title" />
		</div>
		
		<div>
			장소 : <input type="text" name="location" />
		</div>
			
			내용 :
		<div>
			 <textarea rows="" cols="" name="contents"></textarea>
		</div>
		
		<div>
			공개여부 : <select name="open">
			<option value ="O">공개</option>
			<option value ="N">비공개</option>			
			</select>
		</div>
		
		<div>
			반복 : <select name="loop">
			<option value ="X">반복없음</option>
			<option value ="W">주</option>
			<option value ="M">월</option>			
			<option value ="Y">년</option>			
			</select>
		</div>
		
		<div id="pictureBoxbtn">
			<input type="button" value="사진추가" onClick="picture()"/>
		</div>
			<div id ="pictureBox">
		</div>
		
		<div>
			<input type="button" value="전송" onClick="sendSchedule()">
		</div>
      	
      	
      	
      </div>
      </div>
       
   </div>
    <script type="text/javascript" src="resources/js/newDashboard.js"></script>	
	

</body>
</html>