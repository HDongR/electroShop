<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script> 
<c:set var="user" value="${sessionScope.user}"/>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button> 
			<a class="navbar-brand" href="/">ES</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Page 1 <span class="caret"></span></a>
					<ul class="dropdown-menu list-inline">
						<li><a href="#">Page 1-1</a></li>
						<li><a href="#">Page 1-2</a></li>
						<li><a href="#">Page 1-3</a></li>
					</ul></li>
				<li><a href="#">Page 2</a></li>
				<li><a href="#">Page 3</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				 
					<c:if test="${null eq user}"><li><a href="/user/login/"><span class="glyphicon glyphicon-user"></span> 로그인</a> 
						</li>
					</c:if>
					<c:if test="${null ne user}">
						<li>
						</li>
						
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.nickname} 회원님<span class="caret"></span></a>
							<ul class="dropdown-menu list-inline">
								<li><a href="/user/update/"><span class="glyphicon glyphicon-pencil"></span> 정보수정</a></li> 
								<c:choose>
							       <c:when test="${user.joinType == 'COMMON'}">
							          <li><a id ="common" href="#"><span class="glyphicon glyphicon-user"></span> es 로그아웃</a> </li>
							       </c:when>
							       <c:when test="${user.joinType == 'KAKAO'}">
							           <li><a id="kakao" href="#"><span class="glyphicon glyphicon-user"></span> kakao 로그아웃</a> </li>
							       </c:when>
							       <c:when test="${user.joinType == 'NAVER'}">
							           <li><a id="naver" href="#"><span class="glyphicon glyphicon-user"></span> naver 로그아웃</a> </li>
							       </c:when> 
							   	</c:choose>  
							</ul>
						</li>
					
					</c:if>
					 
  
				<li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> 장바구니</a></li>
			</ul>
		</div>
	</div>
</nav> 

<script>
$("#common").click(function(){ 
	location.href='/user/logout/';
}); 

$("#kakao").click(function(){
	var id = '<spring:eval expression="@config.getProperty('kakao')" />'; 
	Kakao.init(id);
	Kakao.Auth.logout();
	location.href='/user/logout/';
}); 

$("#naver").click(function(){
	location.href='/user/logout/';
});
 
</script> 