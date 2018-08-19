window.onload = function() {
	var url = "/../MonkeyNews/sensitive.do?method=findallsensitive";
	var params = "";
	sendRequest(url, params, 'POST', showres);
}

function reload() {
	var url = "/../MonkeyNews/sensitive.do?method=findallsensitive";
	var params = "";
	sendRequest(url, params, 'POST', showres);
}

function showres() {
	if (xmlHttpRequest.readyState == 4) {
		if (xmlHttpRequest.status == 200) {
			var info = xmlHttpRequest.responseText;
			document.getElementById("sensitive-list").innerHTML = info;
			// 查完敏感词查评论举报
			var url = "/../MonkeyNews/report.do?method=findallreport";
			var params = "";
			sendRequest(url, params, 'POST', showresofreport);
		}
	}
}

function showresofreport() {
	if (xmlHttpRequest.readyState == 4) {
		if (xmlHttpRequest.status == 200) {
			var info = xmlHttpRequest.responseText;
			var html = "<tr>"
					+ "<th><input id='checkboxmain' class='checkboxmain' type='checkbox' name='allin' onclick='checkall()'/>全选</th>"
					+ "<th>处理Id</th>"
					+ "<th>被举报用户Id</th>" + "<th>被举报用户名</th>" 
					+ "<th>被举报用户状态</th>" + "<th>评论内容</th>"
					+ "<th>举报理由</th>" + info;
			document.getElementById("report-list").innerHTML = html;

		}
	}
}

function inputsensitiveword(e) {
	var words = e.parentNode.children[0].value;
	if (words != "") {
		var url = "/../MonkeyNews/sensitive.do?method=addsensitive";
		var params = "words=" + words;
		sendRequest(url, params, 'POST', showresofaddsensitive);
	}
}

function showresofaddsensitive() {
	if (xmlHttpRequest.readyState == 4) {
		if (xmlHttpRequest.status == 200) {
			var info = xmlHttpRequest.responseText;
			if (info == "添加成功") {
				reload();
			} else {
				alert();
			}
		}
	}
}

function delsensitiveword(e) {
	var words = e.parentNode.children[0].innerHTML;
	var url = "/../MonkeyNews/sensitive.do?method=delsensitive";
	var params = "words=" + words;
	sendRequest(url, params, 'POST', showresofdelsensitive);
}

function showresofdelsensitive() {
	if (xmlHttpRequest.readyState == 4) {
		if (xmlHttpRequest.status == 200) {
			var info = xmlHttpRequest.responseText;
			console.log(info);
			reload();
		}
	}
}

function checkall() {
	var main = document.getElementById('checkboxmain');
	var each = document.getElementsByName('check');
	if (main.checked == true) {// 因为获得的是数组，所以要循环 为每一个checked赋值
		for (var i = 0; i < each.length; i++) {
			each[i].checked = true;
		}

	} else {
		for (var j = 0; j < each.length; j++) {
			each[j].checked = false;
		}
	}

}

function submiteachtypeofdoreport() {
	var dotypeall = document.getElementsByName('do-type');
	var dotype;
	for (i = 0; i < dotypeall.length; i++) {
		if (dotypeall[i].checked) {
			dotype = dotypeall[i].value;
		}
	}
	switch (dotype) {
	case "donothing":
		window.location.reload();
		break;
	case "nospeak":
		var each = document.getElementsByName('check');
		for (var i = 0; i < each.length; i++) {
			if (each[i].checked == true) {
				// 禁言这一条的用户
				var Id = each[i].parentNode.parentNode.children[2].innerHTML;
				var url = "/../MonkeyNews/usermanagement.do?method=upuserstatus";
				var params = "Id=" + Id +"&status=禁言";
				sendRequest(url, params, 'POST', showresofnospeak);
			}
			if (each[i].checked == true) {
				var Id = each[i].parentNode.parentNode.children[1].innerHTML;
				var url = "/../MonkeyNews/report.do?method=delreport";
				var params = "Id=" + Id;
				sendRequest(url, params, 'POST', showresofdelete);
			}
		}
		break;
		
	case "userout":
		var each = document.getElementsByName('check');
		for (var i = 0; i < each.length; i++) {
			if (each[i].checked == true) {
				var Id = each[i].parentNode.parentNode.children[2].innerHTML;
				var url = "/../MonkeyNews/usermanagement.do?method=upuserstatus";
				var params = "Id=" + Id +"&status=封号";
				sendRequest(url, params, 'POST', showresofuserout);
			}
			if (each[i].checked == true) {
				var Id = each[i].parentNode.parentNode.children[1].innerHTML;
				var url = "/../MonkeyNews/report.do?method=delreport";
				var params = "Id=" + Id;
				sendRequest(url, params, 'POST', showresofdelete);
			}
		}
		break;

	case "delete":
		var each = document.getElementsByName('check');
		for (var i = 0; i < each.length; i++) {
			if (each[i].checked == true) {
				var Id = each[i].parentNode.parentNode.children[1].innerHTML;
				var url = "/../MonkeyNews/report.do?method=delreport";
				var params = "Id=" + Id;
				sendRequest(url, params, 'POST', showresofdelete);
			}
		}
		break;
	}
	reload();
}

function showresofnospeak(){
	if (xmlHttpRequest.readyState == 4) {
		if (xmlHttpRequest.status == 200) {
			submiteachtypeofdoreport();
		}
	}
}

function showresofuserout(){
	if (xmlHttpRequest.readyState == 4) {
		if (xmlHttpRequest.status == 200) {
			submiteachtypeofdoreport();
		}
	}
}

function showresofdelete(){
	if (xmlHttpRequest.readyState == 4) {
		if (xmlHttpRequest.status == 200) {	
			submiteachtypeofdoreport();
		}
	}
}

