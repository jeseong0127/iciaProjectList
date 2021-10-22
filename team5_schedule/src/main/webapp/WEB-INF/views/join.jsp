<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
  <script type="text/javascript" src="resources/js/js.js">
  </script>
  <script>
  /* 아이디 중복 검사 */
  function dupCheck(obj){
  	let uCode = document.getElementsByName("uCode")[0];
  	
  	if(obj.value != "재입력"){
  		// 아이디 유효성 검사
  		if(!isValidateCheck(1, uCode.value)){
  			uCode.value = "";
  			uCode.focus();
  			return;
  		}
  		
  		/* AJAX */
  		dupPostAjax("IsDup","uCode="+uCode.value,"afterDupCheck");
  		
  	}else{
  		uCode.value = "";
  		uCode.readOnly = false;
  		uCode.focus();
  		obj.value = "중복검사";	
  	}
  }
  
  function afterDupCheck(jsonData){
	  let btn = document.getElementsByName("dupCheck")[0];
	  let uCode = document.getElementsByName("uCode")[0];
	  let msg = document.getElementById("message");
	  
	  alert(jsonData);
	  
	  //사용가능
	  if(jsonData == true){
		 uCode.setAttribute("readonly",true);
		 btn.setAttribute("value", "재입력");
		 //msg.setAttribute("innerText","사용가능");
		 msg.innerText = "사용가능";
         
	  // 사용불가
	  }else{
		  //uCode.setAttribute("value",""); //
		  uCode.value ="";
		  //msg.setAttribute("innerText","사용불가"); //
		  msg.innerText = "사용불가";
		  uCode.focus();
	  }
  }
  
  
  
  </script>
  </head>
  <link rel="stylesheet" href="resources/css/join.css" type="text/css">
  <!-- <body onload = "callMessage('${message}')"> -->
  <body>
    <div class="all_box">
    <div class="box">
    <div class="title_box">
        <h3 class="title_text">join</h3><br>
    </div>
    <div class="input_box">

      <div>
      <label class="input_title">아이디</label>
      <input class="input_text" type="text" name="uCode" placeholder="아이디를 입력해주세요."
          onfocus="this.placeholder=''" onblur="this.placeholder='아이디를 입력해주세요.'">
      </diV><br>

      <div>
      <div id ="message">---</div>
      <div><input type="button" name="dupCheck" value="중복체크" onClick="dupCheck(this)"></div>
      </div>

      <div>
      <label class="input_title">비밀번호</label>
      <input class="input_text" type="password" name="aCode" placeholder="비밀번호를 입력해주세요."
          onfocus="this.placeholder=''" onblur="this.placeholder='비밀번호를 입력해주세요.'"
          onfocusout="pw_check()">
      </div><br>
			
			<span name = "pw_text"></span>
			
          <div>
          <label class="input_title">재입력</label>
          <input class="input_text" type="password" name="aCode" placeholder="비밀번호를 입력해주세요."
              onfocus="this.placeholder=''" onblur="this.placeholder='비밀번호를 입력해주세요.'"
              onfocusout="pw_check2()">
          </div><br>
			
			<span name = "pw_text2"></span>
			
          <div>
              <label class="input_title">이름</label>
              <input class="input_text" type="text" name="uName" placeholder="이름을 입력해주세요."
                  onfocus="this.placeholder=''" onblur="this.placeholder='이름을 입력해주세요.'">
          </div><br>

          <div>
                  <label class="input_title">메일</label>
                  <input class="input_text" type="text" name="uMail" placeholder="메일을 입력해주세요."
                      onfocus="this.placeholder=''" onblur="this.placeholder='메일을 입력해주세요.'">
          </div><br>
		  
		  <div>프로필<input type="file" name="file1"/>
		  
		  </div>
			
      <input class="input_btn"type="button" name="" value="JOIN" onClick="sendJoinInfo()">
    </div>
    </div>
  </div>
  </body>
</html>
