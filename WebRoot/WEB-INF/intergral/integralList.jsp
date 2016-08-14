<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<!-- <script type="text/javascript" src="jsp/js/jquery.js"></script> -->
	<link href="jsp/css/pagination.css" rel="stylesheet" type="text/css"/>
	<link href="jsp/css/select.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="jsp/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="jsp/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="jsp/js/select-ui.min.js"></script>
	<script type="text/javascript" src="jsp/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	

<script type="text/javascript">
	// 点击分页按钮以后触发的动作
	// 点击分页按钮以后触发的动作
 function handlePaginationClick(new_page_index, pagination_container) {
    $("#comForm").attr("action", "<%=path%>/pt/DoIntegralList?pageNum=" + (new_page_index+1));
    $("#comForm").submit();
    return false; 
}

$(function(){
	$("#News-Pagination").pagination(${result.total_record}, {
        items_per_page:${result.page_size}, // 每页显示多少条记录
        current_page:${result.current_page} - 1, // 当前显示第几页数据
        num_display_entries:4, // 分页显示的条目数
        next_text:"下一页",
        prev_text:"上一页",
        num_edge_entries:2, // 连接分页主体，显示的条目数
        callback:handlePaginationClick
	});
	
}); 
	</script>
	
	<script type="text/javascript">
	$(document).ready(function(e) {
		$(".select3").uedSelect({
			width : 100
		});
	});
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">积分列表</a></li>
		</ul>
	</div>
	<div class="rightinfo">
		<div class="querydiv">
			<form action="<%=path%>/pt/DoIntegralList" id="stuForm" method="get">
				<input type="hidden" name="tag" value="query" />
				<ul class="seachform">
					<li>
						<label>用户手机号</label>
						<input name="user_phone" type="text" value="${user_phone }" class="scinput" />
					</li>
					
						<li><label>创建时间 </label><input id="create_date" name="create_date" type="text"  value="${create_date }" readonly="readonly" class="scinput Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></li>
					
					<li>
						<label>积分状态 </label>
						<div class="vocation">
							<select class="select3" name="record_state">
								<option value="0">全部</option>
								<option value="1">收入</option>
								<option value="2">支出</option>
							</select>
						</div>
					</li>
					<li><label>&nbsp;</label><input type="submit" class="scbtn"
						value="查询" /></li>
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
						<th>用户名</th>
						<th>积分</th>
						<th>状态</th>
						<th>详情</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<thead>
					<c:forEach items="${result.data_list }" var="record">
						<tr>
							<td><c:out value="${record.user_name }"></c:out></td>
							<td><c:out value="${record.integral }"></c:out></td>
							<td><c:if test="${record.record_state eq 0}">
									<span>全部</span>
								</c:if> <c:if test="${record.record_state eq 1}">
									<span class="">收入</span>
								</c:if> <c:if test="${record.record_state eq 2}">
									<span class="">支出</span>
								</c:if></td>
							<td><c:out value="${record.comment }"></c:out></td>
							<td><c:out value="${record.create_time}"></c:out></td>
							<td></td>
						</tr>
					</c:forEach>
				</thead>
			</table>
			<div class="pagin">
				<div class="message">
					共<i class="blue">${result.total_record }</i>条记录，共<i class="blue">${result.total_page }&nbsp;</i>页，当前显示第&nbsp;<i
						class="blue">${result.current_page }&nbsp;</i>页
				</div>

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