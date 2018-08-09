<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" isELIgnored="false" %>
	
	<%-- 引入JSTL --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
	<head>
			<%-- 引入JS和CSS --%>
		<script src="js/jquery/3.3.1/jquery.min.js"></script>
		<link href="css/bootstrap/3.3.7/bootstrap.min.css" rel="stylesheet">
		<script src="js/bootstrap/3.3.7/bootstrap.min.js"></script>
		<link href="css/back/style.css" rel="stylesheet">
		<%-- 
		rel是relationship的英文缩写stylesheet中style是样式的意思,sheet是表格之意,总起来是样式表的意思
		rel="stylesheet" 描述了当前页面与href所指定文档的关系.即说明的是,href连接的文档是一个新式表
 		--%>
		<script>
			<!--定义一些判断输入框的函数-->
			function checkEmpty(id, name)
			{
				var value = $("#" + id).val();
				<%-- 多重判断会好一点  --%>
				if(null==value || value=="" || value.length==0)
					{
						alert(name + "不能为空");
						$("#" + id)[0].focus();
						return false;
					}
				return true;
			}
			
			function checkNumber(id, name)
			{
				var value = $("#" + id).val();

				//checkEmpty(id, name);
				if (value.length==0)
					{
						alert(name + "不能为空");
						$("#" + id)[0].focus(); <%-- 可以直写成$("#" + id).focus() --%>
						return false;
					}
				<%-- 如果 value 是特殊的非数字值 NaN（或者能被转换为这样的值），返回的值就是 true。如果 value是其他值,则返回 false。 --%>
				if (isNan(value))   
					{
						alert(name + "必须为数字");
						$("#" + id)[0].focus();
						return false;
					}
				return true;
			}
			
		  function checkInt(id, name)
		  {
			  var value = $("#" + id).val();
			  if (value.length == 0)
				  {
				  	alert(name + "不能为空");
				  	$("#" + id)[0].focus();
				  	return false;
				  }
			  <%-- 只有当value的值为int类型的时候，！=才不成立，从而返回的是true --%>
			  if (parseInt(value) != value)
				  {
				  	alert(name + "必须是整数");
				  	$("#" + id)[0].focus();
				    return false;
				  }
			  return true;
		  }
		  
		  <!--删除链接确认操作-->
		  $(function()
				  {
					  <%--
					  $("a")构造的这个对象，是用CSS选择器构建了一个jQuery对象——它选择了所有的<a/>这个标签
					  $("a").click(function(){...}) 
					  就是在点击页面上的任何一个链接时的触发事件。
					  确切地说，就是jQuery用<a/>这个标签构建了一个对象$("a")，函数 click()是这个jQuery对象的一个（事件）方法
					  --%>
			  		$("a").click(function()
			  				{
			  					<%--this指向函数的调用者S("a")对象--%>
			  					var deleteLink = $(this).attr("deleteLink");
			  					<%--想要了解attr属性访问这个网站：
			  					http://www.w3school.com.cn/tiy/t.asp?f=jquery_attributes_attr_get
			  					--%>
			  					console.log(deleteLink);
			  					
			  					if (deleteLink == "true")
			  						{
			  							<%-- 
			  							如果用户点击确定按钮，则 confirm() 返回 true。如果点击取消按钮，则 confirm() 返回 false。
										在用户点击确定按钮或取消按钮把对话框关闭之前，它将阻止用户对浏览器的所有输入。
										在调用 confirm() 时，将暂停对 JavaScript 代码的执行，在用户作出响应之前，不会执行下一条语句。
										详见：http://www.w3school.com.cn/jsref/met_win_confirm.asp
								 		--%>
			  							var confirmDelete = confirm("确认删除");
			  							if (confirmDelete)
			  								return true;
			  							return false;
			  						}
			  				});
				  });
		  
		</script>
	</head>
	<body></body>
</html>