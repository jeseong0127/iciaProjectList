<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MHDP Full Calendar</title>
    <link rel="stylesheet" href="resources/css/style.css">

<script type="text/javascript" src="resources/js/js.js"></script>
<script type="text/javascript">
let count = 0;
function picture(){
	let div = document.getElementById("pictureBox");
	let sendDiv = document.getElementById("pictureBoxbtn");
	
	if(count < 5){
		div.innerHTML += "<div class='pictureAddBox'><input multiple='multiple' type='file' name='files' value='찾아보기'/><input type='button' value='삭제' onClick ='pictureBoxRemove(this)'/></div>";
	}
	
	if(count < 5){
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
	let date = document.getElementsByName("date")[0];
	let location = document.getElementsByName("location")[0];
	let contents = document.getElementsByName("contents")[0];
	let open = document.getElementsByName("open")[0];
	let loop = document.getElementsByName("loop")[0];
	let files = document.getElementsByName("files");
	
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
<body onload="getTeamList('${uCode}')">
	
	<div id="main">
	
	<div>
		
		<div id="getTeamList">
		</div>
		
		<input type="date" name = "date"/>
		
		<div>
			제목 : <input type="text" name="title" />
		</div>
		
		<div>
			장소 : <input type="text" name="location" />
		</div>
		
		<div>
			내용 : <textarea rows="" cols="" name="contents"></textarea>
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
		
		<!--
			<div id ="testPictures">
				<img src="resources/images/${image1}" style="height:100px; width:200px;"/>
				<img src="resources/images/${image2}" style="height:100px; width:200px;"/>
				<img src="resources/images/${image3}" style="height:100px; width:200px;"/>
				<img src="resources/images/${image4}" style="height:100px; width:200px;"/>
				<img src="resources/images/${image5}" style="height:100px; width:200px;"/>
			</div>
		-->
	</div>
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
    
    <script type="text/javascript" src="resources/js/calendar.js"></script>
</body>
</html>
