<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<div class="container col-sm-10">

	<h2>등록된 상품 리스트 </h2> 
	<h5>총 ${count}개의 상품이 있습니다.</h5>
	
	 <form name="form1" method="post" action="/manager/goods/goods_manage_page">
        <select name="searchOption">
            <!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
            <option value="all" <c:out value="${searchOption == 'all'?'selected':''}"/> >제목+내용</option>
            <option value="subject" <c:out value="${searchOption == 'subject'?'selected':''}"/> >이름</option>
            <option value="contents" <c:out value="${searchOption == 'contents'?'selected':''}"/> >내용</option>
        </select>
        <input name="keyword" value="${keyword}">
        <input type="submit" value="조회"> 
    </form>
    
   
    
	<br/>
	   <div class="text-center">  
		<table class="table">
	    <thead>
	      <tr>
	        <th class="text-center "><input type="checkbox"></th>
	        <th class="text-center ">번호</th>
	        <th class="text-center ">이미지</th>
	        <th class="text-center ">이름</th>
	        <th class="text-center ">가격</th> 
	        <th class="text-center ">등록일</th> 
	        	<th class="text-center "></th> 
	      </tr>
	    </thead>
	    <tbody>
	    	  <c:forEach items="${list}" var="goods">  
	    	    <tr>
	    	    	  <td style="vertical-align:middle"><input type="checkbox"></td>
		 	  <td style="vertical-align:middle">${goods.goodsSeq}</td>
		 	  <td style="vertical-align:middle"><img src="${goods.mainPicUrl}" width="100"></img></td>
		 	  <td style="vertical-align:middle">${goods.subject}</td>
		 	  <td style="vertical-align:middle"><fmt:formatNumber pattern="#,###" value="${goods.cost}"/> 원</td>
		 	  <td style="vertical-align:middle"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${goods.joinDate}"/></td>
		 	  <td style="vertical-align:middle"><a href="/manager/goods/modify_goods_page/${goods.goodsSeq}"  class="btn btn-link">수정</a></td>
		 	</tr>
		  </c:forEach>  
	    </tbody>
	    </table> 
	</div> 
	
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
	 
	
<script type="text/javascript">
	function list(page){
	    location.href="/manager/goods/goods_manage_page?curPage="+page+"&searchOption-${searchOption}"+"&keyword=${keyword}";
	}
</script>