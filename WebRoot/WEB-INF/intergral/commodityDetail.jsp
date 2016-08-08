<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!-- <script type="text/javascript" src="jsp/js/jquery.js"></script> -->
	<link href="jsp/css/pagination.css" rel="stylesheet" type="text/css"/>
	<link href="jsp/css/select.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="jsp/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="jsp/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="jsp/js/select-ui.min.js"></script>
	<style>
		.forminfo li label{
			width:100px
		}
		.textinput{
			width: 345px;
		    height: 80px;
		    line-height: 18px;
		    border-top: solid 1px #a7b5bc;
		    border-left: solid 1px #a7b5bc;
		    border-right: solid 1px #ced9df;
		    border-bottom: solid 1px #ced9df;
		    overflow-y : scroll;
		}
	</style>
	<script>
		function inputFile($this){
			 $this = $($this);
			 $('#input1').val($this.val());
		}
	</script>
  </head>
  
  <body>
   <div class="place">
    <span>位置：</span>
    <ul class="placeul">
	    <li><a href="doLogin">首页</a></li>
	    <li>
	    	<span style="font-weight:100 !important">
				<c:url value="/pt/doCommodityServlet" var="backMain">
					<c:param name="tag" value="query"/>
				</c:url>
	    		<a href="${backMain}">商品列表</a>
	    	</span>
	    </li>
	    <li>
	    	<span>
				<c:url value="/pt/doCommodityServlet" var="refresh">
					<c:param name="tag" value="detail"/>
					<c:param name="commodify" value="${commodity_id}"/>
				</c:url>
	    		<a href="${refresh}">商品详情</a>
	    	</span>
	    </li>
    </ul>
    </div>
    
    <div class="formbody">
    	<div class="formtitle"><span>商品详细信息</span></div>
	    <form action="<%=path %>/pt/doCommodityDetal"   id="stuForm"  method="post">
	    	<input type="hidden" name="tag" value="add" />
	    	<input type="hidden" name="commodity_id" value="result.commodity_id" />
	    	<ul class="forminfo">
	    		<li>
	    			<label>商品名称</label>
	    			<input name="commodity_name" value="${result.commodity_name}" type="text" class="dfinput" />
	    		</li>
	    		<li>
	    			<label>商品价格（积分）</label>
	    			<input name="commodity_pay" value="${result.commodity_pay}" type="text" class="dfinput" />
	    			<i>积分价格为正整数</i>
	    		</li>
	    		<li>
	    			<label>商品数量</label>
	    			<input name="commodity_num" value="${result.commodity_num}" type="text" class="dfinput" />
	    			<i>商品数量为正整数</i>
	    		</li>
	    		<li>
	    			<label>出售状态</label>
	    			<input type="radio" name="commodity_use_flag" value="0" <c:if test="${result.commodity_use_flag == '0'}">checked="checked"</c:if> />出售中
					<input type="radio" name="commodity_use_flag" value="1" <c:if test="${result.commodity_use_flag == '1'}">checked="checked"</c:if> />停售
	    		</li>
	    		<li>
	    			<label>商品类型</label>
	    			<input type="radio" name="commodity_type" value="0" <c:if test="${result.commodity_type == '0'}">checked="checked"</c:if> />实物商品
					<input type="radio" name="commodity_type" value="1" <c:if test="${result.commodity_type == '1'}">checked="checked"</c:if> />虚拟商品
	    		</li>
	    		<li>
	    			<label>商品简介</label>
	    			<textarea name="commodity_comment" class="textinput" rows="5" >${result.commodity_comment}</textarea>
	    		</li>
	        	<li><label>&nbsp;</label><input type="submit" class="btn" value="确认保存"/></li>
	      	</ul>
	    </form>
	    <form action="<%=path %>/pt/doFileUpload" id="stuForm" method="post" enctype="multipart/form-data">
	      <ul class="forminfo">
	    	<li>
	    		<label>商品图片</label>
	    		<input type="text" id="input1" class="dfinput" onclick="$('#myfile').click();" placeholder="浏览">
				<input type="submit" value="上传图片" />
				<input type="file" name="commodiy_file_url" id="myfile" onchange="inputFile(this)" style="display:none">
	    	</li>
	    	<li>
	    		<img id="commodity_url" src="${commodity_img_url}">
	    	</li>
	      </ul>
	    </form>
     </div>
  </body>
</html>
