<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<div class="container">
	<h2>장바구니 내 상품 리스트 </h2>  
	
    <h5> ${count}개의 장바구니 상품이 있습니다.</h5>
    <div class="btn-group btn-group-justified"> 
     
	<br/>
    
    <div class="row">
        <c:forEach var="cart" items="${cartList}"> 
 			<div class="col-sm-4">
	 			<div class="panel panel-primary">
	 				<a href="#" style="text-decoration:none">
			        <div class="panel-heading"><h4>상품명 : ${goods.goodsSubject}</h4></div>
			        <div align="center" class="panel-body">
			        		<img src="${goods.goodsMainPicUrl}" class="img-responsive" style="width:100%" alt="Image">
			        		<br/>
			        		<h4>가격 : <fmt:formatNumber pattern="#,###" value="${goods.goodsCost}"/> 원</h4> 
			        		<div class="row">
			        			<label class="bg-primary">재고 : ${goods.goodsStock} 개</label>
			        			 
			        		</div>
			        </div>
			        </a>
			        <div align="center" class="panel-footer">
			        <div class="row">
			        		<div class="col-sm-5">
				        		<input id="goodsCnt${goods.goodsSeq}" class="form-control" type="number" min="0" step="1" name="goodsCnt" value="0">				        		
			        		</div> 
			        		<button onclick="javascript:addCart('${goods.goodsSeq}')" class="btn btn-primary btn-link">장바구니에 담기</button>
			        		</div>
					</div>
		      	</div>
	   		</div>
  		</c:forEach> 
	</div>
    
    <br/>
    <div align="center">
	    <nav aria-label="Page navigation">
		  <ul class="pagination">
		
					<c:if test="${boardPager.curBlock > 1}">
						<li class="page-item">
					      <a class="page-link" href="javascript:list('1','${orderType}','${order}')" aria-label="First">
					        <span aria-hidden="true">&laquo;</span> 
					      </a>
					    </li> 
	                </c:if>
	                
	                <!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->
	                <c:if test="${boardPager.curBlock > 1}">
		                <li class="page-item">
					      <a class="page-link" href="javascript:list('${boardPager.prevPage}','${orderType}','${order}')" aria-label="Previous">
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
	                        		<li class="page-item"><a class="page-link" href="javascript:list('${num}','${orderType}','${order}')">${num}</a></li>
	                        </c:otherwise>
	                    </c:choose>
	                </c:forEach>
	                
	                <!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->
	                <c:if test="${boardPager.curBlock <= boardPager.totBlock}">
	                		<li class="page-item">
					      <a class="page-link" href="javascript:list('${boardPager.nextPage}','${orderType}','${order}')" aria-label="Next">
					        <span aria-hidden="true">&rsaquo;</span> 
					      </a>
					    </li> 
	                </c:if>
	                
	                <!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->
	                <c:if test="${boardPager.curPage <= boardPager.totPage}">
		                <li class="page-item">
					      <a class="page-link" href="javascript:list('${boardPager.totPage}','${orderType}','${order}')" aria-label="Last">
					        <span aria-hidden="true">&raquo;</span> 
					      </a>
					    </li> 
	                </c:if>
	                
	      </ul>
		</nav>
	</div>
</div>
