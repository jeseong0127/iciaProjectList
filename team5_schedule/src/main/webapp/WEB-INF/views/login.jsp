<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
  </head>
<link rel="stylesheet" href="resources/css/login.css" type="text/css">
<script type="text/javascript" src="resources/js/js.js">
</script>
<script>
	const message ="${message}";
	if(message != ""){
		alert(message);
	}
</script>
  <body onLoad="getAjax('https://api.ipify.org','format=json', 'getPublicIp')">
    <div class="all_box">
    <div class="box">
    <div class="title_box">
        <h3 class="title_text">Log in</h3><br>
        <a href="joinForm">회원이 아니신가요?</a>
    </div>
    <div class="input_box">
      <label class="input_title">아이디</label>
      <input class="input_text" type="text" name="uCode" placeholder="아이디를 입력해주세요."
          onfocus="this.placeholder=''" onblur="this.placeholder='아이디를 입력해주세요.'"><br><br>
      <label class="input_title">비밀번호</label>
      <input class="input_text" type="password" name="aCode" placeholder="비밀번호를 입력해주세요."
          onfocus="this.placeholder=''" onblur="this.placeholder='비밀번호를 입력해주세요.'">
      <div class="pw_search">
          비밀번호 찾기
      </div>
      <input class="input_btn"type="button" onClick="sendLoginInfo()" value="LOG IN">
    </div>
    </div>
  </div>
  <input type="button" onClick="getBrowserType()" value="browser"/>
  </body>
</html>
