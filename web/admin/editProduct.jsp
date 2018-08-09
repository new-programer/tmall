<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="java.util.*"%>
   
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品</title>


<script>
	$(function()
		{
			$("#editForm").submit(function()
				{
					if (!checkEmpty("productName","产品名称"))	
						return false;
/* 					if (!checkEmpty("productSubTitle","产品小标题"))	
						return false; */
					if (!checkNumber("originalPrice","原价"))	
						return false;
					if (!checkNumber("promotePrice","促销价"))	
						return false;
					if (!checkInt("productStock","库存"))	
						return false;
					return true;
				});
		});
</script>

<div class="workingArea">
 	<ol class="breadcrumb">
 		<li><a href="admin_category_list">所有分类</a></li>
 		<li><a href="admin_product_list?cid=${product.category.id}">${product.category.name}</a></li>
 		<li class="active">${product.name}</li>
 		<li class="active">编辑产品</li>
 	</ol>
 	
 	<div class="panel panel-warning editDiv">
 		<div class="panel-heading">编辑产品</div>
 		<div class="panel-body">
	 		<form action="admin_product_update" method="post" id="editForm">
	 			<table>
	 				<tr>
	 					<td>产品名称</td>
	 					<td><input type="text" id="productName" name="productName" value="${product.name}"class="form-control"></td>
	 				</tr>
	 				<tr>
	 					<td>产品小标题</td>
	 					<td><input type="text" id="productSubTitle" name="productSubTitle" value="${product.subTitle}"class="form-control"></td>	 					
	 				</tr>
	 				<tr>
	 					<td>原价</td>
	 					<td><input type="text" id="originalPrice" name="originalPrice" value="${product.originalPrice}"class="form-control"></td>	 					
	 				</tr>	 
	 				<tr>
	 					<td>促销价</td>
	 					<td><input type="text" id="promotePrice" name="promotePrice" value="${product.promotePrice}"class="form-control"></td>	 					
	 				</tr>
	 				<tr>
	 					<td>库存</td>
	 					<td><input type="text" id="productStock" name="productStock" value="${product.stock}"class="form-control"></td>	 	 					
	 				</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="cid" value="${product.category.id}">
							<input type="hidden" name="ptid" value="${product.id}">
							<button type="submit" class="btn btn-success">更新</button>
						</td>
					</tr>
	 			</table> 		
	 		</form>
 		</div>
    </div>
</div>