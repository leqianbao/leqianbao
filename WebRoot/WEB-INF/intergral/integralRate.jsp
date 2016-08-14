<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="jsp/js/jquery.js"></script>
		<script>
			function checkValue(){
				var r = new RegExp("^[1-9]*[1-9][0-9]*$");
				if((!(r.test($("#rate_num").val())))
							|| ($("#rate_num").val() > 2147483647)){
					alert("请输入正确的RMB消费积分汇率数值！");
					return false;
				}
				if((!(r.test($("#rate_num2").val())))
						|| ($("#rate_num2").val() > 2147483647)){
					alert("请输入正确的提现积分汇率数值！");
					return false;
				}
				if((!(r.test($("#rate_num3").val())))
						|| ($("#rate_num3").val() > 2147483647)){
					alert("请输入正确的购买理财积分汇率数值！");
					return false;
				}
				if((!(r.test($("#rate_num4").val())))
						|| ($("#rate_num4").val() > 2147483647)){
					alert("请输入正确的购买商品积分汇率数值！");
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
		    <li><a>首页</a></li>
		    <li>
		    	<span>
		    		<a>积分汇率</a>
		    	</span>
		    </li>
	    </ul>
	    </div>
	    
	    <div class="formbody">
	    
	    <div class="formtitle"><span>积分汇率</span></div>
	    	<form action="<%=path %>/pt/doIntegralRate" method="post" onsubmit="return checkValue()">
	      		<input type="hidden" name="tag" value="update" />
	      		<input type="hidden" name="id" value="${id}" />
	    		<ul class="forminfo">
				    <li>
				    	<label>RMB消费积分汇率</label>
				    	<input id="rate_num" name="integral_rate" type="text" class="dfinput" value="${integral_rate}"/>
				    	<i>每一块钱可以当做多少积分来使用（正整数）</i>
				    </li>
				    <li>
				    	<label>提现积分汇率</label>
				    	<input id="rate_num2" name="integral_rate_t" type="text" class="dfinput" value="${integral_rate_t}"/>
				    	<i>每提现多少钱对应获得多少积分（正整数）</i>
				    </li>
				    
				    <li>
				    	<label>购买理财积分汇率</label>
				    	<input id="rate_num3" name="integral_rate_gl" type="text" class="dfinput" value="${integral_rate_gl}"/>
				    	<i>每购买理财消费多少钱对应获得多少积分（正整数）</i>
				    </li>
				    
				    <li>
				    	<label>购买商品积分汇率</label>
				    	<input id="rate_num4" name="integral_rate_gs" type="text" class="dfinput" value="${integral_rate_gs}"/>
				    	<i>每购买商品消费多少钱对应获得多少积分（正整数）</i>
				    </li>
				    <li><label>&nbsp;</label><input type="submit" class="btn" value="确认保存"/></li>
	    		</ul>
	    	</form>
	   </div>
	</body>
</html>
