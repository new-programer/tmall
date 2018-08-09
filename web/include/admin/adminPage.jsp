<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
$(function()
	{
		$("ul.pagination li.disabled a").click(function()
				{
					return false;
				});
	}
);
</script>
<nav>
	<%-- <ul>标签为无序标签，<ol>标签为有序标签 --%>
	<%--
	每个分页跳转加传值的时候后面加${page.param}的目的是传值的时候：在传page.star值的同时也传cid=?(?是该属性所属类的id)的值
	<a href="?page.start=0${page.param}" aria-label="Previous">
	<a href="?page.start=${page.start - page.count}${page.param}" aria-label="Previous">
	<a href="?page.start=${status.index * page.count}${page.param}" class="current">${status.count}</a>
	<a href="?page.start=${page.start + page.count}${page.param}" aria-label="Next">
	<a href="?page.start=${page.last}${page.param}" aria-label="Next">
	 --%>
	<ul class="pagination">  <%-- pagination 有页码标注的意思 --%>
		<%-- 首页  --%>
		<li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>>
			<%-- <a href="?page.start=0" aria-label="Previous"> 中
				问号‘？’前表示传到的哪里的目的页面链接，若问号'?'前什么都没有则表示传到本面，问号'?'后面是代表要传送的内容
				点击数字0 的时候，会跳转到问号'?'前地址指向的这个页面
			--%>
			<a href="?page.start=0${page.param}" aria-label="Previous">
				<span aria-hidden="true">首页 </span>
			</a>
		</li>
		
		<%-- 上一页  --%>
		<li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>>
			<a href="?page.start=${page.start - page.count}${page.param}" aria-label="Previous">
				<span aria-hidden="true">上一页</span>
			</a>
		</li>
		
		<%--- 分页标识，注视掉的是自己写的 --%>
		<%-- 
		<c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
			<li>

				<a href="?page.start=${status.index * page.count}${page.param}" class="current">${status.count}</a>
			</li>
		</c:forEach>
		--%>
		
		
			<%-- ${status.index} 这个指的是当前这次迭代从 0 开始的迭代索引  --%>
			<%-- ${status.count} 这个指的是当前这次迭代从 1开始的迭代计数 --%>
			<%-- 
				status.index * page.count的含义是当前页开始的数据条次序，如有30条数据，
				若从第0条开始展示，每页5条，则 第1页第一条为0*5=0，第二页第一条为1*5=5，一次类推。。。
			--%>
			
			
		<%-- 下面的分页标识代码是how2j给出的，我现在还不理解为啥要添加那两个判断条件 ？
		如果不添加这两个判断即使在第一页“上一页”图标并没有变灰色，在最后一页“下一页”的图标也没有变灰色
		--%>
		
		<%-- 目前存在问题 end="${page.totalPage-1},end的值这样的话会出错，总的结果小于0了，不知为何 --%>
		<c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
		        <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
		            <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
		                <a 
		                href="?page.start=${status.index*page.count}${page.param}"
		                <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
		                >${status.count}</a>
		            </li>
		        </c:if>
   		</c:forEach>
   		
		<%-- 下一页  --%>
		<li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
			<a href="?page.start=${page.start + page.count}${page.param}" aria-label="Next">
				<span aria-hidden="true">下一页</span>
			</a>
		</li>
		
		<%-- 末页  --%>
		<li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
			<a href="?page.start=${page.last}${page.param}" aria-label="Next">
				<span aria-hidden="true">末页</span>
			</a>
		</li>
	</ul>
</nav>