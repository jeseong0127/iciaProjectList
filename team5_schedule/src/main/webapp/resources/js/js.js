/*
function sendLoginInfo(){
	const user_id = document.getElementsByName("user_id")[0];
	const user_pw = document.getElementsByName("user_pw")[0];
	
	let form = makeForm("login","post");
	form.appendChild(user_id);
	form.appendChild(user_pw);
	
	document.body.appendChild(form);
	
	form.submit();
}
*/
let publicIp;

function sendLoginInfo(){
	
	/*
	alert(publicIp);
	alert(location.host);
	return;
	*/
	
	let uCode = document.getElementsByName("aCode")[0];
	let aCode = document.getElementsByName("uCode")[0];
	
	let method = makeInput("hidden","method",1);
	let pubIp = makeInput("hidden","publicIp",publicIp);
	let privateIp = makeInput("hidden","privateIp",location.host);
	
	let form = makeForm("login","post");
	
	let input = makeInput("hidden","info",20);
	let input2 = makeInput("hidden","info","jeseong");
	let date = document.getElementsByName("day")[0];
	
	form.appendChild(uCode);
	form.appendChild(aCode);
	
	form.appendChild(input);
	form.appendChild(input2);
	
	form.appendChild(method);
	form.appendChild(pubIp);
	form.appendChild(privateIp);
	
	
	document.body.appendChild(form);
	
	form.submit();
}

function sendJoinInfo(){
	let uCode = document.getElementsByName("uCode")[0];
	let aCode = document.getElementsByName("aCode")[0];
	let uName = document.getElementsByName("uName")[0];
	let uMail = document.getElementsByName("uMail")[0];
	
	let file1 = document.getElementsByName("file1")[0];
	
	let form = makeForm("join","post");
	
	
	form.appendChild(file1);
	
	form.appendChild(uCode);
	form.appendChild(aCode);
	form.appendChild(uName);
	form.appendChild(uMail);
	
	document.body.appendChild(form);
	
	form.submit();
}

/* 유효성 검사 */
  function isValidateCheck(type, word){
    	let result;
    	const codeComp = /^[a-z|A-Z]{1}[a-z|0-9]{7,11}$/g;
    	const pwdComp1  = /[a-z]/g;
    	const pwdComp2  = /[A-Z]/g;
    	const pwdComp3 = /[0-9]/g;
    	const pwdComp4  = /[!@#$%^&*]/g;
    	
    	if(type == 1){
    		result = codeComp.test(word); 
    	}else if(type == 2){
    		let count = 0;
			count += pwdComp1.test(word)? 1:0;
			count += pwdComp2.test(word)? 1:0;
			count += pwdComp3.test(word)? 1:0;
			count += pwdComp4.test(word)? 1:0;
			
    		result = (count >= 3)? true:false;
    	}
    	return result;
    }


function makeForm(action,method,name = null){
	let form = document.createElement("form");
	
	if(name != null){form.setAttribute("name",name);}
	form.setAttribute("action",action);
	form.setAttribute("method",method);
	form.setAttribute("enctype","multipart/form-data");
	
	return form;
}


function makeInput(type,name,value){
	let input = document.createElement("input");
	
	input.setAttribute("type",type);
	input.setAttribute("name",name);
	input.setAttribute("value",value);
	
	return input;
}

function getAjax(jobCode,clientData,fn){
	
	console.log(jobCode);
	console.log(clientData);
	console.log(fn);
	
	//step1 객체 생성
	let ajax = new XMLHttpRequest();
	
	//step2 상태 설정
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			window[fn](JSON.parse(ajax.responseText));
		}
	};
	
	//step3
	if(clientData != ""){ jobCode += "?" + clientData;}
	ajax.open("GET",jobCode);
	
	//step4
	ajax.send();
}

function postAjax(jobCode,clientData,fn,count){
	//step1
	let ajax = new XMLHttpRequest();
	
	
	//step2
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			
			window[fn](JSON.parse(ajax.responseText),count);
		}
	};
	
	//step3
	ajax.open("POST",jobCode);
	
	//step4
	//ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.setRequestHeader("Content-type", "application/json");
	ajax.send(clientData);
}

function postAjaxTeam(jobCode,clientData,fn){
	//step1
	let ajax = new XMLHttpRequest();
	
	
	//step2
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			
			window[fn](JSON.parse(ajax.responseText));
		}
	};
	
	//step3
	ajax.open("POST",jobCode);
	
	//step4
	//ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.setRequestHeader("Content-type", "application/json");
	ajax.send(clientData);
}

function dupPostAjax(jobCode,clientData,fn){
	//step1
	let ajax = new XMLHttpRequest();
	
	
	//step2
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			
			window[fn](JSON.parse(ajax.responseText));
		}
	};
	
	//step3
	ajax.open("POST",jobCode);
	
	//step4
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	//ajax.setRequestHeader("Content-type", "application/json");
	ajax.send(clientData);
}



function getPublicIp(data){
	publicIp = data.ip;
}


function korCheck(obj, event){
	const pattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	
	if(pattern.test(event.target.value.trim())) {
		obj.value = obj.value.replace(pattern,'').trim();
	}
}

function id_check(){
	  let uCode = document.getElementsByName("uCode")[0];
	  let uText = document.getElementById("message")[0];
	  
	if(!isValidateCheck(1,uCode.value)){
		uText.value = "아이디 형식에 맞게 다시입력해주세요";
		uCode.value = "";
		uCode.focus();
	}
	  
  }

function pw_check(){
	  let aCode = document.getElementsByName("aCode")[0];
	  let aText = document.getElementsByName("pw_text")[0];
	  
	  if(!isValidateCheck(2,aCode.value)){
		aText.innerText = "비밀번호 형식에 맞게 다시입력해주세요";
	   }else{
		aText.innerText = "비밀번호 형식에 맞습니다";
	}	  

  }

function pw_check2(){
	  let aCode = document.getElementsByName("aCode");
	  let aText = document.getElementsByName("pw_text2")[0];
	  
	  if(aCode[0].value == aCode[1].value){
		  aText.innerText = "비밀번호가 같습니다";
	  }else{
		aText.innerText = "비밀번호가 다릅니다";
	}
	  
  }


function logOut(id){
	let uCode = makeInput("hidden","uCode",id);
	let method = makeInput("hidden","method",-1);
	let pubIp = makeInput("hidden","publicIp",publicIp);
	let privateIp = makeInput("hidden","privateIp",location.host);
	
	
	let form = makeForm("logOut","post");
	
	form.appendChild(uCode);
	form.appendChild(method);
	form.appendChild(pubIp);
	form.appendChild(privateIp);
	
	document.body.appendChild(form);
	
	form.submit();
}


function getBrowserType(){
	let agt = navigator.userAgent;
	
	alert(agt);
}

