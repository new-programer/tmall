<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*"%>

<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- ../指向的是父级目录里的 --%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<script>
$(function()
		{
			<%--  此处的addForm的id就是，后面form标签中的id  --%>
			$("#addForm").submit(function()
					{
						if (!checkEmpty("propertyName", "属性名称"))  // propertyName是分类名称输入项的id <input id="propertyName">
							return false;
						return true;
					});
		}); 	
</script>

<title>属性管理</title>
<div class="workingArea">
	<!-- class="breadcrumb" 是bootstrap框架里的东西，作用是将
	1.所有分类
	2.马桶
	3.属性管理
	中显示处理成：所有分类/马桶/属性管理  这种显示，既美观又实用
	-->
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_property_list?cid=${category.id}">${category.name}</a></li>
		<li class="active">属性管理</li>
	</ol>
	
	<%--该分类显示所有属性 --%>
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>属性名称</th>
					<th>编辑</th>
					<th>删除</th>		
				</tr>			
			</thead>
			<tbody>
			<%-- 之前 items="${properties}"误写成items="{properties}"，一直抛出异常，找了半天才找到，以后写代码要注意点--%>
				<c:forEach items="${properties}" var="property">
					<tr>
						<td>${property.id}</td>
						<td>${property.name}</td>
						<td><a href="admin_property_edit?id=${property.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
						<td><a href="admin_property_delete?id=${property.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>			
		</table>
	</div>
	
	<%-- 添加分页 --%>
	<div class="pageDiv">
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>
	
	<%-- 添加属性 --%>
	<div class="panel panel-warning addDiv">
		<div class="panel heading">新增属性</div>
		<div class="panel body"  align="center">
			<form action="admin_property_add" method="post" id="addForm">
				<table class="addTable">
					<tr>
						<td>属性名称</td>
						<td><input type="text" id="propertyName" name="name" class="form-control"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="cid" value="${category.id}">
							<button type="submit" class="btn btn-success">提交</button>
						</td>
					</tr>
				</table>
			</form>
		</div>		
	</div>
</div>	
<%-- 页脚 --%>
<%@include file="../include/admin/adminFooter.jsp" %>
