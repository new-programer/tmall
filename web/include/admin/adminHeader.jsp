<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" isELIgnored="false" %>
	
	<%-- 引入JSTL --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
	<head>
			<%-- 引入JS和CSS --%>
		<script src="js/jquery/2.0.0/jquery.min.js"></script>
		<link href="css/bootstrap/3.3.6/bootstrap.min.scc" rel="stylesheet">
		<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
		<link href="css/back/style.css" rel="stylesheet">
		
		<script>
			<!--定义一些判断输入框的函数-->
			function checkEmpty(id, name)
			{
				var value = $("#" + id).val();
				if(value.length == 0)
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
				if (value.length == 0)
					{
						alert(name + "不能为空");
						$("#" + id)[0].focus();
						return false;
					}
				<!--如果 value 是特殊的非数字值 NaN（或者能被转换为这样的值），返回的值就是 true。如果 value是其他值,则返回 false。-->
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
			  <!--只有当value的值为int类型的时候，！=才不成立，从而返回的是true-->
			  if (parseInt(value) != value)
				  {
				  	alert(name + "必须是整数");
				  	$("#" + id)[0].focus();
				    return true;
				  }
		  }
		  
		  <!--删除链接确认操作-->
		  $(function()
				  {
					  <!--
					  $("a")构造的这个对象，是用CSS选择器构建了一个jQuery对象——它选择了所有的<a/>这个标签
					  $("a").click(function(){...}) 
					  就是在点击页面上的任何一个链接时的触发事件。
					  确切地说，就是jQuery用<a/>这个标签构建了一个对象$("a")，函数 click()是这个jQuery对象的一个（事件）方法
					  -->
			  		$("a").click(function()
			  				{
			  					var deleteLink = $(this).attr("deleteLink");
			  					console.log(deleteLink);
			  					
			  					if (deleteLink == "ture")
			  						{
			  							var confirmDelete = confirm("确认删除");
			  							if (confirmDelete)
			  								return true;
			  							else
			  								return false;
			  						}
			  				}
			  				);
				  });
		  
		</script>
	</head>
</html>