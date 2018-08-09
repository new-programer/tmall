<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑分类</title>
<script>
	$(function()
	{
		$("#editForm").submit(function()
		{
			if(!checkEmpty("categoryName","分类名称"))
				return false;
			else
				return true;
		});
	});
</script>

<div class="workingArea">
	<!-- class="breadcrumb" 是bootstrap框架里的东西，作用是将
	1.所有分类
	2.编辑分类
	中显示处理成：所有分类/编辑分类  这种显示，既美观又实用
	-->
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li class="active">编辑分类</li>
	</ol>
	
	<div class="panel panel-warning editDiv">
		<div class="panel-heading">编辑分类</div>
		<div class="panel-body">
			<form id="editForm" method="post" action="admin_category_update" enctype="multipart/form-data">
				<table class="editTable">
					<tr>
						<td>分类名称</td>
						<td><input id="categoryName" name="name" value="${category.name}" type="text" class="form-control"></td>
					</tr>
					<tr>
						<td>分类图片</td>
						<td><input id="categoryPic" accept="image/*" type="file" name="filepath"></td>
					</tr>
					
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="cid" value="${category.id}">
							<button type="submit" class="btn btn-success">上传</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>