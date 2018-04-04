<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- ../指向的是父级目录里的 --%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<script>
	$(function()
	{
		<!--此处的addForm的id就是，后面form标签中的id-->
		$("#addForm").submit(function()
				{
					if (!checkEmpty("name", "分类名称"))
						return false;
					if (!checkEmpty("categoryPic", "分类图片"))
						return false;
					return true;
				});
	});
</script>

<title>分类管理</title>

<div class="workingArea">
	<h1 class="label label-info"></h1>
	<br>
	<br>
	
	<%-- 分类管理表区 --%>
	<div class="listDataTableDiv">
		<%--具体知识见OneNote的Bootstrap入门（四）表格
		table 中的class 改为 “ table table-striped table-bordered table-hover ”鼠标经过、悬停时有效果，
		把内容放在一个class为table-responsive的div中
		<table>的class改为“table table-condensed”
		在浏览器变小的时候，不会影响表格内容，而是下方多一条滚动条
		 --%>
		<table class="table-striped table-bordered table-hover table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>图片</th>
					<th>分类名称</th>
					<th>属性管理</th>
					<th>产品管理</th>
					<th>编辑</th>
					<th>删除</th>
				</tr>
			</thead>
			
				<tbody>
				<c:forEach items="${thecs}" var="category">
					<tr>
						<td>${category.id}</td>
						<td><img height="40px" src="imgs/category/${category.id}.jpg"></td>
						<td>${category.name}</td>
						<td>
							<a href="admin_property_list?cid=${category.id}">
								<%-- glyphicon 是bootstrap的图标库--%>
								<span class="glyphicon glyphicon-th-list"></span>
							</a>
						</td>
						
						<td>
							<a href="admin_product_list?cid=${category.id}">
								<%-- glyphicon 是bootstrap的图标库--%>
								<span class="glyphicon glyphicon-shopping-cart"></span>
							</a>
						</td>
						
						<td>
							<a href="admin_category_edit?cid=${category.id}">
								<span class="glyphicon glyphicon-edit"></span>
							</a>
						</td>
						<td>
							<a deleteLink="true" href="admin_category_delete?id=${category.id}">
								<span class="glyphicon glyphicon-trash"></span>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	
		</table>
	</div>
	
	<%-- 分页区 --%>
	<div class=pageDiv>
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>
	
	<%-- 添加商品分类产品区 --%>
	<div class="panel panel-warning addDiv">
		<div class="panel-heading">新增分类</div>
		<div class="panel-body">
		<%-- 因为输入的数据都要提交，故需要引入form标签 --%>
			<form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
				<%-- enctype="multipart/form-data"是将文件以二进制的形式上传，这样可以实现多种类型的文件上传 --%>
				<table class="addTable">
					<tr>
						<td>分类名称</td>
						<td><input id="categoryName" name="name" type="text" class="form-control"></td>
					</tr>
					<tr>
						<td>分类图片</td>
						<td>
							<input id="categoryPic" accept="image/*" type="file" name="filepath">
						</td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<button type="submit" class="btn btn-success">提交</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>


<%-- 导入底部构图 --%>
<%@ include file="../include/admin/adminFooter.jsp" %> 