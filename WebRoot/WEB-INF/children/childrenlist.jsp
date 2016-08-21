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
	<script type="text/javascript">

	// 点击分页按钮以后触发的动作
	 function handlePaginationClick(new_page_index, pagination_container) {
	    $("#comForm").attr("action", "<%=path %>/pt/doChildren?tag=query&pageNum=" + (new_page_index+1));
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
	function saveMoney(e,id){
		$this = $(e);
		var value = $this.val();
		var regex = /^([1-9]\d{0,15}|0)(\.\d{1,4})?$/;
		if(!(regex.test(value))){
			alert("请输入正确的混合消费现金价格！");
			return false;
		}
		$.ajax({
			type : "post", 
			url : "<%=path %>/pt/doChildren", 
			async : true, 
			data : {tag:"update", 
				id:id, 
				value:value	
			},
			dataType : "*", 
			success : function(json) { 
				var msg_body = json.replace("{","").replace("}","");
				if(msg_body){
					var msg = msg_body.split("saveMsg:")[1];
					alert(msg);
				}
			},
			error : function() { 
				alert('金额保存失败');
			}
		});
	}
</script>
<script type="text/javascript">
	$(document).ready(function(e) {
		$(".select3").uedSelect({
			width : 100
		});
	});
</script>
<style>
	dl, dt, dd, span{
		display:table-cell;
	}
</style>
  </head>
  
  <body>
   <div class="place">
    <span>位置：</span>
    <ul class="placeul">
	    <li><a>首页</a></li>
	    <li>
	    	<span>
	    		<a>子账户列表</a>
	    	</span>
	    </li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="querydiv">
    <form action="<%=path %>/pt/doChildren"   id="comForm"  method="post">
		<input type="hidden" name="tag" value="query" />
	 	<ul class="seachform">
		    <li>
		    	<label>主账户用户ID </label>
		    	<input name="user_id" type="text"  value="${user_id }" class="scinput" />
		    </li>
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
		        <th>序号</th>
		        <th>主账户用户ID</th>
		        <th>名称</th>
		        <th>手机号</th>
		        <th>身份证号</th>
		        <th>开户行名称</th>
		        <th>银行卡号</th>
		        <th>提现金额</th>
        	</tr>
        </thead>
        <%
        	int index = 0;
        %>
       	<tbody>
			<c:forEach items="${result.data_list }" var="child">
			<%
        		index = index + 1;
        	%>
				<tr>
					<td><%=index%></td>
					<td><c:out value="${child.user_id}"></c:out></td>
					<td><c:out value="${child.child_name }"></c:out></td>
					<td><c:out value="${child.child_phone }"></c:out></td>
					<td><c:out value="${child.child_id_card}" ></c:out></td> 
					<td><c:out value="${child.child_bank_name}" ></c:out></td> 
					<td><c:out value="${child.child_bank_account}" ></c:out></td> 
					<td>
						<input name="child_balance" value="${child.child_balance}" 
							maxlength="13" type="text" class="dfinput" onblur="saveMoney(this,${child.id})"/>
					</td> 
				</tr>
			</c:forEach> 
      	</tbody>
    </table>
    <div class="pagin">
    	<div class="message">共<i class="blue">${result.total_record }</i>条记录，共<i class="blue">${result.total_page }&nbsp;</i>页，当前显示第&nbsp;<i class="blue">${result.current_page }&nbsp;</i>页</div>

		<ul class="paginList">
			<li id="News-Pagination">
			</li>
		</ul>
    </div> 
    
    </c:if> 

    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
  </body>
</html>
