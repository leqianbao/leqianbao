<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
<link href="jsp/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript" src="jsp/js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>系统功能</div>
    
    <dl class="leftmenu">
        
    <dd>
	    <c:forEach items="${sessionScope.rightList}" var="right">
			<c:if test="${right=='DD'}">
			    <div class="title">
			    	<span><img src="jsp/images/leftico01.png" /></span>系统管理
			    </div>
	     	</c:if>
		</c:forEach>
    	<ul class="menuson">
	    	<c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='DD'}">
					<c:url value="/pt/doManager" var="queryInst">
						<c:param name="tag" value="add"/>
					</c:url>
	        		<li>
	        			<cite></cite>
	        			<a href="${queryInst}" target="rightFrame">管理员管理</a>
	        			<i></i>
	        		</li>
	        	</c:if>
			</c:forEach> 
			<c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='DB'}">
	        		<li class="active">
	        			<cite></cite>
	        			<a href="right.html" target="rightFrame">权限管理</a>
	        			<i></i>
	        		</li>
	           	</c:if>
			</c:forEach>
			<c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='DD'}">
	        		<li>
		        		<cite></cite>
		        		<a href="imgtable.html" target="rightFrame">角色管理</a>
		        		<i></i>
		        	</li>
	           	</c:if>
			</c:forEach>
			<c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='DD'}">
	        		<li>
	        			<cite></cite>
	        			<a href="form.html" target="rightFrame">角色授权</a>
	        			<i></i>
	        		</li>
	           	</c:if>
			</c:forEach>
        </ul>    
    </dd>



				
    <dd>
    	<div class="title">
    		<span><img src="jsp/images/leftico02.png" /></span>用户管理
    	</div>
  
		<ul class="menuson">
		 <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doUser" var="queryUser">
						<c:param name="tag" value="query"/>
						<c:param name="pageNum" value="1"/>
					</c:url>
	         <li><cite></cite><a href="${queryUser}" target="rightFrame">查看用户</a><i></i></li>
	        	</c:if>
		</c:forEach> 
	        </ul>     
	    </dd> 
	    
	    
	    <dd><div class="title"><span><img src="jsp/images/leftico03.png" /></span>消息管理</div>
	    <ul class="menuson">
	        <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doMessage" var="queryMsgS">
						<c:param name="tag" value="query"/>
						<c:param name="pageNum" value="1"/>
						<c:param name="msgType" value="0"/>
					</c:url>
	         <li><cite></cite><a href="${queryMsgS}" target="rightFrame">系统消息</a><i></i></li>
	        	</c:if>
		</c:forEach> 
		      <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doMessage" var="queryMsgP">
						<c:param name="tag" value="query"/>
						<c:param name="pageNum" value="1"/>
						<c:param name="msgType" value="1"/>
					</c:url>
	         <li><cite></cite><a href="${queryMsgP}" target="rightFrame">个人消息</a><i></i></li>
	        	</c:if>
		</c:forEach> 
			      <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doMessage" var="toaddMsg">
						<c:param name="tag" value="toadd"/>
					</c:url>
	         <li><cite></cite><a href="${toaddMsg}" target="rightFrame">发布消息</a><i></i></li>
	        	</c:if>
		</c:forEach> 
	        
	    </ul>    
    </dd>  
    
    
    <dd><div class="title"><span><img src="jsp/images/leftico04.png"/></span>理财产品管理</div>
    <ul class="menuson">
         <c:forEach items="${sessionScope.rightList}" var="right">
			<c:if test="${right=='AA'}">
				 <c:url value="/pt/doFinanceProduct" var="queryFinanceP">
					<c:param name="tag" value="query"/>
					<c:param name="pageNum" value="1"/>
				</c:url>
         		<li><cite></cite><a href="${queryFinanceP}" target="rightFrame">查看理财产品</a><i></i></li>
        	</c:if>
		</c:forEach> 
	    <c:forEach items="${sessionScope.rightList}" var="right">
			<c:if test="${right=='AA'}">
				 <c:url value="/pt/doFinanceProduct" var="toaddFinanceP">
					<c:param name="tag" value="toadd"/>
				</c:url>
         		<li><cite></cite><a href="${toaddFinanceP}" target="rightFrame">添加理财产品</a><i></i></li>
        	</c:if>
		</c:forEach> 
    </ul>
    
    </dd>  
    
    <dd>
    	<div class="title"><span><img src="jsp/images/leftico04.png" /></span>充值管理</div>
	    <ul class="menuson">
	     	<c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doPutinMoney" var="queryPutin">
						<c:param name="tag" value="query"/>
						<c:param name="pageNum" value="1"/>
					</c:url>
	         		<li><cite></cite><a href="${queryPutin}" target="rightFrame">查看充值记录</a><i></i></li>
	        	</c:if>
			</c:forEach> 
	     	<c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doPutinMoney" var="toaddPutin">
						<c:param name="tag" value="toadd"/>
					</c:url>
	         		<li><cite></cite><a href="${toaddPutin}" target="rightFrame">金额充值</a><i></i></li>
	        	</c:if>
			</c:forEach> 
	    </ul>
    </dd>
     
    <dd>
    	<div class="title"><span><img src="jsp/images/leftico04.png" /></span>提现管理</div>
	    <ul class="menuson">
	    	 <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doFetchCashMain" var="queryOutMoney">
						<c:param name="tag" value="query"/>
						<c:param name="pageNum" value="1"/>
					</c:url>
	         		<li><cite></cite><a href="${queryOutMoney}" target="rightFrame">查看提现记录</a><i></i></li>
	        	</c:if>
			 </c:forEach> 
	    </ul>
    </dd> 
    <dd>
    	<div class="title"><span><img src="jsp/images/leftico04.png" /></span>积分商城管理</div>
	    <ul class="menuson">
	    	 <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doCommodity" var="queryCommodityList">
						<c:param name="tag" value="query"/>
						<c:param name="pageNum" value="1"/>
					</c:url>
	         		<li><cite></cite><a href="${queryCommodityList}" target="rightFrame">查看商品</a><i></i></li>
	        	</c:if>
			 </c:forEach> 
			 <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doCommodity" var="handler">
						<c:param name="tag" value="edit"/>
					</c:url>
					<li><cite></cite><a href="${handler}" target="rightFrame">新增商品</a></li>
	        	</c:if>
			 </c:forEach> 
			 
	    	 <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doUserOrder" var="queryOrderList">
						<c:param name="tag" value="query"/>
						<c:param name="pageNum" value="1"/>
					</c:url>
	         		<li><cite></cite><a href="${queryOrderList}" target="rightFrame">查看订单</a><i></i></li>
	        	</c:if>
			 </c:forEach>
			 
			 <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/DoIntegralList" var="queryIntegralList">
						<c:param name="page_num" value="1"/>
						<c:param name="state" value="0"/>
					</c:url>
	         		<li><cite></cite><a href="${queryIntegralList}" target="rightFrame">查看积分</a><i></i></li>
	        	</c:if>
			 </c:forEach> 
	    </ul>
    </dd> 
    <dd>
    	<div class="title"><span><img src="jsp/images/leftico04.png" /></span>积分汇率管理</div>
	    <ul class="menuson">
	    	 <c:forEach items="${sessionScope.rightList}" var="right">
				<c:if test="${right=='AA'}">
					 <c:url value="/pt/doIntegralRate" var="queryRate">
						<c:param name="tag" value="query"/>
					</c:url>
	         		<li><cite></cite><a href="${queryRate}" target="rightFrame">积分汇率</a><i></i></li>
	        	</c:if>
			 </c:forEach> 
	    </ul>
    </dd> 
     
    <%-- <dd><div class="title"><span><img src="jsp/images/leftico04.png" /></span>统计报表</div>
    <ul class="menuson">
        <li><cite></cite><a href="filelist.html" target="rightFrame">会员统计</a><i></i></li>
        <li><cite></cite><a href="tab.html" target="rightFrame">理财统计</a><i></i></li>
        <li><cite></cite><a href="jsp/error.jsp" target="rightFrame">押金统计</a><i></i></li>
    </ul>
    </dd>   --%> 
    </dl>
    
  </body>
</html>
