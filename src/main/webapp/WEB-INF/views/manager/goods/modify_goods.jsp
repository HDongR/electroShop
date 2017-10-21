<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<div class="container col-sm-10">
	<h2>상품수정</h2>
	<br/> 

	<form id="modifyGoodsForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="goodsSeq" value="${goodsVO.goodsSeq}">
		<div class="form-group">
			<label class="control-label bg-primary" for="goodsSubject">상품명</label> 
			<input type="text" id="goodsSubject" class="form-control" name="goodsSubject" value="${goodsVO.goodsSubject}">
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="goodsCost">판매가</label>
			<div class="container row">
				<input class="col-sm-8" type="number" id="goodsCost" name="goodsCost"
					  min="0" value="${goodsVO.goodsCost}"> <label class="col-sm-2">원</label>
			</div>
		</div>
		
		<div class="form-group">
			<label class="control-label bg-primary" for="goodsStock">재고량</label>
			<div class="container row">
				<input class="col-sm-8" type="number" id="goodsStock" name="goodsStock"
					step="1" min="0" value="${goodsVO.goodsStock}"> <label class="col-sm-2">개</label>
			</div>
		</div>
		

		<div class="form-group">
			<label class="control-label bg-primary" for="goodsMainPic">메인사진</label> <input
				id="goodsMainPic" type="file" name="goodsMainPic" accept="image/*" value="${goodsVO.goodsMainPicUrl}"> <img
				id="mainPicPreview" src="${goodsVO.goodsMainPicUrl}" alt="" width=100/>
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="goodsContents">제품상세입력</label>
			<textarea class="form-control" name="goodsContents" id="goodsContents" >${goodsVO.goodsContents}</textarea>

		</div>
		<input id="modifyBtn" class="btn btn-primary btn-md" type="submit" value="상품수정 완료">
	</form>
	<br/>
	
</div>

<!-- ckeditor설정 -->
<script>
	CKEDITOR.replace('goodsContents', {
		'filebrowserUploadUrl' : '/manager/upload_img'
	});  
	
	CKEDITOR.on('dialogDefinition', function(ev) {
		var dialogName = ev.data.name;
		var dialogDefinition = ev.data.definition;

		switch (dialogName) {
		case 'image': //Image Properties dialog
			//dialogDefinition.removeContents('info');
			dialogDefinition.removeContents('Link');
			dialogDefinition.removeContents('advanced');
			break;
		}
	});
</script>

<!-- 메인이미지 프리뷰 -->
<script type="text/javascript">
	$(function() {
		$("#goodsMainPic").on('change', function() {
			readURL(this);
		});
	});

	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#mainPicPreview').attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}
</script>

<!-- 글등록버튼 -->
<script type="text/javascript">
	function CKupdate() {
		for (instance in CKEDITOR.instances)
			CKEDITOR.instances[instance].updateElement();
	}

	$("#modifyBtn").click(function(event) {
		CKupdate();

		//stop submit the form, we will post it manually.
		event.preventDefault();

		// Get form
		var form = $('#modifyGoodsForm')[0];

		// Create an FormData object
		var data = new FormData(form);  
		data.append("goodsMainPicUrl", "${goodsVO.goodsMainPicUrl}");
		
		// disabled the submit button
		$("#modifyBtn").prop("disabled", true);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : "/manager/goods/modify_goods",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(data) { 
				if(data == 'completeUpdatedGoods'){
					location.href = '/manager/';
				}else if(data == 'validError'){
					alert('알맞게 모두 입력하세요')
				} 
				$("#modifyBtn").prop("disabled", false); 
			},
			error : function(e) {
				alert("error");
				console.log(e);
				$("#modifyBtn").prop("disabled", false);

			}
		});

	});
</script>
