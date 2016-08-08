<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>信息管理系统界面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link href="jsp/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jsp/js/jquery.js"></script>

  </head>
  
  <body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">表单</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>基本信息</span></div>
     <form action="<%=path %>/pt/doPutinMoney"   id="stuForm"  method="post">
      <input type="hidden" name="tag" value="add" />
    <ul class="forminfo">
    <li><label>充值金额</label><input name="putin_money" type="text" class="dfinput" /><i>充值金额为正整数</i></li>
    <li><label>充值描述</label><input name="putin_desc" type="text" class="dfinput" /><i>充值金额的描述信息</i></li>
    <li><label>用户ID</label><input name="user_id" type="text" class="dfinput" /><i>用户的ID，可到用户列表里查看</i></li>
      <li><label>&nbsp;</label><input type="submit" class="btn" value="确认保存"/></li>
    </ul>
    
    </form>
    
    </div>
  </body>
</html>
