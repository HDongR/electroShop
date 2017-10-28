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
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">TV<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/board/goods?goodsCatHighSeq=1&goodsCatMidSeq=1">Google</a></li>
						<li><a href="/board/goods?goodsCatHighSeq=1&goodsCatMidSeq=2">Apple</a></li> 
					</ul></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">NoteBook<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/board/goods?goodsCatHighSeq=2&goodsCatMidSeq=3">SamSung</a></li>
						<li><a href="/board/goods?goodsCatHighSeq=2&goodsCatMidSeq=5">Intel</a></li>
						<li><a href="/board/goods?goodsCatHighSeq=2&goodsCatMidSeq=4">LG</a></li>
					</ul></li>
				
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Comunity<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">자유게시판</a></li>
						<li><a href="#">질문게시판</a></li> 
					</ul></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				 
					<c:if test="${null eq user}">
						<li><a href="/user/login_page"><span class="glyphicon glyphicon-user"></span> 로그인</a> 
						</li>
					</c:if>
					<c:if test="${null ne user}"> 
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.userNickname} 
						
						<c:choose>
							<c:when test="${user.userJoinType == 'MANAGER'}">
									관리자님
							</c:when>
									<c:otherwise>
									회원님
							</c:otherwise>
						</c:choose>
						
						<span class="caret"></span></a>
							<ul class="dropdown-menu list-inline">
								<li><a href="javascript:page_move('/user/modify_user_page', '${user.userEmail}');"><span class="glyphicon glyphicon-pencil"></span> 정보수정</a></li> 
								<c:choose>
									<c:when test="${user.userJoinType == 'MANAGER'}">
										<li><a href="manager/"><span class="glyphicon glyphicon-cog"></span> 관리자 페이지</a> </li>
								  		<li><a id ="manager" href="#"><span class="glyphicon glyphicon-user"></span> 관리자 로그아웃</a> </li>
								   	</c:when>
									<c:when test="${user.userJoinType == 'COMMON'}">
								  		<li><a id ="common" href="#"><span class="glyphicon glyphicon-user"></span> es 로그아웃</a> </li>
								   	</c:when>
								   	<c:when test="${user.userJoinType == 'KAKAO'}">
										<li><a id="kakao" href="#"><span class="glyphicon glyphicon-user"></span> kakao 로그아웃</a> </li>
									</c:when>
								    	<c:when test="${user.userJoinType == 'NAVER'}">
										<li><a id="naver" href="#"><span class="glyphicon glyphicon-user"></span> naver 로그아웃</a> </li>
								   	</c:when> 
							   	</c:choose>  
							</ul>
						</li>
					
					</c:if>
					 
  				<c:if test="${user.userJoinType != 'MANAGER'}">
					<li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> 장바구니</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</nav> 

<form name="goLink">
	<input type="hidden" name="userEmail"/>
</form>

<script>
$("#manager").click(function(){ 
	location.href='/user/logout';
}); 

$("#common").click(function(){ 
	location.href='/user/logout';
}); 

$("#kakao").click(function(){
	var id = '<spring:eval expression="@config.getProperty('kakao')" />'; 
	Kakao.init(id);
	Kakao.Auth.logout();
	location.href='/user/logout';
}); 

$("#naver").click(function(){
	location.href='/user/logout';
});

function page_move(s_page,s_email){
	  var f=document.goLink;  //폼 name 
	  f.userEmail.value = s_email;
	  f.action=s_page;  //이동할 페이지
	  f.method="post";  //POST방식
	  f.submit(); 
}
 
</script> 