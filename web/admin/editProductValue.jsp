<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="java.util.*"%>
   
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品属性值</title>


<!-- 	 	注释部分：	
	使用post方式提交ajax 
			$.post 是 $.ajax的简化版，专门用于发送POST请求
			 
			$.post(
			    page,
			    {"name":value},
			    function(result){
			        $("#checkResult").html(result);
			    }
			);
			
			$.post 使用3个参数
			第一个参数: page 访问的页面
			第二个参数: {name:value} 提交的数据
			第三个参数: function(){} 响应函数
			只有第一个参数是必须的，其他参数都是可选 
			 
-->
<!-- 			实例：
			<script src="http://how2j.cn/study/jquery.min.js"></script>
			<div id="checkResult"></div>
			输入账号 :<input id="name" type="text">
		
			$(function(){
			   $("#name").keyup(function(){
			     var page = "/study/checkName.jsp";
			     var value = $(this).val();
			  
			        $.post(
			            page,
			            {"name":value},
			            function(result){
			              $("#checkResult").html(result);
			            }
			        );
			   });
			});
 -->

<script type="text/javascript" src="jquery.min.js" charset="UTF-8"></script>  	

<script>	
	//Ajax技术，详见：https://blog.csdn.net/baidu_35205952/article/details/51644542?locationNum=8&fps=1
	$(function()
		{
			$("input.pvValue").keyup(function()
				{
					var page = "admin_product_updatePropertyValue";
					var value = $(this).val();
					var pvid = $(this).attr("pvid");
					var parentSpan = $(this).parent("span");
					parentSpan.css("border","1px solid yellow");
					
					$.post(
							page,
							{"value":value,"pvid":pvid},
							function(result)
							{
								if (result == "success")
									parentSpan.css("border","1px solid green");
								else
									parentSpan.css("border","1px solid red");
							}
					);
				});
		});
</script>

<div class="workingArea">
 	<ol class="breadcrumb">
 		<li><a href="admin_category_list">所有分类</a></li>
 		<li><a href="admin_product_list?cid=${product.category.id}">${product.category.name}</a></li>
 		<li class="active">${product.name}</li>
 		<li class="active">编辑产品属性</li>
 	</ol>
 	
 	<div class="editPVDiv">
		 	<c:forEach items="${propertyValues}" var="propertyValue">
			 	<div class="eachPropertyValue">
			 		<span class="pvName">${propertyValue.property.name}</span>
			 		<span class="pvValue"><input class="pvValue" pvid="${propertyValue.id}" type="text" 
			 		value='${propertyValue.value}'></span>
			 		
			 		<%-- 
			 		value='<%new String("${propertyValue.value}".getBytes("ISO-8859-1"),"UTF-8"); //解决中文乱码问题 %>' 
			 		--%>
			 	</div>
		 	</c:forEach>
		 	<div style="clear:both"></div>  
    </div>
</div>