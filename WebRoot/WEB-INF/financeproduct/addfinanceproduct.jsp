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
     <form action="<%=path %>/pt/doFinanceProduct"   id="stuForm"  method="post">
      <input type="hidden" name="tag" value="add" />
      <input type="hidden" name="interest_img" value="/image/days.png" />
    <ul class="forminfo">
    <li><label>年化利率</label><input name="interest_rate" type="text" class="dfinput" /><i>年化利率为%多少，如：4.35，11.5</i></li>
    <li><label>理财天数</label><input name="interest_days" type="text" class="dfinput" /><i>理财天数为，30 60 90 180 </i></li>
    <li><label>理财月数</label><input name="interest_months" type="text" class="dfinput" /><i>月数天数/30,每30天按一个月算</i></li>
    <li><label>起投金额</label><input name="interest_money" type="text" class="dfinput" /><i>起投金额为整十整百的正整数</i></li>
    <li><label>理财产品名称</label><input name="product_name" type="text" class="dfinput" /><i>产品的名称</i></li>
    <li><label>理财产品描述</label><input name="product_desc" type="text" value="七日年化收益" class="dfinput" /><i>默认为：七日年化收益</i></li>
      <li><label>&nbsp;</label><input type="submit" class="btn" value="确认保存"/></li>
    </ul>
    
    </form>
    
    </div>
  </body>
</html>
