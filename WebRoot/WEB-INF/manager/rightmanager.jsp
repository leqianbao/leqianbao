<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>博时轩后台管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link href="jsp/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jsp/js/jquery.js"></script>
<script type="text/javascript" src="jsp/js/select-ui.min.js"></script>
<script type="text/javascript" src="jsp/editor/kindeditor.js"></script>
<script type="text/javascript">
    KE.show({
        id : 'content7',
        cssPath : './index.css'
    });
  </script>
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
     <form action="<%=path %>/pt/doManager"   id="stuForm"  method="post">
    <input type="hidden" name="tag" value="add" />
    <ul class="forminfo">
    <li><label>登录账号</label><input name="mgr_loginid" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>登录密码</label><input name="mgr_passwd" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>确认密码</label><input name="mgr_passwd" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>姓名</label><input name="mgr_name" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>性别</label><input name="mgr_sex" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>联系电话</label><input name="mgr_phone" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>管理员地址</label><input name="mgr_address" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>&nbsp;</label><input type="submit" class="btn" value="确认保存"/></li>
    </ul>
    
    </form>
    </div>
  </body>
</html>
