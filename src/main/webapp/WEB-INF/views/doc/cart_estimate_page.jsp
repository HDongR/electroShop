<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
 
<div>
	<div class="container" style="border:1px solid rgba(80, 80, 80, 0.3); padding-top: 10px;padding-bottom: 10px; margin: 8px">
		<p class="bg-primary" style="text-align: center; font-size: 20px; padding: 5px;">견적서</p>
		<div class="row">
			<div class="col-xs-5" style="font-size: 12px; text-align: right;">
				<table class="table">
					<thead>
						<tr>
							<td>견적일 :</td>
							<td id="estimate_date"></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>이름 :</td>
							<td><input type="text" maxlength="20"></td>
						</tr>
						<tr>
							<td>견적금액 :</td>
							<td><p id="estimate_allCost"></p>(부과세포함, 운송료별도)</td>
						</tr>
						<tr>
							<td>전화번호 :</td>
							<td><input type="text" maxlength="15"></td>
						</tr>
						<tr>
							<td>팩스번호 :</td>
							<td><input type="text" maxlength="15"</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-xs-7" style="font-size: 12px; text-align: left;">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="active" align="center" style="vertical-align: middle;" rowspan="5">공<br/><br/>급<br/><br/>자</td>
							<td class="active">등록번호</td>
							<td colspan="3" style="text-align: center; font-weight: bolder;">101-35-23132</td>
						</tr>
						<tr> 
							<td class="active">상호</td>
							<td>ElectroShop</td>
							<td class="active">대표</td>
							<td>유호동</td>
						</tr>
						<tr> 
							<td class="active">사업장주소</td>
							<td colspan="3">세종시 연서면 대첩로</td>
						</tr>
						<tr> 
							<td class="active">업태</td>
							<td>도,소매,제조,서비스</td>
							<td class="active">종목</td>
							<td>전기,전자부품</td>
						</tr>
						<tr> 
							<td class="active">전화</td>
							<td>010-2967-9441</td>
							<td class="active">팩스</td>
							<td>-</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	
		<h6>다음과 같이 견적합니다</h6>
		
		<div>
			<table class="table table-condensed" style="text-align: center; font-size: 12px; color: #202020;">
				<thead>
					<tr class="info">
						<th style="text-align: center;">No.</th>
						<th style="text-align: center;">제품명</th>
						<th style="text-align: center;">상품번호</th>
						<th style="text-align: center;">단가</th>
						<th style="text-align: center;">수량</th>
						<th style="text-align: center;">합계</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cartList}" var="cart" varStatus="status">
				    		<tr>
				    	    		<td>${status.count}</td>
					 	  	<td>${cart.goodsVO.goodsSubject}</td>
					 	  	<td class="seq">${cart.cartGoodsSeq}</td>
					 	  	<td style="text-align: right" id="goodsCost${cart.cartGoodsSeq}"><fmt:formatNumber pattern="#,###" value="${cart.goodsVO.goodsCost}"/> 원</td>
					 	  	<td id="goodsCnt${cart.cartGoodsSeq}">${cart.cartGoodsCnt}</td>
					 	  	<td style="text-align: right"><fmt:formatNumber pattern="#,###" value="${cart.goodsVO.goodsCost * cart.cartGoodsCnt}"/> 원</td>
					 	</tr>
					</c:forEach>  
				</tbody>
				<tfoot align="right">
					<tr>
						<td colspan="4">공급가액:</td>
						<td colspan="2" id="supplyValue"></td>
					</tr>
					<tr>
						<td colspan="4">부가세:</td>
						<td colspan="2" id="tax"></td>
					</tr>
					<tr class="info">
						<td colspan="4">견적총액:</td>
						<td colspan="2" id="totalEstimate"></td>
					</tr>
				</tfoot>
			</table>
		</div>
		
		<div class="container" style="font-size: 12px">
			<p>※	가격정보가 수시로 변하므로 <font color="red">제품구매시에는 최종 단가를 다시 확인</font>하시기 바랍니다.</p>
			<p>※	부가세 포함가격이며, 배송비용은 별도입니다.</p>
			<p>※	담당자 : 온라인출력</p>
			<p>※	ES Shop 고객센터 : 010-2967-9441</p>
			<p>※	A4 용지에 인쇄시 좌, 우 여백을 10mm로 설정후 인쇄하시기 바랍니다.</p>
		</div> 
	</div>
	<br/>
	<div class="row" align="center">
		<button class="btn btn-primary btn-md" type="button" onclick="javascript:window.print()">프린트</button>
		<button class="btn btn-info btn-md" type="button" onclick="javascript:window.close()">창닫기</button>
	</div>
	<br/>
</div>

<script type="text/javascript">
	 
	$(document).ready(function () { 
		//견적일
		$("#estimate_date").html(now_short());
		
		//견적금액
		$("#estimate_allCost").html(Number(allGoodsCostMake(2).toFixed(1)).toLocaleString('ko') + ' 원');

		//공급가액 
		$("#supplyValue").html(Number(allGoodsCostMake(0).toFixed(1)).toLocaleString('ko') + ' 원');
		
		//부가세
		$("#tax").html(Number(allGoodsCostMake(1).toFixed(1)).toLocaleString('ko') + ' 원');
		
		//견적총액
		$("#totalEstimate").html(Number(allGoodsCostMake(2).toFixed(1)).toLocaleString('ko') + ' 원');
	});  
	
	
	//장바구니 가격 계산 후 출력(총가격 포함) type -> 0:공급가액, 1:부가세, 2:견적총액
	function allGoodsCostMake(type){ 
		var seqs = document.getElementsByClassName("seq");
		 
		var allSum=0;
		var allGoodsSum=0;
		var tax=0;
		
		for(i=0; i<seqs.length; i++){
			var cartGoodsSeq = seqs[i].innerHTML;
			var goodsCnt = $("#goodsCnt"+cartGoodsSeq).html(); 
			
			var leng = $("#goodsCost"+cartGoodsSeq).html().length;
			var goodsCost = deleteDelimeterToCost( $("#goodsCost"+cartGoodsSeq).html().substring(0, leng-1) ); 
			
			var sum = Number(goodsCnt) * Number(goodsCost);
			allGoodsSum+=sum; 
		}	
		
		tax=Math.round(allGoodsSum/10);
		allSum=allGoodsSum+tax; 
		
		if(type == 0){
			return allGoodsSum;
		}else if(type == 1){
			return tax;
		}else{
			return allSum;
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
</script>