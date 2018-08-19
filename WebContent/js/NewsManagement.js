window.onload = function(){
	var url="/../MonkeyNews/news.do?method=findallnews";
	var params = "";
	sendRequest(url,params,'POST',showres);
}

function callback(){
	var url="/../MonkeyNews/news.do?method=findallnews";
	var params = "";
	sendRequest(url,params,'POST',showres);
}

function showres(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			var html = "<tr>"
				+"<th>新闻Id</th>"
				+"<th>新闻名称</th>"
				+"<th>浏览数量</th>" 
				+"<th>点赞数量</th>" 
				+"<th>收藏数量</th>"
				+"<th>评论数量</th>"
				+"<th>删除新闻</th>"
				+ info;
			document.getElementById("news-list").innerHTML = html;
		}
	}
}

$(document).ready(function(){
	$("#input-find-news").focus(function(){
		$("#input-find-news").val("");
		$("#input-find-news").css("color","black");
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
	var url="/../MonkeyNews/news.do?method=findnews";
	var newsId = $("#input-find-news").val();
	var params = "newsId="+newsId;
	sendRequest(url,params,'POST',showresoffindnews);
}

function showresoffindnews(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			var html = "<tr>"
				+"<th>新闻Id</th>"
				+"<th>新闻名称</th>"
				+"<th>浏览数量</th>" 
				+"<th>点赞数量</th>" 
				+"<th>收藏数量</th>"
				+"<th>评论数量</th>"
				+"<th>删除</th>"
				+ info;
			document.getElementById("news-list").innerHTML = html;
		}
	}
}

function mouseovername(e){
	$(e).css({
		"color":"red",
		"cursor":"pointer"
	});
}

function mouseoutname(e){
	$(e).css({
		"color":"black"
	});
}

function findoneinfo(e){
	var newsId = e.parentNode.children[0].innerHTML;
	var url="/../MonkeyNews/news.do?method=findnews";
	var params = "newsId="+newsId;
	sendRequest(url,params,'POST',showresoffindnews);
}

function deletenews(e){
	var newsId = e.parentNode.parentNode.children[0].innerHTML;
	var url="/../MonkeyNews/news.do?method=delnews";
	var params = "newsId="+newsId;
	sendRequest(url,params,'POST',showresofdel);
}

function showresofdel(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			alert(info);
			//location.reload();
			//这个方法太low了
			callback();
		}
	}
}

function deletecomment(e,el){
	var Id=e;
	var newsId = el.parentNode.parentNode.parentNode.children[1].children[0].innerHTML;
	var url="/../MonkeyNews/news.do?method=delcomment";
	var params = "Id="+ Id+"&newsId="+ newsId;
	sendRequest(url,params,'POST',showresofdelcomment);
}

function showresofdelcomment(){
	if(xmlHttpRequest.readyState == 4){
		if(xmlHttpRequest.status == 200){
			var info=xmlHttpRequest.responseText;
			if(info != null){
				alert("success");
				findoneinfobyId(info);
			}
			else{
				alert("failed");
			}
		}
	}
}

function findoneinfobyId(newsId){
	var url="/../MonkeyNews/news.do?method=findnews";
	var params = "newsId="+newsId;
	sendRequest(url,params,'POST',showresoffindnews);
}



