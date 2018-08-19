<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,target-densitydpi=high-dpi,initial-scale=0.3, minimum-scale=0.3, maximum-scale=0.3"/>
	<title>NewsManagement</title>
	<link rel="stylesheet" type="text/css" href="../css/common.css" />
	<link rel="stylesheet" type="text/css" href="../css/NewsManagement.css" />
	<link href="../img/logo.ico" rel="shortcut icon">
</head>
<body>
	<!-- 头部logo -->
    <div class="header">
        <div class="logo-box">
            <a href="CommentCheck.jsp">
            	<img src="../img/logo.png" class="img-logo">
            	<font class="text-main">猿闻后台管理</font>
            </a>
            <div class="welcome">
            	欢迎<font style="color:gold;"><%=request.getSession().getAttribute("adminname")%></font>，您的身份是<%=request.getSession().getAttribute("admintype")%>
        	</div>
        </div>
    </div>
    <!-- 中间内容 -->
    <div class="wrapper">
    	<div class="power-list">
    		<ul>
	    		<a href="CommentCheck.jsp"><li id="comment-do">评论审核</li></a>
	    		<a href="UserManagement.jsp"><li id="user-do">用户管理</li></a>
	    		<a href="NewsManagement.jsp"><li id="news-do">文章管理</li></a>
	    		<a href="Setting.jsp"><li  id="admin-do">管理员设置</li></a>
	    		<li id="signout">退出管理</li>
    		</ul>
    	</div>
    	<div class="main-box">
    		<h2 class="main-title">文章管理</h2>
    		<div class="findnews">
    			<input id="input-find-news" type="text" class="input-find-news" value="搜索文章Id" name="findnews" />
    			<img class="img-find" src="../img/find.png">
    		</div>
    		<div class="news-list">
    			<table id="news-list">
				<!-- 新闻列表后台获取 -->
				</table>
    		</div>
    	</div>
    </div>
    <!-- 底部 -->
    <div class="footer">
        <!-- 该界面暂时不需要用到footer -->
    </div>
    <script src="../js/jquery.min.js"></script>
    <script src="../js/NewsManagement.js"></script>
    <script src="../js/common.js"></script>
    <script src="../js/ajax.js"></script>
</body>
</html>