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
	<style type="text/css">
		.input-width{
			width:170px;
		}
	</style>
	<script type="text/javascript">
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
		function saveIntegral(e,user_id){
			$this = $(e);
			var value = $this.val();
			var r = new RegExp("^\\d+$");
			if(!(r.test(value))){
				alert("请输入正确积分数！");
				return false;
			}
			$.ajax({
				type : "post", 
				url : "<%=path %>/pt/doIntegral", 
				async : true, 
				data : {tag:"update", 
					user_id: user_id,
					value : value,
				},
				dataType : "*", 
				success : function(json) { 
					var msg_body = json.replace("{","").replace("}","");
					if(msg_body){
						var msg = msg_body.split("saveMsg:")[1];
						alert(msg);
					};
				},
				error : function() { 
					alert('积分保存失败');
				}
			});
		}
	</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a>首页</a></li>
			<li><a>用户积分</a></li>
		</ul>
	</div>
	<div class="rightinfo">
		<div class="querydiv">
			<form action="<%=path%>/pt/doIntegral" id="stuForm" method="get">
				<input type="hidden" name="tag" value="query" />
				<ul class="seachform">
					<li>
						<label>用户手机号</label>
						<input name="user_phone" type="text" value="${user_phone }" class="scinput" />
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
						<th>用户手机号</th>
						<th>用户名</th>
						<th>积分</th>
						<th>更新时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<thead>
					<c:forEach items="${result.data_list }" var="userIntegral">
						<tr>
							<td><c:out value="${userIntegral.user_phone}"></c:out></td>
							<td><c:out value="${userIntegral.user_name}"></c:out></td>
							<td>
								<input name="integral" value="${userIntegral.user_integral}" 
									maxlength="10" type="text" class="dfinput input-width" onblur="saveIntegral(this,'${userIntegral.user_id}')"/>
							</td>
							<td><c:out value="${userIntegral.update_date}"></c:out></td>
							<td>
								<span>
									<c:url value="/pt/DoIntegralList" var="query">
										<c:param name="tag" value="query"/>
										<c:param name="user_phone" value="${userIntegral.user_phone}"/>
									</c:url>
									<a href="${query}">查看明细</a>
								</span>
							</td>
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