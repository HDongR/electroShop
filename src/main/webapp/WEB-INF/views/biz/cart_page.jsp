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
		 	  <td style="vertical-align:middle"><input id="cartCnt${cart.key}" type="number" step="1" min="1" max="9999" style="width: 50px;" value="${cart.value.cartGoodsCnt>9999? 9999:cart.value.cartGoodsCnt}" ></td>
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
    
    <div align="right">
		<button type="button" class="btn btn-primary btn-lg" onclick="#">구매하기</button>
	 	<button type="button" class="btn btn-info btn-lg" onclick="javascript:void(window.open('http://localhost:8080','win0','width=700,height=600,status=no,toolbar=no,scrollbars=yes'))">견적서출력</button> 
	 	 
	</div>
 
    <br/>  
    
    <h3>이용안내</h3>
    	<ul>
    		<li><p class="text-danger">상품 발송예정일은 평균적인 예상 발송일이며, 상품 준비기간, 납기지연,품절,통관,기타 등 사정에 따라 발송예정일은 변동될 수 있습니다. [발송예정일-결제(입금)일 기준]</p></li>
  		<li><p class="text-danger">국내+해외상품 구매시 해외상품 평균준비기간으로 상품 발송예정일이 표시됩니다.</p></li>
  		<li><p class="text-danger">주의 - 주문 결제 후 업체 사정으로 인한 품절, 판매종료(단종), 납기지연이 발생할 수 있습니다.</p></li>
  		<li><p class="text-danger">주의 - 품절, 판매종료, 납기지연 상품의 경우 장바구니에서 삭제될 수 있습니다.</p></li>
  		<li><p class="text-muted">디바이스마트의 총 결제금액은 상품 가격 금액의 부가세(VAT) 10%를 포함한 가격입니다.</p></li>
  		<li><p class="text-muted">장바구니에 담겨 있는 상품을 주문하시려면 "주문하기" 버튼을 눌러주시기 바랍니다.</p></li>
  		<li><p class="text-muted">장바구니 수량 칸의 숫자를 변경하시면 자동으로 반영됩니다. 상품을 삭제하실 때에는 체크박스 버튼을 눌르고 삭제하기 버튼을 눌러 주시기 바랍니다.</p></li>
    </ul>
    
     


    
</div>
 

<!-- 개수에 따른 가격표기 -->
<script type="text/javascript">

	//초기에 모든가격 표기
	$(document).ready(function () { 
		allGoodsCostMake(); 
	});  
	
	$("input[type=number]").on('change keyup', function(event) {
		oneGoodsAllCostSum(event.target.id.substring(7,), event.target.value);
	}); 
	   
	   
	
	//한상품의 개수에 따른 가격표시 및 개수업데이트
	function oneGoodsAllCostSum(cartKey, goodsCnt){
		if(goodsCnt < 1 || goodsCnt > 9999){
			alert("1개이상 9999개 이하로 입력해주세요");
			$("#cartCnt"+cartKey).val(1);
			updateCart(cartKey, 1); 
		}else{ 
			updateCart(cartKey, goodsCnt);
		}
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
	
	//장바구니 가격 계산 후 출력(총가격 포함)
	function allGoodsCostMake(){
		var numbers = $("input[type=number]");
		var allSum=0;
		var allGoodsSum=0;
		var tax=0;
		if(numbers.length > 0){
			for(i=0; i<numbers.length; i++){
				var goodsCnt = Number(numbers.get(i).value);
				
				var cartKey = numbers.get(i).id.substring(7,);
				var leng = $("#goodsCost"+cartKey).html().length;
				var goodsCost = deleteDelimeterToCost($("#goodsCost"+cartKey).html().substring(0, leng-1)); 
				
				var sum = goodsCnt * Number(goodsCost);
				$("#oneGoodsAllCost"+cartKey).html(Number(sum.toFixed(1)).toLocaleString('ko') + ' 원');
				allGoodsSum+=sum; 
			} 
			tax=Math.round(allGoodsSum/10);
			allSum=allGoodsSum+tax; 
			
			$("#allGoodsCost").html('총구매금액 : ' + Number(allSum.toFixed(1)).toLocaleString('ko') + ' 원 (상품가격 : '+
					Number(allGoodsSum.toFixed(1)).toLocaleString('ko') + ' 원 + 부가세 : ' + 
					Number(tax.toFixed(1)).toLocaleString('ko') + ' 원)');
		}
	}
	
	//장바구니 개수 업데이트 
	function updateCart(cartKey, goodsCnt){
		if(goodsCnt > 0 && goodsCnt < 10000){
			$.post("/cart/updateCart", {
				cartGoodsSeq : cartKey,
				cartGoodsCnt : goodsCnt
 			}, function(data,status){
 				if(status == "success"){
 					if(data == "error"){
 						alert("다시시도해주세요");
 					}else if(data == "validError"){
 						alert("알맞게 다시 입력하세요");
 					}else if(data == "success"){
 						 
 						allGoodsCostMake();
 					}
 				}else{
 					alert("다시시도해주세요");
 				}
 			});
		}else{
			alert("1개이상 9999개 이하로 입력해주세요"); 
		}
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