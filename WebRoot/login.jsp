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
	<link href="jsp/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript" src="jsp/js/jquery.js"></script>
<script type="text/javascript" src="jsp/js/cloud.js"></script>

<script language="javascript">
	$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
});  
	
	if (top != window)   
		top.location.href = window.location.href;   
	if( window.parent != window ){
	    window.parent.location.href = window.location.href;
	}
	function changeCode(){
		document.getElementById("imgCode").src="<%=path%>/getCode?test="+Math.random();
	}
	function checkAll(){
		var uname=document.getElementById("uname").value;
		var upass=document.getElementById("upass").value;
		var code=document.getElementById("ucode").value;
		
		return true;	
	}
</script> 
	
  </head>
  
<body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">
    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>   
    <div class="loginbody"> 
    <span class="systemlogo"></span> 
       
    <div class="loginbox">
    <form method="post" action="<%=path%>/doLogin"  onsubmit="return checkAll()">
    <ul>
    <li><input name="uname" id="uname" type="text" class="loginuser" value="用户名" onclick="JavaScript:this.value=''"/></li>
    <li><input name="upass" id="upass" type="password" class="loginpwd" value="" onclick="JavaScript:this.value=''"/></li>
    <li><input name="ucode" id="ucode"type="text" class="verificationcode" value="验证码" onclick="JavaScript:this.value=''"/>
    <a class="imgcode" href="javascript:changeCode();"><img id="imgCode" src="<%=path %>/getCode"></a>
   </li>
    
    <li><input name="" type="submit" class="loginbtn" value="登录"   /><label style="color:red">${requestScope.codeError}</label>
   <!--  <label><input name="" type="checkbox" value="" checked="checked" onclick="this.form.submit()"  />记住密码</label>
    <label><a href="#">忘记密码？</a></label> -->
    </li>
    </ul>
    </form>
    </div>
    </div>
 
    <div class="loginbm">版权所有  2015  <a href="http://www.boshixuan.cn">杭州轹城信息技术有限公司</a> 地址：拱墅区潮王路238号六层608室（托管516）  电话：400-859-7333 </div>
    
  </body>
</html>
