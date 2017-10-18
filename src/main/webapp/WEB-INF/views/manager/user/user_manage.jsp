<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
 
<div class="container col-sm-10">
	<h2>등록된 유저 리스트</h2>  
	<form name="form1" method="post" action="/manager/user/user_manage_page">
	 
        <select class="selectpicker" name="searchOption">
            <!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
            <option value="allUser" <c:out value="${searchOption == 'all'?'selected':''}"/> >이메일+닉네임</option>
            <option value="email" <c:out value="${searchOption == 'user_email'?'selected':''}"/> >이메일</option>
            <option value="nickname" <c:out value="${searchOption == 'user_nickname'?'selected':''}"/> >닉네임</option>
        </select>
        <div class="input-group col-sm-6">
	      <input type="text" class="form-control" placeholder="이메일이나 닉네임을 검색하세요" name="keyword" value="${keyword}">
	      <div class="input-group-btn">
	        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
	      </div>
	    </div>
    </form>
    <h5> ${count}명의 유저가 있습니다.</h5>
    
    <br/>
    
	<div class="text-center">  
		<table class="table">
	    <thead>
	      <tr> 
	        <th class="text-center ">이메일</th>
	        <th class="text-center ">핸드폰번호</th>
	        <th class="text-center ">주소지</th>
	        <th class="text-center ">이름</th> 
	        <th class="text-center ">닉네임</th> 
	        <th class="text-center ">유저타입</th> 
	        	<th class="text-center ">가입일</th> 
	        	<th class="text-center "></th> 
	      </tr>
	    </thead>
	    <tbody>
	    	  <c:forEach items="${list}" var="user">  
	    	    <tr> 
		 	  <td style="vertical-align:middle">${user.email}</td>
		 	  <td style="vertical-align:middle">${user.phoneNum}</td>
		 	  <td style="vertical-align:middle">${user.addrCity} ${user.addrArea} ${user.addrDetail}</td>
		 	  <td style="vertical-align:middle">${user.name}</td>
		 	  <td style="vertical-align:middle">${user.nickname}</td>
		 	  <td style="vertical-align:middle">${user.joinType}</td>
		 	  <td style="vertical-align:middle"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.joinDate}"/></td>
		 	  <td style="vertical-align:middle"><a href="javascript:page_move('/manager/user/modify_user_page', '${user.email}');"  class="btn btn-link">수정</a>
		
		 	  
		 	  </td>
		 	</tr>
		  </c:forEach>  
	    </tbody>
	    </table> 
	</div>
	
	 	  <form name="goLink">
		 	  	<input type="hidden" name="email"/>
		 	  </form>
	
    <nav aria-label="Page navigation">
	  <ul class="pagination">
	
				<c:if test="${boardPager.curBlock > 1}">
					<li class="page-item">
				      <a class="page-link" href="javascript:list('1')" aria-label="First">
				        <span aria-hidden="true">&laquo;</span> 
				      </a>
				    </li> 
                </c:if>
                
                <!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->
                <c:if test="${boardPager.curBlock > 1}">
	                <li class="page-item">
				      <a class="page-link" href="javascript:list('${boardPager.prevPage}')" aria-label="Previous">
				        <span aria-hidden="true">&lsaquo;</span> 
				      </a>
				    </li> 
                </c:if>
                
                <!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->
                <c:forEach var="num" begin="${boardPager.blockBegin}" end="${boardPager.blockEnd}">
                    <!-- **현재페이지이면 하이퍼링크 제거 -->
                    <c:choose>
                        <c:when test="${num == boardPager.curPage}">
                       		<li class="page-item active"><a class="page-link" href="#">${num}</a></li>
                        </c:when>
                        <c:otherwise>
                        		<li class="page-item"><a class="page-link" href="javascript:list('${num}')">${num}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                
                <!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->
                <c:if test="${boardPager.curBlock <= boardPager.totBlock}">
                		<li class="page-item">
				      <a class="page-link" href="javascript:list('${boardPager.nextPage}')" aria-label="Next">
				        <span aria-hidden="true">&rsaquo;</span> 
				      </a>
				    </li> 
                </c:if>
                
                <!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->
                <c:if test="${boardPager.curPage <= boardPager.totPage}">
	                <li class="page-item">
				      <a class="page-link" href="javascript:list('${boardPager.totPage}')" aria-label="Last">
				        <span aria-hidden="true">&raquo;</span> 
				      </a>
				    </li> 
                </c:if>
                
      </ul>
	</nav>

</div>


<!-- 페이지처리 -->
<script type="text/javascript">
	function list(page){
	    location.href="/manager/user/user_manage_page?curPage="+page+"&searchOption=${searchOption}"+"&keyword=${keyword}";
	} 
</script>

<script type="text/javascript">
 
	function page_move(s_page,s_email){
		  var f=document.goLink;  //폼 name
		  console.log(f);
		  f.email.value = s_email;
		  f.action=s_page;  //이동할 페이지
		  f.method="post";  //POST방식
		  f.submit(); 
	}
</script>

