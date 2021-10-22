<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="resources/css/mainStyle.css">
<script type="text/javascript" src="resources/js/js.js"></script>
<script>
let teamid; // 선택한 팀id

function callTeamList(id){
	if(teamid != null){ //팀 선택후 , 친구버튼 클릭시 에러방지
		teamid = null;
	}
	let sendJsonData = [];
	sendJsonData.push({mbid:id}); // [{mbid:jeseong2021}]
	let clientData = JSON.stringify(sendJsonData);
	alert(clientData);
	postAjax('schedule/teamList',clientData,'getTeamList');
}

function getTeamList(jsonData){
	alert(jsonData);
	let teamList = document.getElementById("main");
	let teamData = "";
	
	for(i=0; i < jsonData.length; i++){
		//let teamData = "<span onClick='callMemberList("+jsonData[0].tid+")'>"+ jsonData[0].tid + jsonData[0].tname+ jsonData[0].mbid +"</span>"
		//teamData += "<div class='teamList'><div class='teamList_box' onClick='callMemberList("+jsonData[i].tid+")'><ul class ='teamList_ul'><li>[순번]</li><li>[팀이름]</li><li>[팀아이디]</li><li>[관리자]</li></ul><ul class ='teamList_ul'><li>"+(i+1)+"</li><li>"+jsonData[i].tname+"</li><li>"+jsonData[i].tid+"</li><li>"+jsonData[i].mbid+"</li></ul><div class='memberList_box'></div></div></div>";
		teamData += "<div class='teamList'><div class='teamList_box' onClick='callMemberList("+jsonData[i].tid+","+i+")'><ul class ='teamList_ul'><li>[순번]</li><li>[팀이름]</li><li>[팀아이디]</li><li>[관리자]</li></ul><ul class ='teamList_ul'><li>"+(i+1)+"</li><li>"+jsonData[i].tname+"</li><li>"+jsonData[i].tid+"</li><li>"+jsonData[i].mbid+"</li></ul></div></div><div class='memberList_box'></div>";
	}
	
	teamList.innerHTML = teamData;
}

// 팀의 멤버들 호출
function callMemberList(tidvalue,count){
	alert("실행됨");
	let sendJsonData = [];
	sendJsonData.push({tid:tidvalue});
	let clientData = JSON.stringify(sendJsonData);
	postAjax('schedule/memberList',clientData,'getMemberList',count);
}

// 팀의 멤버리스트 가져오기
function getMemberList(jsonData,count){
	let memberlist = document.getElementsByClassName("memberList_box")[count];
	
	teamid = jsonData[0].tid; 
	
    if(memberlist.style.display == "block"){
    	memberlist.style.display = "none";
    	teamid = null;
    }else{
    	memberlist.style.display = "block";
    }
	
	let memberData = "";
	for(i=0; i < jsonData.length; i++){
		memberData += "<ul class ='memberList_ul'><li>[닉네임]</li><li>[등급]</li><li>[아이디]</li></ul><ul class ='memberList_ul'><li>"+jsonData[i].mbname+"</li><li>"+jsonData[i].cgname+"</li><li>"+jsonData[i].mbid+"</li></ul>";
	}
	memberlist.innerHTML = memberData + "<div id='addMemberList'></div>" +"<input type='button' value='팀원 추가하기' onClick='addfriends()'/>";
}

//팀 추가
function addTeam(id){
	let teamName = prompt("팀이름을 입력해주세요");
	let sendJsonData = [];
	sendJsonData.push({tname:teamName});
	sendJsonData.push({mbid:id});
	let clientData = JSON.stringify(sendJsonData);
	alert(clientData);
	postAjaxTeam('schedule/addTeam',clientData,"getTeamList");
}

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
		friendsData += "<input type='button' value='[요청함]' onClick='addFriendState()'/>";
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
		if(jsonData[0].mbid2 == "받은 요청이 없습니다"){
			text += "받은 요청이 없습니다" + "<br>";
		}else{
			text += jsonData[i].mbid2 + "<input type ='hidden' name='fname' value='"+jsonData[i].mbid2+"'/><input type ='button' value='수락' onClick='yesFriend("+i+")' /><input type ='button' value='거절' onClick='noFriend("+jsonData[i].mbid2+")' />"+ "<br>";
			//text += jsonData[i].mbid2 + "<span onClick='yesFriend("+jsonData[i].mbid2+")'>수락</span>"+"<span onClick='noFriend("+jsonData[i].mbid2+")'>거절</span>";
		
		}
	}
	stateList.innerHTML = text;
}	

// 친구신청 -> 수락
function yesFriend(index){
	let fname = document.getElementsByName("fname")[index].value;
	let sendJsonData = [];
	sendJsonData.push({mbid2:fname});
	let clientData = JSON.stringify(sendJsonData);
	postAjaxTeam('schedule/addFriendState',clientData,"다음은?");
}

//친구신청 -> 거절
function noFriend(friendId){
	alert(friendId);
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

</script>

</head>
<!--  <body onLoad="callTeamList()">-->
<body onLoad="callTeamList('${uCode}')">
	
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
			<a href="dashboard">스케줄관리&nbsp;&nbsp;/</a>
			<span onClick="callTeamList('${uCode}');addfriends();">친구</span>
		</div>
	</div>
	
	<div id="friends">
	</div>
	
	<div id="main">
	</div>
	
	<div class="memberList">
	</div>
	

<!--
	<div class="memberList">
		<div class="memberList_box">
			<div>
				<input type="button" value="멤버보기" onClick ="postAjax('schedule/memberList','','getMemberList')"/>
			</div>
			<div id ="memberBox">
			</div>
		</div>
	</div>
-->

</body>
</html>