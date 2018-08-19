window.onload = function(){
	var url="/../MonkeyNews/admin.do?method=findalladmin";
	var params = "";
	sendRequest(url,params,'POST',showres);
}
function callback(){
	var url="/../MonkeyNews/admin.do?method=findalladmin";
	var params = "";
	sendRequest(url,params,'POST',showres);
}
function showres(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			var html = "<tr>"
			+"<th>管理员Id</th>"
			+"<th>管理员名称</th>"
			+"<th>管理员类型</th>" 
			+ info;
			document.getElementById("admin-list").innerHTML = html;
		}
	}
}

$(document).ready(function(){
	$("#input-find-admin").focus(function(){
		$("#input-find-admin").val("");
		$("#input-find-admin").css("color","black");
	});
	
	$(".img-find").click(function(){
		find();
	});
	
	//回车响应
	$(document).on("keydown",function (event) {
        var e = event || window.event ||arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){
        	$(".img-find").click();
          }
    });
	
	
});

function find(){
	var Id = $("#input-find-admin").val();
	var url="/../MonkeyNews/admin.do?method=findadmin";
	var params = "Id="+Id;
	sendRequest(url,params,'POST',showresoffind);
}
function showresoffind(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			var html = "<tr>"
				+"<th>管理员Id</th>"
				+"<th>管理员名称</th>"
				+"<th>管理员类型</th>" 
				+ info;
				document.getElementById("admin-list").innerHTML = html;
		}
	}
}

function change(){
	var admintype = document.activeElement.value;
	var adminId = document.activeElement.parentNode.parentNode.children[0].innerHTML;
	var url="/../MonkeyNews/admin.do?method=upadmintype";
	var params = "Id="+adminId+"&admintype="+admintype;
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

function inputadmin(e) {
	var adminname = e.parentNode.parentNode.children[1].children[0].value;
	var adminpass = e.parentNode.parentNode.children[1].children[2].value;
	var admintype = e.parentNode.parentNode.children[2].children[0].value;
	if(adminname!=null&&adminpass!=null){
		var url="/../MonkeyNews/admin.do?method=insertadmin";
		var params = "adminname="+adminname+"&adminpass="+adminpass+"&admintype="+admintype;
		sendRequest(url,params,'POST',showresofinsert);
	}
}
function showresofinsert(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			console.log(info);
			callback();
		}
	}
}

function deleteadmin(e){
	var Id = e.parentNode.parentNode.children[0].innerHTML;
	var url="/../MonkeyNews/admin.do?method=deladmin";
	var params = "Id="+Id;
	sendRequest(url,params,'POST',showresofdel);
}

function showresofdel(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			alert(info);
			callback();
		}
	}
}