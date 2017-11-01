<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<div class="container">
	<h2>장바구니 내 상품 리스트 </h2>  
	
    <h5> ${cartList.size()}개의 장바구니 상품이 있습니다.</h5>
     
    <button id="cartDeleteBtn" class="btn btn-danger btn-md" data-toggle="modal" data-target="#deleteModal">삭제하기</button>
    
    <!-- Modal -->
 	<div class="modal fade" id="deleteModal" role="dialog">
	    <div class="modal-dialog">
	    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">장바구니 상품 삭제</h4>
	        </div>
	        <div class="modal-body">
	          <p>정말 장바구니 상품(들)을 삭제하시겠습니까</p>
	        </div>
	       
	        <div class="modal-footer">
	          <button id="cartDeleteOK" type="button" class="btn btn-default" data-dismiss="modal">삭제</button>
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
	        <th class="text-center ">상품명</th>
	        <th class="text-center ">가격</th>
	        <th class="text-center ">수량</th>
	        <th class="text-center ">합계</th>
	        <th class="text-center ">배송비</th> 
	        <th class="text-center ">평균준비기간</th>
	      </tr>
	    </thead> 
	    <tbody>
	    	  <c:forEach items="${cartList}" var="cart" varStatus="status">  
	    	    <tr>
	    	    	  <td style="vertical-align:middle"><input name="isChecked" type="checkbox" value="${cart.key}"></td>
		 	  <td style="vertical-align:middle"><img src="${cart.value.goodsVO.goodsMainPicUrl}" width="80"/>${cart.value.goodsVO.goodsSubject}</td>
		 	  <td id="goodsCost${cart.key}" style="vertical-align:middle"><fmt:formatNumber pattern="#,###" value="${cart.value.goodsVO.goodsCost}"/> 원</td>
		 	  <td style="vertical-align:middle"><input id="cartCnt${cart.key}"  onfocusout="javascript:oneGoodsAllCostSum('${cart.key}','');" type="number" step="1" min="0" value="${cart.value.cartGoodsCnt}" style="width: 50px;"></td>
		 	  <td id="oneGoodsAllCost${cart.key}" style="vertical-align:middle"><fmt:formatNumber pattern="#,###" value="${cart.value.goodsVO.goodsCost * cart.value.cartGoodsCnt}"/> 원</td>
		 	  <td style="vertical-align:middle">기본배송</td>
		 	  <td style="vertical-align:middle">2~3일</td>
		 	</tr>
		  </c:forEach>  
	    </tbody>
	  	<tfoot>
	    		<tr>
	    			<td colspan="7" align="right">
		    			<h5 id="allGoodsCost"></h5>
		    		</td>
	    		</tr>
	    </tfoot>
	    </table> 
	</div>  
	
	 
    
    <br/> 
</div>
 

<!-- 개수에 따른 가격표기 -->
<script type="text/javascript">
	$("input[type=number]").click(function(event){
		oneGoodsAllCostSum(event.target.id.substring(7,), event.target.value);
	});
	
	//한상품의 개수에서 
	function oneGoodsAllCostSum(cartKey, goodsCnt){
		if(goodsCnt == ''){
			goodsCnt = $("#cartCnt"+cartKey).val();
		}
		var leng = $("#goodsCost"+cartKey).html().length;
		var goodsCost = deleteDelimeterToCost($("#goodsCost"+cartKey).html().substring(0, leng-1)); 
		var sum = Number(goodsCost) * Number(goodsCnt);
		 
		if(sum > 0){
			$("#oneGoodsAllCost"+cartKey).html(Number(sum.toFixed(1)).toLocaleString('ko') + ' 원');
		}else{
			$("#cartCnt"+cartKey).val(0);
			$("#oneGoodsAllCost"+cartKey).html('0 원');
		}
		
		allGoodsCostMake();
	}
	
	//가격에서 ','을 삭제후 숫자만 리턴
	function deleteDelimeterToCost(goodsCost){
		var result = '';
		for(var i=0; i<goodsCost.length; i++){
			if(goodsCost[i] != ','){
				result+=goodsCost[i];
			}
		}
		return result;
	}
	
	//초기에 모든가격 표기
	$(document).ready(function () { 
		allGoodsCostMake(); 
	}); 
	
	function allGoodsCostMake(){
		var numbers = $("input[type=number]");
		var allSum=0;
		
		for(i=0; i<numbers.length; i++){
			var goodsCnt = Number(numbers.get(i).value);
			
			var cartKey = numbers.get(i).id.substring(7,);
			var leng = $("#goodsCost"+cartKey).html().length;
			var goodsCost = deleteDelimeterToCost($("#goodsCost"+cartKey).html().substring(0, leng-1)); 
			
			var sum = goodsCnt * Number(goodsCost);
			allSum+=sum;
		} 
		 
		$("#allGoodsCost").html('총구매금액 : ' + Number(allSum.toFixed(1)).toLocaleString('ko') + ' 원');
	}
	
</script>

<!-- 체크박스 및 삭제처리 -->
<script type="text/javascript">
	$("#cartDeleteBtn").hide();
	
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
			$("#cartDeleteBtn").show();
		}else{
			$("#cartDeleteBtn").hide();
		}
	});
	
	function isCheckedAllComponent(){ 
		var checkBoxs = $("input[type=checkbox]");
		for(i=0; i<checkBoxs.length; i++){
			if(checkBoxs.get(i).checked && checkBoxs.get(i).id != 'allCheck'){
				return true;	
			} 
		} 
		return false;
	}
	
	$("#cartDeleteOK").click(function(event){
		$("#cartDeleteBtn").prop("disabled", true);
		
		var checkBoxs = $("input[type=checkbox]");
		var cartSeqList = [];
		for(i=0; i<checkBoxs.length; i++){
			if(checkBoxs.get(i).checked && checkBoxs.get(i).id != 'allCheck'){ 
				cartSeqList.push(checkBoxs.get(i).value); 
			} 
		} 
		
		deletePost(cartSeqList);
		 
	});
	
	function deletePost(_cartSeqList){
		$.post('/cart/deleteCart', 
				{
					cartSeqList : _cartSeqList
				},
				function(data, status){ 
					if(status == 'success'){ 
						if(data == 'completeDeleteCart'){ 
							window.location.reload();
						} else if(data == 'Error'){
						 	alert("Error")
						}
					}else{
						alert("다시 시도하세요");
					} 
					
					$("#cartDeleteBtn").prop("disabled", false);
		}); 
	}

</script>