$(document).ready(function(){
	//退出按钮
	$("#signout").click(function(){
		//并不能清除session
		sessionStorage.setItem("adminname","null");
		sessionStorage.setItem("admintype","null");
		window.open('Login.html',"_self");
	});
	
	//高度适应
	var height = $(document).height() - 62;
	$(".power-list").css("height",height + "px");
});