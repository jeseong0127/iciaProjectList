<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function sendEmailAuth(){
	let uCode = document.getElementsByName("uCode")[0];
	let aCode = document.getElementsByName("aCode")[0];
	let tid = document.getElementsByName("tid")[0];
	
	let f = document.createElement("form");
	
	f.action = "cerMailAuth";
	f.method = "post";
	
	f.appendChild(uCode);
	f.appendChild(aCode);
	f.appendChild(tid);
	
	document.body.appendChild(f);
	
	f.submit();
}

function callteCode(teamid){

	alert(teamid);
}

</script>

<body onload="callteCode('${param.tid}')">
	
	<input type="text" name="uCode"/>
	<input type="password" name="aCode"/>
	<input type="hidden" name="tid" value="${param.tid}"/>
	<input type="button" value="인증하기" onClick="sendEmailAuth()"/>
	
	
</body>
</html>