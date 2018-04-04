<%@page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav>
	<%-- <ul>标签为无序标签，<ol>标签为有序标签 --%>
	<ul class="pagination">  <%-- pagination 有页码标注的意思 --%>
		<%-- 首页  --%>
		<li>
			<a href="?page.start=0" aria-label="Previous">
				<span aria-hidden="true">首页 </span>
			</a>
		</li>
		
		<%-- 上一页  --%>
		<li>
			<a href="?page.start=${page.start - page.count}" aria-label="">
				<span aria-hidden="true">上一页</span>
			</a>
		</li>
		
		<%--- 分页标识 --%>
		<c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
			<li>
					<%--${status.index } 这个指的是当前这次迭代从 0 开始的迭代索引--%>
					<%--${status.count } 这个指的是当前这次迭代从 1 开始的迭代计数--%>
			<%--- status.index * page.count的含义是当前页开始的数据条次序，如有30条数据，
			若从第0条开始展示，每页5条，则 第1页第一条为0*5=0，第二页第一条为1*5=5，一次类推。。。
			--%>
				<a href="?page.start=${status.index * page.count}" class="current">${status.count}</a>
			</li>
		</c:forEach>
		
		<%-- 下一页  --%>
		<li>
			<a href="?page.start=${page.start + page.count}" aria-label="Next">
				<span aria-hidden="true">下一页</span>
			</a>
		</li>
		
		<%-- 末页  --%>
		<li>
			<a href="?page.start=${page.last}" aria-label="Next">
				<span aria-hidden="">末页</span>
			</a>
		</li>
	</ul>
</nav>