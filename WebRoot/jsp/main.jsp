<%@	page import="cn.lc.dao.FetchCashDao"%>
<%@ page import="cn.lc.beans.FetchCash"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%!int getCount() {
		FetchCashDao fetchCashDao = new FetchCashDao();
		List<FetchCash> fetchCashs = fetchCashDao.getUntreatedList();
		return fetchCashs == null ? 0 : fetchCashs.size();
	}%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>信息管理系统界面
</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<script type="text/javascript">
	var int = self.setInterval("tip()", 30*60*1000);
	function tip() {
		console.log("tip");
		if(<%=getCount()%>>0){
			alert("您有未处理的提现请求");
		}
	}
</script>
<frameset rows="88,*" cols="*" frameborder="no" border="0"
	framespacing="0">
	<frame src="jsp/top.jsp" name="topFrame" scrolling="No"
		noresize="noresize" id="topFrame" title="topFrame" />
	<frameset cols="187,*" frameborder="no" border="0" framespacing="0">
		<frame src="jsp/left.jsp" name="leftFrame" scrolling="No"
			noresize="noresize" id="leftFrame" title="leftFrame" />
		<frame src="jsp/index.jsp" name="rightFrame" id="rightFrame"
			title="rightFrame" />
	</frameset>
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>
