<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<script type="text/javascript" src="/resources/js/date_utils.js/"></script> 
    
<div class="container">
	<h2>카테고리 내 상품 리스트 </h2>  
	<form name="form1" method="post" action="/board/goods">
	 
        <select class="selectpicker" name="searchOption">
            <!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
            <option value="allGoods" <c:out value="${searchOption == 'allGoods'?'selected':''}"/> >제목+내용</option>
            <option value="goods_subject" <c:out value="${searchOption == 'goods_subject'?'selected':''}"/> >이름</option>
            <option value="goods_contents" <c:out value="${searchOption == 'goods_contents'?'selected':''}"/> >내용</option>
        </select> 
        <input type="hidden" name="goodsCatHighSeq" value="${goodsCatHighSeq}">
        <input type="hidden" name="goodsCatMidSeq" value="${goodsCatMidSeq}">
        <div class="input-group col-sm-6">
	      <input type="text" class="form-control" placeholder="제목이나 내용을 검색하세요" name="keyword" value="${keyword}">
	      <div class="input-group-btn">
	        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
	      </div>
	    </div>
    </form>
    <h5> ${count}개의 상품이 있습니다.</h5>
    <div class="btn-group btn-group-justified"> 
    
		<!-- 인기순 -->
		<c:if test="${(order == 'DESC' && orderType == 'SCORE') || orderType != 'SCORE'}">
			<a href="javascript:list('1','SCORE', 'ASC')" class="btn btn-primary">인기순 <span class="glyphicon glyphicon-triangle-bottom"></span></a> 
		</c:if>
		<c:if test="${order == 'ASC' && orderType == 'SCORE'}">
			<a href="javascript:list('1','SCORE', 'DESC')" class="btn btn-primary">인기순 <span class="glyphicon glyphicon-triangle-top"></span></a> 
		</c:if>
				 
		<!-- 신상품순 -->
		<c:if test="${(order == 'DESC' && orderType == 'DATE') || orderType != 'DATE'}">
			<a href="javascript:list('1','DATE', 'ASC')" class="btn btn-primary">신상품순 <span class="glyphicon glyphicon-triangle-bottom"></span></a> 
		</c:if>
		<c:if test="${order == 'ASC' && orderType == 'DATE'}">
			<a href="javascript:list('1','DATE', 'DESC')" class="btn btn-primary">신상품순 <span class="glyphicon glyphicon-triangle-top"></span></a> 
		</c:if>
		 
		<!-- 이름순 -->
		<c:if test="${(order == 'DESC' && orderType == 'SUBJECT') || orderType != 'SUBJECT'}">
			<a href="javascript:list('1','SUBJECT', 'ASC')" class="btn btn-primary">이름순 <span class="glyphicon glyphicon-triangle-bottom"></span></a> 
		</c:if>
		<c:if test="${order == 'ASC' && orderType == 'SUBJECT'}">
			<a href="javascript:list('1','SUBJECT', 'DESC')" class="btn btn-primary">이름순 <span class="glyphicon glyphicon-triangle-top"></span></a> 
		</c:if>
		
		<!-- 가격순 -->
		<c:if test="${(order == 'DESC' && orderType == 'COST') || orderType != 'COST'}">
			<a href="javascript:list('1','COST', 'ASC')" class="btn btn-primary">가격순 <span class="glyphicon glyphicon-triangle-bottom"></span></a> 
		</c:if>
		<c:if test="${order == 'ASC' && orderType == 'COST'}">
			<a href="javascript:list('1','COST', 'DESC')" class="btn btn-primary">가격순 <span class="glyphicon glyphicon-triangle-top"></span></a> 
		</c:if>
		
	</div>
	<br/>
    
    <div class="row">
        <c:forEach var="goods" items="${list}"> 
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
				        		<input id="goodsCnt${goods.goodsSeq}" class="form-control" type="number" min="1" max="9999" step="1" name="goodsCnt" value="1">				        		
			        		</div> 
			        		<button onclick="javascript:upsertCart('${goods.goodsSeq}')" class="btn btn-primary btn-link">장바구니에 담기</button>
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

<!-- 장바구니로 등록 -->
<script type="text/javascript">
 	function upsertCart(goodsSeq){
 		var goodsCnt = $("#goodsCnt"+goodsSeq).val();
 		if(goodsCnt < 1){
 			alert("1개 이상 등록하세요")
 		}else if(goodsCnt > 10000){
 			alert("10000개 이상 등록할 수 없습니다")
 			$("#goodsCnt"+goodsSeq).val(1);
 		}else{
 			$.post("/cart/upsertCart", {
 				cartGoodsSeq : goodsSeq,
 				cartGoodsCnt : goodsCnt,
 				cartCrtDate : now()
 			}, function(data,status){
 				if(status == "success"){
 					if(data == "error"){
 						alert("다시시도해주세요");
 					}else{
 						location.reload();
 					}
 				}else{
 					alert("다시시도해주세요");
 				}
 			});
 		}
 	}
</script>

<!-- 정렬옵션 -->
<script type="text/javascript">
	function list(page, orderType, order){
		location.href="/board/goods?curPage="+page+"&searchOption=${searchOption}"+"&keyword=${keyword}"+"&goodsCatHighSeq=${goodsCatHighSeq}" + "&goodsCatMidSeq=${goodsCatMidSeq}" + "&orderType="+orderType+"&order="+order;         
	} 
</script>