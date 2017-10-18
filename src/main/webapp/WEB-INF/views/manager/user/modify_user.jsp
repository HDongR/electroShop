<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 

 <div class="container col-sm-10">
	<h2>유저수정</h2>
	<br/> 

	<form name="myForm" class="form-horizontal">
		<div id="formGroupEmail" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="userEmail">Email:</label>
			<div class="col-sm-8">
				<input class="form-control" type="email" name="userEmail" id="userEmail" value="${userVO.userEmail}" disabled>
			</div>
		</div> 
	 
		
	 	<div id="formGroupNickname" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="nickname">닉네임:</label>
			<div class="col-sm-8"> 
				<input id="nickname" class="form-control" name="nickname"  pattern="/^[0-9가-힣a-zA-Z]{2,10}$/" value="${userVO.userNickname}"  title="'-'를 뺀 휴대전화 번호 10~11자리를 입력해주세요.">
			</div>
			<div class="col-sm-2">
				<h6>
					특문제외 2~10자
				</h6>
			</div>
		</div>  
 		
	</form>     
	
	
</div>




<script type="text/javascript">
	 

</script>