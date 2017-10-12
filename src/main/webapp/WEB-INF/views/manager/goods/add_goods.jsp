<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="/resources/ckeditor4/ckeditor.js"></script>
<script type="text/javascript" src="/resources/js/date_utils.js/"></script>

<div class="container col-sm-10">
	<h2>상품등록</h2>
	<br/>

	<form id="addGoodsForm" method="post" enctype="multipart/form-data">

		<div class="form-group">
			<label class="control-label bg-primary" for="subject">상품명</label> <input
				type="text" id="subject" class="form-control" name="subject">
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="cost">판매가</label>
			<div class="container row">
				<input class="col-sm-8" type="number" id="cost" name="cost"
					step="1000" min="0"> <label class="col-sm-2">원</label>
			</div>
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="mainPic">메인사진</label> <input
				id="mainPic" type="file" name="mainPic" accept="image/*" /> <img
				id="mainPicPreview" src="#" alt="" width=100/>
		</div>

		<div class="form-group">
			<label class="control-label bg-primary" for="contents">제품상세입력</label>
			<textarea class="form-control" name="contents" id="contents"></textarea>

		</div>
		<input id="uploadBtn" class="btn btn-primary btn-md" type="submit"
			value="상품등록 완료">
	</form>
	<br/>
	
</div>

<!-- ckeditor설정 -->
<script>
	CKEDITOR.replace('contents', {
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
		$("#mainPic").on('change', function() {
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
		data.append("joinDate", now());

		// disabled the submit button
		$("#uploadBtn").prop("disabled", true);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : "/manager/add_goods",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(data) { 
				if(data == 'completeAddedGoods'){
					location.href = '/manager/';
				}else if(data == 'validError'){
					alert('모두 입력해주세요')
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
