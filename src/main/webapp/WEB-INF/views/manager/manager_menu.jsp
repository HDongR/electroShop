<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<c:set var="user" value="${sessionScope.user}" />  
 
<c:if test="${null ne user}">
	<c:if test="${user.joinType == 'MANAGER'}">


		<div class="col-sm-3 sidenav hidden-xs">
			<h2>
				<a style="text-decoration: none" href="/">ES</a>
			</h2>
			<ul class="nav nav-pills nav-stacked">
				<li class="${condition eq 'manager_main' ? 'active' : ''}"><a href="/manager/">통계</a></li>
				<li class="${condition eq 'add_goods' ? 'active' : ''}"><a href="/manager/add_goods">상품등록</a></li>
				<li><a href="#section3">Gender</a></li>
				<li><a href="#section3">Geo</a></li>
			</ul>
			<br>
		</div>
		<br>
	</c:if>
</c:if>
 