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
				<c:url value="/pt/doUserOrderServlet" var="backMain">
					<c:param name="tag" value="query"/>
				</c:url>
	    		<a href="${backMain}">订单列表</a>
	    	</span>
	    </li>
	    <li>
	    	<span>
				<c:url value="/pt/doUserOrderDetailServlet" var="refresh">
					<c:param name="tag" value="detail"/>
					<c:param name="userOrder" value="${user_order_id}"/>
				</c:url>
	    		<a href="${refresh}">订单详情</a>
	    	</span>
	    </li>
    </ul>
    </div>
    
    <div class="formbody">
    	<div class="formtitle"><span>订单详细信息</span></div>
	    <form action="<%=path %>/pt/doUserOrderDetail"   id="stuForm"  method="post">
	    	<input type="hidden" name="tag" value="save" />
	    	<input type="hidden" name="id" value="${result.user_order_id}" />
	    	<ul class="forminfo">
	    		<li>
	    			<label>订单号</label>
	    			<input class="disable-input" name="order_no" disabled="disabled" value="${result.order_no}" type="text"/>
	    		</li>
	    		<li>
	    			<label>创建时间</label>
	    			<input class="disable-input" name="create_date" disabled="disabled" value="${result.create_date}" type="text"/>
	    		</li>
	    		<li>
	    			<label>物流单号</label>
	    			<c:if test="${result.logistics_number == '' || result.logistics_number == null }">
	    				<input class="dfinput" name="logistics_number" value="${result.logistics_number}" type="text"/>
	    			</c:if>
	    			<c:if test="${result.logistics_number != '' && result.logistics_number != null }">
	    				<input class="disable-input" name="logistics_number" value="${result.logistics_number}" disabled="disabled" type="text" />
	    			</c:if>	    			
	    			<i>请务必准确核实</i>
	    		</li>
	    		<li>
	    			<label>订单状态</label>
	    			<input type="radio" name="order_state" value="0" <c:if test="${result.order_state == '0'}">checked="checked"</c:if> />订单提交
					<input type="radio" name="order_state" value="1" <c:if test="${result.order_state == '1'}">checked="checked"</c:if> />待发货
					<input type="radio" name="order_state" value="2" <c:if test="${result.order_state == '2'}">checked="checked"</c:if> />已发货
					<input type="radio" name="order_state" value="3" <c:if test="${result.order_state == '3'}">checked="checked"</c:if> />已收货
	    		</li>
	    		<li>
	    			<label>收货人</label>
	    			<input class="disable-input" name="address_name" disabled="disabled" value="${result.address_name}" type="text"/>
	    		</li>
	    		<li>
	    			<label>联系方式</label>
	    			<input class="disable-input" name="address_phone" disabled="disabled" value="${result.address_phone}" type="text"/>
	    		</li>
	    		<li>
	    			<label>收货地址</label>
	    			<input class="disable-input" name="address_addres" disabled="disabled" value="${result.address_addres}" type="text"/>
	    		</li>	    		
	        	<li><label>&nbsp;</label>
	    			<c:if test="${tag == 'edit'}">
	        			<input type="submit" class="btn" value="确认保存"/>
	    			</c:if>	
	    			<c:if test="${tag == 'check'}">
	        			<input class="disable-btn" value="确认保存"/>
	    			</c:if>	  	    			        		
	        	</li>
	      	</ul>
	    </form>
     </div>
  </body>
</html>
