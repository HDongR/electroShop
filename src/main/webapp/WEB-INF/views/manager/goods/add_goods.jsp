<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
   

<div class="container col-sm-10">
	<h2>상품등록</h2>
	<br/> 
    
	<form id="addGoodsForm" method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label class="control-label bg-primary">카테고리</label><br/>
			<select id="catHighSelect" class="selectpicker" name="catHighName" onchange="set_selectbox();">
	        </select>
	        <select id="catMidSelect" class="selectpicker" name="goodsCatMidSeq"> 
	        </select>  
		</div>
		
		<div class="form-group">
			<label class="control-label bg-primary" for="goodsSubject">상품명</label> <input
				type="text" id="goodsSubject" class="form-control" name="goodsSubject">
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="goodsCost">판매가</label>
			<div class="container row">
				<input class="col-sm-8" type="number" id="goodsCost" name="goodsCost"
					step="1000" min="0"> <label class="col-sm-2">원</label>
			</div>
		</div>
		
		<div class="form-group">
			<label class="control-label bg-primary" for="goodsStock">재고량</label>
			<div class="container row">
				<input class="col-sm-8" type="number" id="goodsStock" name="goodsStock"
					step="1" min="0"> <label class="col-sm-2">개</label>
			</div>
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="goodsMainPic">메인사진</label> <input
				id="goodsMainPic" type="file" name="goodsMainPic" accept="image/*" /> <img
				id="mainPicPreview"  alt="add image" width=100/>
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="goodsContents">제품상세입력</label>
			<textarea class="form-control" name="goodsContents" id="goodsContents"></textarea>

		</div>
		<input id="uploadBtn" class="btn btn-primary btn-md" type="submit"
			value="상품등록 완료">
	</form>
	<br/>
	
</div>

<!-- 카테고리 선택 -->
<script type="text/javascript">
	var jsonData = <c:out value="${categoryJson}" escapeXml="false"/>;
	$(document).ready(function () { 
		make_selectbox(Object.keys(jsonData)[0]);
	});
	
	$(document).ready(function(){
		for(key in jsonData){
			$('#catHighSelect').append('<option value="'+ key +'">' + jsonData[key].catHighName + '</option>');
		}
		$("#catHighSelect").selectpicker("refresh");
	});
  
	
	function make_selectbox(highSeq){ 
		$('#catMidSelect').empty();
	   	var midList = jsonData[highSeq].categoryMidList; 
	   	for(i=0; i< midList.length; i++){
	   		$('#catMidSelect').append('<option value="'+ midList[i].catMidSeq +'">' + midList[i].catMidName + '</option>');
	   	}	 
	   	$("#catMidSelect").selectpicker("refresh"); 
	}  
	 
	function set_selectbox(){
 		var catHighSeq = $("#catHighSelect option:selected").val();
 		make_selectbox(catHighSeq);
	}
</script>

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

	$("#uploadBtn").click(function(event) {
		CKupdate();

		//stop submit the form, we will post it manually.
		event.preventDefault();

		// Get form
		var form = $('#addGoodsForm')[0];

		// Create an FormData object
		var data = new FormData(form);

		// If you want to add an extra field for the FormData
		data.append("goodsCrtDate", now());

		// disabled the submit button
		$("#uploadBtn").prop("disabled", true);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : '/manager/add_goods',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(data) { 
				if(data == 'completeAddedGoods'){
					location.href = '/manager/';
				}else if(data == 'validError'){
					alert('알맞게 모두 입력하세요')
				}else if(data == 'databaseError'){
					alert('databaseError')
				}
				$("#uploadBtn").prop("disabled", false); 
			},
			error : function(e) {
				alert("error");
				console.log(e);
				$("#uploadBtn").prop("disabled", false);

			}
		});

	});
</script>
