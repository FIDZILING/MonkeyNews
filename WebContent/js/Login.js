$(document).ready(function(){
	//账号框输入响应
	$("#adminname").focus(function(){
		$("#resultoflogin").text("");
		$("#adminname").css({
			"color":"black",
			"border":"blue solid 1px"
		});
		if($("#adminname").val() == "账号" || $("#adminname").val() == "请输入正确的用户名"){
			$("#adminname").val("");
	}
	});
	//账号框移开响应
	$("#adminname").blur(function(){
		if($("#adminname").val() == ""){
			$("#adminname").css({
				"color":"red",
				"border":"red solid 1px"
			});
			$("#adminname").val("请输入正确的用户名");
		}
		else{
			$("#adminname").css("border","#aaa solid 1px");
		}
	});
  
	//密码框输入响应
	$("#adminpass").focus(function(){
		$("#resultoflogin").text("");
		$("#adminpass").css({
			"color":"black",
			"border":"blue solid 1px"
		});
		$("#adminpass").attr('type','password');
		if($("#adminpass").val() == "密码" || $("#adminpass").val() == "请输入正确的密码"){
			$("#adminpass").val("");
	}
	});
	//密码框移开响应
	$("#adminpass").blur(function(){
		if($("#adminpass").val() == ""){
			$("#adminpass").css({
				"color":"red",
				"border":"red solid 1px"
			});
			$("#adminpass").val("请输入正确的密码");
			$("#adminpass").attr('type','text');
		}
		else{
			$("#adminpass").css("border","#aaa solid 1px");
		}
	});
	
	//登陆按钮
	$("#adminlogin").click(function(){
		login();
	});
	
	//响应回车
	$(document).on("keydown",function (event) {
        var e = event || window.event ||arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){
        	$("#adminlogin").click();
          }
    });
});

//登录按钮ajax
function login(){
	var url="/../MonkeyNews/admin.do?method=login";
	var adminname = $("#adminname").val();
	var adminpass = $("#adminpass").val();
	var params = "adminname="+adminname+"&adminpass="+adminpass;
	sendRequest(url,params,'POST',showresoflogin);
}
function showresoflogin(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			if(info == "登录失败！"){
				$("#resultoflogin").text(info);
			}
			if(info == "管理员管理"){
				window.open('Setting.jsp',"_self");
			}
			else if(info == "用户管理"){
				window.open('UserManagement.jsp',"_self");
			}
			if(info == "新闻管理"){
				window.open('CommentCheck.jsp',"_self");
			}
		}
	}
}
