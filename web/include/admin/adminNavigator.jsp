<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8" isELIgnored="false"%>

<div class = "navitagorDiv">
		<!-- navbar-fixed-top 作用是将导航栏始终固定在窗口顶部 -->
		<!-- navbar-inverse 作用是将导航栏反色（变成黑色背景） -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<!-- 
		src的地址内容之所以包括‘/tmall’，是因为在APACHE TOMCAT中配置了 server.xml的
		<Context reloadable="false" debug="0" docBase="G:\\JAVA EE\\tmall\\web" path="/tmall"/>
		中的path=“、”
		-->
		
		<img style="margin-left:10-px; margin-right:0px" class="pull-left" 
		src="/tmall/imgs/site/tmallbuy.png" height="45px">
		<!-- 加链接 -->
		<a class="navbar-brand" href="#nowhere">天猫后台</a>
		<a class="navbar-brand" href="#admin_category_list">分类管理</a>
		<a class="navbar-brand" href="#admin_user_list">用户管理</a>
		<a class="navbar-brand" href="#admin_order_list">订单管理</a>
	</nav>
</div>