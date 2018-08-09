<%@page language="java" contentType="text/html; charset=UTF-8"
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
					if (!checkEmpty("productName", "产品名称"))  // categoryName是产品名称输入项的id <input id="categoryName">
						return false;
					if (!checkEmpty("originalPrice", "原价"))  
							return false;
					if (!checkEmpty("promotePrice", "促销价"))  
								return false;	
					if (!checkEmpty("productStock", "库存"))  
									return false;
					return true;
				});
	}); 
</script>

<title>产品管理</title>

<div class="workingArea">
 	<ol class="breadcrumb">
 		<li><a href="admin_category_list">所有分类</a></li>
 		<li><a href="admin_product_list?cid=${category.id}">${category.name}</a></li>
 		<li class="active">产品管理</li>
 	</ol>
	
	<%-- 产品管理表区 --%>
	<div class="listDataTableDiv">
		<%--具体知识见OneNote的Bootstrap入门（四）表格
		table 中的class 改为 “ table table-striped table-bordered table-hover ”鼠标经过、悬停时有效果，
		把内容放在一个class为table-responsive的div中
		<table>的class改为“table table-condensed”
		在浏览器变小的时候，不会影响表格内容，而是下方多一条滚动条
		 --%>
		<table class="table table-striped table-bordered table-hover table-condensed">
			<thead>
				<tr class="success">
					<th ALIGN="CENTER">ID</th>
					<th>图片</th>
					<th>产品名称</th>
					<th>产品小标题</th>
					<th>原价</th>
					<th>促销价</th>
					<th>库存</th>
					<th>图片管理</th>
					<th>设置属性</th>
					<th>编辑</th>
					<th>删除</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${products}" var="product">
					<tr>
						<td>${product.id}</td>
						<td><img height="50px" width="100px" src="imgs/category/${category.id}.jpg"></td>
						<td>${product.name}</td>
						<td>${product.subTitle}</td>
						<td>${product.originalPrice}</td>
						<td>${product.promotePrice}</td>
						<td>${product.stock}</td>
						<td>
							<%-- ptid是产品ID，pid是分类属性ID --%>
							<a href="admin_productImage_list?ptid=${product.id}">
								<%-- glyphicon 是bootstrap的图标库--%>
								<span class="glyphicon glyphicon-picture"></span>
							</a>
						</td>
						
						<td>
							<a href="admin_product_editPropertyValue?ptid=${product.id}">
								<%-- glyphicon 是bootstrap的图标库--%>
								<span class="glyphicon glyphicon-th-list"></span>
							</a>
						</td>
						
						<td>
							<a href="admin_product_edit?ptid=${product.id}">
								<span class="glyphicon glyphicon-edit"></span>
							</a>
						</td>
						<td>
							<%-- deleteLink是自定义的属性，在需要时调用--%>
							<a href="admin_product_delete?ptid=${product.id}" deleteLink="true">
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
	
	<%-- 添加商品产品产品区 --%>
	<div class="panel panel-warning addDiv" align="center">
<!-- 		<div class="panel-heading">新增产品</div> -->
		<div class="panel-body">
		<%-- (因为输入的数据都要提交，故需要引入form标签  项目配置路径是/tmall，所以action要加上“/tmall”),其实我实验过，此处不用加/tmall/
		 enctype="multipart/form-data",作用是以二进制方式提交
		--%>
			<form method="post" id="addForm" action="admin_product_add">
				<%-- enctype="multipart/form-data"是将文件以二进制的形式上传，这样可以实现多种类型的文件上传 --%>
				<table class="addTable">
					<tr>
						<td>产品名称</td>
						<td><input id="productName" name="productName" type="text" class="form-control"></td>
					</tr>
					<tr>
						<td>产品小标题</td>
						<td><input id="productSubTitle" name="productSubTitle" type="text" class="form-control"></td>
					</tr>
					<tr>
						<td>原价</td>
						<td><input id="originalPrice" name="oPrice" type="text" class="form-control"></td>
					</tr>
					<tr>
						<td>促销价</td>
						<td><input id="promotePrice" name="pPrice" type="text" class="form-control"></td>
					</tr>
					<tr>
						<td>库存</td>
						<td><input id="productStock" name="productStock" type="text" value="200" class="form-control"></td>
					</tr>								
												
					<tr class="submitTR">
						<td colspan="2" align="center">
							<%-- 
							onsubmit="" 触发submit type 
							onclick="" 触发button type  （这样说理解上比较狭隘）
							两者详细区别见：https://www.cnblogs.com/ahudyan-forever/p/5795463.html
							--%>
							<input type="hidden" name="cid" value="${category.id}">
							<button type="submit" class="btn btn-success">开始上传</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>


<%-- 导入底部构图 --%>
<%@ include file="../include/admin/adminFooter.jsp" %> 