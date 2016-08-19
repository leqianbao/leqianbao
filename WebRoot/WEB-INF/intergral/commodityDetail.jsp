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
	<script type="text/javascript" src="jsp/js/ajaxfileupload.js"></script>
	<style>
		.forminfo li label{
			width:160px
		}
		.textinput{
			width: 345px;
		    height: 80px;
		    line-height: 18px;
		    border-top: solid 1px #a7b5bc;
		    border-left: solid 1px #a7b5bc;
		    border-right: solid 1px #ced9df;
		    border-bottom: solid 1px #ced9df;
		    overflow-y : scroll;
		}
		.uploadBtn{
			width: 50px;
		    height: 28px;
		    background-color: #3F97C9;
		    font-size: 12px;
		    color: #fff;
		    cursor: pointer;
		    border-radius : 5px;
		}
	</style>
	<script>
		$(document).ready(function(e) {
			if(!'${result.commodity_id}'){
				$(".placeul").children().eq(2).find("span").find("a").html("新增商品");
				$(".placeul").children().eq(1).remove();
			}
		});
		var tapFlag = false;
		function inputFile($this){
			 $this = $($this);
			 tapFlag = true;
			 $('#commodity_img_url').html($this.val());
		}
		function ajaxFileUpload() {
			if('${result.commodity_id}'){
				
			}else{
				alert("请先点击【确认保存】按钮，确定商品信息已保存");
				return;
			}
			if($('#commodity_img_url').html()){
				
			}else{
				alert("请先点击【浏览】选择要上传的图片");
				return;
			}
			if(tapFlag && $("#myfile").val()){
				
			}else{
				alert("请再次选择，不要重复提交");
				return;
			}
			$.ajaxFileUpload
            (
                {
                    url: '<%=path %>/pt/doFileUpload?commodity_id='+'${result.commodity_id}', //用于文件上传的服务器端请求地址
                    type: 'post',
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: 'myfile', //文件上传域的ID
                    dataType: 'text', //返回值类型 一般设置为json
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                    	var dataStr = data.replace("{","").replace("}","");
                    	var url = dataStr.split("commodity_img_url:")[1];
           			 	$('#commodity_img_url').html("http:"+url);
                    	var msg = dataStr.split("commodity_img_url:")[0].split("uploadMsg:")[1];
                        $("#img1").attr("src", "http:"+url);
                        if (typeof (msg) != 'undefined') {
                            if (msg != '') {
                                alert(msg);
                            } else {
                                alert(msg);
                            }
                        }
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        alert(e);
                    }
                }
            )
            return false;
        }
		function checkValue(){
			var r = new RegExp("^\\d+$");
			var r2 = new RegExp("^[0-9]*[1-9][0-9]*$");
			var regex = /^([1-9]\d{0,15}|0)(\.\d{1,4})?$/;
			if((!(r.test($("#pay_num").val())) 
					&& !(r2.test($("#pay_num").val())))
						|| ($("#pay_num").val() > 2147483647)){
				alert("请输入正确的价格！");
				return false;
			}
			if(!(regex.test($("#pay_money").val()))){
				alert("请输入正确的现金价格！");
				return false;
			}
			if((!(r.test($("#pay_blend_integral").val())) 
					&& !(r2.test($("#pay_blend_integral").val())))
						|| ($("#pay_blend_integral").val() > 2147483647)){
				alert("请输入正确的混合消费积分价格！");
				return false;
			}
			if(!(regex.test($("#pay_blend_money").val()))){
				alert("请输入正确的混合消费现金价格！");
				return false;
			}
			if((!(r.test($("#com_num").val())) 
					&& !(r2.test($("#com_num").val())))
						|| ($("#com_num").val() > 2147483647)){
				alert("请输入正确的数量！");
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
	    	<span style="font-weight:100 !important">
				<c:url value="/pt/doCommodity" var="backMain">
					<c:param name="tag" value="query"/>
				</c:url>
	    		<a href="${backMain}">商品列表</a>
	    	</span>
	    </li>
	    <li>
	    	<span>
	    		<a>商品详情</a>
	    	</span>
	    </li>
    </ul>
    </div>
    
    <div class="formbody">
    	<div class="formtitle"><span>商品详细信息</span></div>
	    <form action="<%=path %>/pt/doCommodityDetail" id="stuForm" method="post" onsubmit="return checkValue()">
	    	<input type="hidden" name="tag" value="add"/>
	    	<input type="hidden" name="commodity_id" value="${result.commodity_id}" />
	    	<ul class="forminfo">
	    		<li>
	    			<label>商品名称</label>
	    			<input name="commodity_name" value="${result.commodity_name}" maxlength="50" type="text" class="dfinput" />
	    		</li>
	    		<li>
	    			<label>商品价格（积分）</label>
	    			<input id="pay_num" name="commodity_pay" value="${result.commodity_pay}" maxlength="10" type="text" class="dfinput" />
	    			<i>积分价格为正整数</i>
	    		</li>
	    		
	    		<li>
	    			<label>商品价格（现金）</label>
	    			<input id="pay_money" name="commodity_money" value="${result.commodity_money}" maxlength="15" type="text" class="dfinput" />
	    			<i>请填写争取的价格</i>
	    		</li>
	    		
	    		<li>
	    			<label>商品价格（混合付款：积分）</label>
	    			<input id="pay_blend_integral" name="commodity_blend_integral" value="${result.commodity_blend_integral}" maxlength="10" type="text" class="dfinput" />
	    			<i>积分价格为正整数</i>
	    		</li>
	    		<li>
	    			<label>商品价格（混合付款：现金）</label>
	    			<input id="pay_blend_money" name="commodity_blend_money" value="${result.commodity_blend_money}" maxlength="15" type="text" class="dfinput" />
	    			<i>请填写真确的价格</i>
	    		</li>
	    		<li>
	    			<label>商品数量</label>
	    			<input id="com_num" name="commodity_num" value="${result.commodity_num}" maxlength="10" type="text" class="dfinput" />
	    			<i>商品数量为正整数</i>
	    		</li>
	    		<li>
	    			<label>出售状态</label>
	    			<input type="radio" name="commodity_use_flag" value="0" <c:if test="${result.commodity_use_flag == '0'}">checked="checked"</c:if> />出售中
					<input type="radio" name="commodity_use_flag" value="1" <c:if test="${result.commodity_use_flag == '1'}">checked="checked"</c:if> />停售
	    		</li>
	    		<li>
	    			<label>商品类型</label>
	    			<input type="radio" name="commodity_type" value="0" <c:if test="${result.commodity_type == '0'}">checked="checked"</c:if> />实物商品
					<input type="radio" name="commodity_type" value="1" <c:if test="${result.commodity_type == '1'}">checked="checked"</c:if> />虚拟商品
	    		</li>
	    		<li>
	    			<label>商品简介</label>
	    			<textarea name="commodity_comment" class="textinput" rows="5" >${result.commodity_comment}</textarea>
	    		</li>
	    		<li>
	    			<label>商品图片</label>
		    		<input type="button" id="input1" class="uploadBtn" onclick="$('#myfile').click();" value="浏览"/>
					<input type="button" value="上传图片" onclick="ajaxFileUpload()"/>
					<input type="file" name="commodiy_file_url" id="myfile" onchange="inputFile(this)" style="display:none">
					<br/><span id="commodity_img_url"></span>
		    	</li>
		    	<li>
		    		<label>图片展示</label>
		    		<img id="img1" style="width:150px;height:150px" src="${result.commodity_imgurl}">
		    	</li>
	        	<li><label>&nbsp;</label><input type="submit" class="btn" value="确认保存"/></li>
	      	</ul>
	    </form>
     </div>
  </body>
</html>
