<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   	<base href="<%=basePath%>"> 
    
    <title>信息管理系统界面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="jsp/css/style.css" rel="stylesheet" type="text/css" />
	<link href="jsp/css/pagination.css" rel="stylesheet" type="text/css"/>
	<link href="jsp/css/select.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="jsp/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="jsp/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="jsp/js/select-ui.min.js"></script>
    <script type="text/javascript" src="jsp/js/My97DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
	// 点击分页按钮以后触发的动作
	 function handlePaginationClick(new_page_index, pagination_container) {
	    $("#stuForm").attr("action", "<%=path %>/pt/doFetchCash?tag=query&pageNum=" + (new_page_index+1));
	    $("#stuForm").submit();
	    return false; 
	}

	$(function(){
		$("#News-Pagination").pagination(${result.total_record},{
	        items_per_page:${result.page_size}, // 每页显示多少条记录
	        current_page:${result.current_page} - 1, // 当前显示第几页数据
	        num_display_entries:4, // 分页显示的条目数
	        next_text:"下一页",
	        prev_text:"上一页",
	        num_edge_entries:2, // 连接分页主体，显示的条目数
	        callback:handlePaginationClick,
		});
		
	}); 
	</script>
</head>
<body>
	<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
	    <li><a href="#">首页</a></li>
	    <li><a href="#">订单列表</a></li>
	    </ul>
    </div>
    <div class="rightinfo">
    	<div class="querydiv">
    	    <form action="<%=path %>/pt/doUserOrder"   id="stuForm"  method="post">
				<input type="hidden" name="tag" value="query" />
			 	<ul class="seachform">
				    <li><label>用户手机号</label><input name="user_phone" type="text"  value="${user_phone }" class="scinput" /></li>
				    <li><label>订单号 </label><input name="order_no" type="text"  value="${order_no }" class="scinput" /></li>
				    <li><label>创建时间 </label><input id="create_date" name="create_date" type="text"  value="${create_date }" readonly="readonly" class="scinput Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></li>
				 	<li><label>&nbsp;</label><input type="submit" class="scbtn" value="查询"/></li>
			    </ul>
			</form>	
    	</div>
        <!-- 后台返回结果为空 -->
		<c:if test="${fn:length(result.data_list) eq 0 }">
			<span>查询的结果不存在</span>
		</c:if>
		<!-- 后台返回结果不为空 -->
		<c:if test="${fn:length(result.data_list) gt 0 }">
			<table class="tablelist">
				<thead>
			    	<tr>
				        <th>ID<i class="sort"><img src="jsp/images/px.gif" /></i></th>
				        <th>订单号</th>
				        <th>商品名称</th>
				        <th>商品数量</th>
				        <th>下单用户</th>
				        <th>创建时间</th>
				        <th>收货时间</th>
				        <th>状态</th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <thead>
		        	<c:forEach items="${result.data_list }" var="userOrder">
						<tr>
							<td><c:out value="${userOrder.user_order_id }"></c:out></td>
							<td><c:out value="${userOrder.order_no }"></c:out></td>
							<td><c:out value="${userOrder.commodity_name}"></c:out></td> 
							<td><c:out value="${userOrder.commodity_num }"></c:out></td>
							<td><c:out value="${userOrder.user_phone }"></c:out></td>
							<td><c:out value="${userOrder.create_date.toString().substring(0,19) }"></c:out></td>
							<td><c:out value="${userOrder.end_date.toString().substring(0,19) }"></c:out></td>
							<td>
								<span>
									<c:if test="${userOrder.order_state eq 0 }">
										<label>订单提交</label>
									</c:if>
									<c:if test="${userOrder.order_state eq 1 }">
										<label>待发货</label>
									</c:if>	
									<c:if test="${userOrder.order_state eq 2 }">
										<label>已发货</label>
									</c:if>	
									<c:if test="${userOrder.order_state eq 3 }">
										<label>已收货</label>
									</c:if>									
								</span>							
							</td>
							<td>
								<span>
									<c:if test="${userOrder.order_state eq 3 }">
										<c:url value="/pt/doUserOrder" var="handler">
											<c:param name="tag" value="check"/>
											<c:param name="order_no" value="${userOrder.order_no}"/>
										</c:url>				
										<a class="gray" href="${handler}">查看</a>
									</c:if>
									<c:if test="${userOrder.order_state lt 3 }">
										<c:url value="/pt/doUserOrder" var="handler">
											<c:param name="tag" value="edit"/>
											<c:param name="order_no" value="${userOrder.order_no}"/>
										</c:url>									
										<a class="red" href="${handler}">编辑</a>
									</c:if>									
								</span>
							</td>						
						</tr>
					</c:forEach> 
		        </thead>
			</table>
			<div class="pagin">
			    <div class="message">共<i class="blue">${result.total_record }</i>条记录，共<i class="blue">${result.total_page }&nbsp;</i>页，当前显示第&nbsp;<i class="blue">${result.current_page }&nbsp;</i>页</div>
			
				<ul class="paginList">	
					<li id="News-Pagination"></li>
				</ul>
			</div> 
		</c:if>
	</div>
	<script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>