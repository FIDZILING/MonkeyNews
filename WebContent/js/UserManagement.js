window.onload = function(){
	var url="/../MonkeyNews/usermanagement.do?method=findalluser";
	var params = "";
	sendRequest(url,params,'POST',showres);
}

function callback(){
	var url="/../MonkeyNews/usermanagement.do?method=findalluser";
	var params = "";
	sendRequest(url,params,'POST',showres);
}

function showres(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			var html = "<tr>"
			+"<th>用户Id</th>"
			+"<th>用户名称</th>"
			+"<th>用户邮箱</th>" 
			+"<th>用户手机号</th>" 
			+"<th>用户状态</th>"
			+"<th>删除用户</th>"
			+"</tr>"
			+ info;
			document.getElementById("user-list").innerHTML = html;
		}
	}
}

$(document).ready(function(){
	$("#input-find-user").focus(function(){
		$("#input-find-user").val("");
		$("#input-find-user").css("color","black");
	});
	
	$(".img-find").click(function(){
		finduser();
	});
	
	//回车响应
	$(document).on("keydown",function (event) {
        var e = event || window.event ||arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){
        	$(".img-find").click();
          }
    });
});

function finduser(){
	var url="/../MonkeyNews/usermanagement.do?method=finduser";
	var Id = $("#input-find-user").val();
	var params = "Id="+Id;
	sendRequest(url,params,'POST',showresoffinduser);
}

function showresoffinduser(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			var html = "<tr>"
			+"<th>用户Id</th>"
			+"<th>用户名称</th>"
			+"<th>用户邮箱</th>" 
			+"<th>用户手机号</th>" 
			+"<th>用户状态</th>"
			+"<th>删除用户</th>"
			+ info;
			document.getElementById("user-list").innerHTML = html;
		}
	}
}
function change(){
	var status = document.activeElement.value;
	var Id = document.activeElement.parentNode.parentNode.children[0].innerHTML;
	var url="/../MonkeyNews/usermanagement.do?method=upuserstatus";
	var params = "Id="+Id+"&status="+status;
	sendRequest(url,params,'POST',showresofupdate);
}
function showresofupdate(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			alert(info);
		}
	}
}

function deleteuser(e){
	var Id = e.parentNode.children[1].innerHTML;
	var url="/../MonkeyNews/usermanagement.do?method=deleteuser";
	var params = "Id="+Id;
	sendRequest(url,params,'POST',showresofdel);
}
function showresofdel(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			alert(info);
			//location.reload();
			callback();
		}
	}
}