<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 

<c:set var="user" value="${sessionScope.user}"/>
 
<c:if test="${null ne user}"> 	 
<c:if test="${user.joinType == 'MANAGER'}">

<nav class="navbar navbar-inverse visible-xs">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="/">ES</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="${condition eq 'manager_main' ? 'active' : ''}"><a href="/manager/">통계</a></li>
		<li class="${condition eq 'add_goods' ? 'active' : ''}"><a href="/manager/add_goods">상품등록</a></li>
		<li><a href="#section3">Gender</a></li>
		<li><a href="#section3">Geo</a></li>
      </ul>
    </div>
  </div>
</nav>


</c:if>
</c:if>