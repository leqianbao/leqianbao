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
<script type="text/javascript" src="jsp/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="jsp/js/jquery.pagination.js"></script>
<script type="text/javascript">

// 点击分页按钮以后触发的动作
 function handlePaginationClick(new_page_index, pagination_container) {
    $("#stuForm").attr("action", "<%=path %>/pt/doPutinMoney?tag=query&pageNum=" + (new_page_index+1));
    $("#stuForm").submit();
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
  </head>
  
  <body>
   <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="querydiv">
    <form action="<%=path %>/pt/doPutinMoney"   id="stuForm"  method="post">
	<input type="hidden" name="tag" value="query" />
	 <ul class="seachform">
    <li><label>订单号</label><input name="putin_num" type="text"  value="${putin_num }" class="scinput" /></li>
    <li><label>充值金额</label><input name="putin_money" type="text"  value="${putin_money }" class="scinput" /></li>
    <li><label>用户ID</label><input name="user_id" type="text"  value="${user_id }" class="scinput" /></li>
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
       <!--  <th><input name="" type="checkbox" value="" checked="checked"/></th> -->
        <th>ID<i class="sort"><img src="jsp/images/px.gif" /></i></th>
        <th>订单号</th>
        <th>充值金额</th>
        <th>充值描述</th>
        <th>用户id</th>
        <th>充值人</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>状态</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
		<c:forEach items="${result.data_list }" var="putin">
						<tr>
							<td><c:out value="${putin.putin_id }"></c:out></td>
							<td><c:out value="${putin.putin_num }"></c:out></td>
							<td><fmt:formatNumber value="${putin.putin_money}" pattern="#,###.##" /></td>
							<%-- <td><c:out value="${putin.putin_money }"></c:out></td> --%>
							<td><c:out value="${putin.putin_desc }"></c:out></td>
							<td><c:out value="${putin.user_id }"></c:out></td>
							<td><c:out value="${putin.created_by }"></c:out></td>
							<td><fmt:formatDate value="${putin.stamp_created }"  type="both" /></td>
							<td><fmt:formatDate value="${putin.stamp_updated }"  type="both" /></td>
<%-- 							<td><c:out value="${putin.stamp_created }"></c:out></td>
							<td><c:out value="${putin.stamp_updated }"></c:out></td> --%>
							<td><c:out value="${putin.state }"></c:out></td>
							<td><span>修改信息</span></td>
						</tr>
		</c:forEach>  
        </tbody>
    </table>
    
   
    <div class="pagin">
    	<div class="message">共<i class="blue">${result.total_record }</i>条记录，共<i class="blue">${result.total_page }&nbsp;</i>页，当前显示第&nbsp;<i class="blue">${result.current_page }&nbsp;</i>页</div>

		<ul class="paginList"><li id="News-Pagination">
		
		</li></ul>

    </div> 
    

    </c:if>
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
  </body>
</html>
