<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎登乐钱宝后台管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="jsp/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="jsp/js/jquery.js"></script>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})	
</script>


</head>

<body style="background:url(jsp/images/topbg.gif) repeat-x;">

    <div class="topleft">
    <a href="jsp/main.jsp" target="_parent"><img src="jsp/images/loginlogo.png" title="系统首页" /></a>
    </div>
   
    <div class="topright">    
    <ul>
    <li><span><img src="jsp/images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    <li><a href="/lichpt/doLogout" target="_parent">退出</a></li>
    </ul>
    <div class="user">
    <span>欢迎用户：${sessionScope.SysManager.mgr_loginid}</span>
   
    </div>    
    
    </div>
    
  </body>
</html>
