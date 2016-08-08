<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri= "http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri= "http://java.sun.com/jsp/jstl/fmt" %>
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
	<!-- <script type="text/javascript" src="jsp/js/jquery.js"></script> -->
	<link href="jsp/css/pagination.css" rel="stylesheet" type="text/css"/>
	<link href="jsp/css/select.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="jsp/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="jsp/js/select-ui.min.js"></script>
	<script type="text/javascript" src="jsp/js/jquery.pagination.js"></script>
	<script type="text/javascript">

// 点击分页按钮以后触发的动作
 function handlePaginationClick(new_page_index, pagination_container) {
    $("#stuForm").attr("action", "<%=path %>/pt/doUser?tag=query&pageNum=" + (new_page_index+1));
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
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="querydiv">
    <form action="<%=path %>/pt/doUser"   id="stuForm"  method="post">
	<input type="hidden" name="tag" value="query" />
	<ul class="seachform">
    <li><label>姓名</label><input name="user_name" type="text"  value="${user_name }" class="scinput" /></li>
    <li><label>手机号</label><input name="user_phone" type="text"  value="${user_phone }" class="scinput" /></li>
    <li><label>是否实名认证</label>  
    <div class="vocation">
    <select class="select3" name="is_verify" >
    <option value="">请选择</option>
    <option value="2">已认证</option>
    <option value="0">未认证</option>
    </select>
    </div>
    </li>
   <%--  <label>姓名</label><input type="text" name="name"  class="dfinput"  style="width:120px" value="${name }"/>
    <label>手机号</label><input type="text" name="phone" class="dfinput"  style="width:120px" value="${phone }"/>
    <input type="submit" class="btn" value="查询"> --%>
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
        <th>用户名</th>
        <th>手机号</th>
        <th>省份证号</th>
        <th>用户等级</th>
        <th>是否实名认证</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>状态</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>

		<c:forEach items="${result.data_list }" var="user">
						<tr>
							<td><c:out value="${user.user_id }"></c:out></td>
							<td><c:out value="${user.user_name }"></c:out></td>
							<td><c:out value="${user.user_phone }"></c:out></td>
							<td><c:out value="${user.idcard_num }"></c:out></td>
							<td><c:out value="${user.user_grade }"></c:out></td>
							<td>
								<c:if test="${user.is_verify eq 0}"><span  class="red">未认证</span></c:if>
								<c:if test="${user.is_verify eq 2}">以认证</c:if> 
							</td>
							<td><fmt:formatDate value="${user.stamp_created }"  type="both" /></td>
							<td><fmt:formatDate value="${user.stamp_updated }"  type="both" /></td>
							<td><c:out value="${user.state }"></c:out></td>
							<td><span>
							 <c:url value="/pt/doUser" var="edit">
							<c:param name="tag" value="toedit"/>
							<c:param name="user_id" value="${user.user_id }"/>
							<c:param name="user_name" value="${user.user_name }"/>
							<c:param name="idcard_num" value="${user.idcard_num }"/>
							</c:url>
							<%-- <a href="${edit}" title="修改信息">修改信息</a>&nbsp;&nbsp; --%>
							修改信息&nbsp;&nbsp;
							 <c:url value="/pt/doUser" var="sendMsg">
							<c:param name="tag" value="sendmsg"/>
							<c:param name="user_id" value="${user.user_id }"/>
							</c:url>
							发送消息
<%-- 							<a href="${sendMsg}" title="发送消息">发送消息</a> --%>
							   </span></td>
						</tr>
		</c:forEach>  
        </tbody>
    </table>
    
   
    <div class="pagin">
    	<div class="message">共<i class="blue">${result.total_record }</i>条记录，共<i class="blue">${result.total_page }&nbsp;</i>页，当前显示第&nbsp;<i class="blue">${result.current_page }&nbsp;</i>页</div>

		<ul class="paginList"><li id="News-Pagination">
		
		</li></ul>
<%--         <ul class="paginList">
        <c:url value="/servlet/doUser" var="first">
			<c:param name="pageNum" value="1"/>
			<c:param name="tag" value="query"/>
		</c:url>
        <li class="paginItem"><a href="${first}" >首页</a></li>
 
         <c:url value="/servlet/doUser" var="pre">
			<c:param name="pageNum" value="${result.currentPage}-1"/>
			<c:param name="tag" value="query"/>
		</c:url>
        <li class="paginItem"><a href="${pre}">上一页</a></li>
         <c:url value="/servlet/doUser" var="next">
			<c:param name="pageNum" value="${result.currentPage} + 1"/>
			<c:param name="tag" value="query"/>
		</c:url>
        <li class="paginItem"><a href="${next}">下一页</a></li>
         <c:url value="/servlet/doUser" var="end">
			<c:param name="pageNum" value="${totalPage}"/>
			<c:param name="tag" value="query"/>
		</c:url>
        
        <li class="paginItem"><a href="${end}">尾页</a></li>
    <div id="News-Pagination"></div>
        </ul> --%>
    </div> 
    

    </c:if>
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
  </body>
</html>
