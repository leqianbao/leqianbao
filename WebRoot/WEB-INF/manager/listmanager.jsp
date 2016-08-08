<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<script type="text/javascript" src="jsp/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="jsp/js/jquery.pagination.js"></script>
<script type="text/javascript">

// 点击分页按钮以后触发的动作
 function handlePaginationClick(new_page_index, pagination_container) {
    $("#stuForm").attr("action", "<%=path %>/pt/doManager?tag=query&pageNum=" + (new_page_index+1));
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
    <form action="<%=path %>/pt/doManager"   id="stuForm"  method="post">
	<input type="hidden" name="tag" value="query" />
    <ul class="seachform">
    <li><label>姓名</label><input name="mgr_name" type="text"  value="${mgr_name }" class="scinput" /></li>
    <li><label>手机号</label><input name="mgr_phone" type="text"  value="${mgr_phone }" class="scinput" /></li>
    <li><label>登录账号</label><input name="mgr_loginid" type="text"  value="${mgr_loginid }" class="scinput" /></li>
  <%-- 
    <label>姓名</label><input type="text" name="mgr_name"  class="dfinput"  style="width:120px" value="${mgr_name }"/>
    <label>手机号</label><input type="text" name="mgr_phone" class="dfinput"  style="width:120px" value="${mgr_phone }"/>
    <label>登录账号</label><input type="text" name="mgr_loginid" class="dfinput"  style="width:120px" value="${mgr_loginid }"/>
    <input type="submit" class="btn" value="查询">
     --%>
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
        <th>登录账号</th>
        <th>名称</th>
        <th>性别</th>
        <th>联系电话</th>
        <th>联系地址</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>

		<c:forEach items="${result.data_list }" var="mg">
						<tr>
							<td><c:out value="${mg.manager_id }"></c:out></td>
							<td><c:out value="${mg.mgr_loginid }"></c:out></td>
							<td><c:out value="${mg.mgr_name }"></c:out></td>
							<td><c:out value="${mg.mgr_sex }"></c:out></td>
							<td><c:out value="${mg.mgr_phone }"></c:out></td>
							<td><c:out value="${mg.mgr_address }"></c:out></td>
							<td>
								<c:if test="${mg.state eq 'A'}">正常</c:if>
								<c:if test="${mg.state eq 'B'}">停用</c:if> 
							</td>
							<td><fmt:formatDate value="${mg.stamp_created }"  type="both" /></td>
							<td><fmt:formatDate value="${mg.stamp_updated }"  type="both" /></td>
							<td><span>
							<c:url value="/pt/doManager" var="edit">
							<c:param name="tag" value="toedit"/>
							<c:param name="manager_id" value="${mg.manager_id }"/>
							<c:param name="mgr_loginid" value="${mg.mgr_loginid }"/>
							<c:param name="mgr_name" value="${mg.mgr_name }"/>
							<c:param name="mgr_sex" value="${mg.mgr_sex }"/>
							<c:param name="mgr_phone" value="${mg.mgr_phone }"/>
							<c:param name="mgr_address" value="${mg.mgr_address }"/>
							</c:url>
							<a href="${edit}" title="修改信息">修改信息</a>&nbsp;&nbsp;
							   
							<c:url value="/pt/doManager" var="addRight">
							<c:param name="tag" value="toright"/>
							<c:param name="manager_id" value="${mg.manager_id }"/>
							<c:param name="role_ids" value="${mg.role_ids }"/>
							</c:url>
							<a href="${addRight}" title="添加修改权限">添加修改权限</a>&nbsp;&nbsp;
							   <c:url value="/pt/doManager" var="passwdReset">
							<c:param name="tag" value="resetpasswd"/>
							<c:param name="manager_id" value="${mg.manager_id }"/>
							</c:url>
							<a href="${passwdReset}"  onclick="return confirm('确定要把密码重置为123456吗？')" title="密码重置">密码重置</a>
							   
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
