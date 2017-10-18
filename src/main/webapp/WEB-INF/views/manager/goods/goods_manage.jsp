<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>

<div class="container col-sm-10">

	<h2>등록된 상품 리스트 </h2>  
	<form name="form1" method="post" action="/manager/goods/goods_manage_page">
	 
        <select class="selectpicker" name="searchOption">
            <!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
            <option value="allGoods" <c:out value="${searchOption == 'allGoods'?'selected':''}"/> >제목+내용</option>
            <option value="subject" <c:out value="${searchOption == 'goods_subject'?'selected':''}"/> >이름</option>
            <option value="contents" <c:out value="${searchOption == 'goods_contents'?'selected':''}"/> >내용</option>
        </select>
        <div class="input-group col-sm-6">
	      <input type="text" class="form-control" placeholder="제목이나 내용을 검색하세요" name="keyword" value="${keyword}">
	      <div class="input-group-btn">
	        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
	      </div>
	    </div>
    </form>
    <h5> ${count}개의 상품이 있습니다.</h5>
   
    <button id="goodsDelete" class="btn btn-danger btn-md" data-toggle="modal" data-target="#deleteModal">삭제하기</button>
    
    <!-- Modal -->
 	<div class="modal fade" id="deleteModal" role="dialog">
	    <div class="modal-dialog">
	    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">상품삭제</h4>
	        </div>
	        <div class="modal-body">
	          <p>정말 삭품(들)을 삭제하시겠습니까</p>
	        </div>
	       
	        <div class="modal-footer">
	          <button id="goodsDeleteOK" type="button" class="btn btn-default" data-dismiss="modal">삭제</button>
	          <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
	        </div>
	      </div>
	      
	    </div>
    </div>
  
    
	<br/>
	<div class="text-center">  
		<table class="table">
	    <thead>
	      <tr>
	        <th class="text-center "><input id="allCheck" type="checkbox"></th>
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
	    	    	  <td style="vertical-align:middle"><input name="isChecked" type="checkbox" value="${goods.goodsSeq}"></td>
		 	  <td style="vertical-align:middle">${goods.goodsSeq}</td>
		 	  <td style="vertical-align:middle"><img src="${goods.mainPicUrl}" width="100"></img></td>
		 	  <td style="vertical-align:middle">${goods.subject}</td>
		 	  <td style="vertical-align:middle"><fmt:formatNumber pattern="#,###" value="${goods.cost}"/> 원</td>
		 	  <td style="vertical-align:middle"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${goods.crtDate}"/></td>
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
	
<!-- 페이지처리 -->
<script type="text/javascript">
	function list(page){
	    location.href="/manager/goods/goods_manage_page?curPage="+page+"&searchOption=${searchOption}"+"&keyword=${keyword}";
	} 
</script>

<!-- 체크박스 및 삭제처리 -->
<script type="text/javascript">
	$("#goodsDelete").hide();
	
	$("input[type=checkbox]").click(function(event){
		 
		if(event.target.id == 'allCheck'){
			if($("#allCheck").prop("checked")){ 
				$("input[type=checkbox]").prop("checked", true);
			}else{
				$("input[type=checkbox]").prop("checked", false);
			}
		}else{
			if($("#allCheck").prop("checked")){ 
				$("#allCheck").prop("checked", false);
			}
		}
		
		if(isCheckedAllComponent()){
			$("#goodsDelete").show();
		}else{
			$("#goodsDelete").hide();
		}
	});
	
	function isCheckedAllComponent(){ 
		var checkBoxs = $("input[type=checkbox]");
		for(i=0; i<checkBoxs.length; i++){
			if(checkBoxs.get(i).checked){
				return true;	
			} 
		} 
		return false;
	}
	
	$("#goodsDeleteOK").click(function(event){
		$("#goodsDelete").prop("disabled", true);
		
		var checkBoxs = $("input[type=checkbox]");
		var goodsSeqList = [];
		for(i=0; i<checkBoxs.length; i++){
			if(checkBoxs.get(i).checked && checkBoxs.get(i).id != 'allCheck'){ 
				goodsSeqList.push(checkBoxs.get(i).value); 
			} 
		} 
		
		deletePost(goodsSeqList);
		 
	});
	
	function deletePost(_goodsSeqList){
		$.post('/manager/goods/delete_goods', 
				{goodsSeqList : _goodsSeqList},
				function(data, status){ 
					if(status == 'success'){ 
						if(data == 'completeDeleteGoods'){ 
							window.location.reload();
						} else if(data == 'databaseError'){
						 	alert("databaseError")
						} else if(data == 'retryPlz'){
							alert("다시 시도하세요");
						}
					}else{
						alert("다시 시도하세요");
					} 
					
					$("#goodsDelete").prop("disabled", false);
		}); 
	}

</script>