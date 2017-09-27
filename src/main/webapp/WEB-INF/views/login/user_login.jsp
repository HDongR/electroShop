<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>

<div ng-app="myApp" class="container" ng-controller="validateCtrl">

	<form name="myForm" class="form-horizontal">
		<div id="formGroupEmail" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="inputEmail">Email:</label>
			<div class="col-sm-10">
				<input type="email" id="inputEmail" class="form-control" name="email" ng-model="email" placeholder="Enter Email" required ng-pattern="/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/"> 
						<span ng-show="!myForm.email.$dirty || myForm.email.$error.pattern || myForm.email.$error.required" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
		</div>
		<div id="formGroupPwd" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="pwd">Password:</label>
			<div class="col-sm-10">
				<input type="password" id="pwd" class="form-control"name="pwd" ng-model="pwd" placeholder="Enter password" ng-pattern="/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{4,15}$/" required>
					<span ng-show="!myForm.pwd.$dirty || myForm.pwd.$error.pattern || myForm.pwd.$error.required" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
		</div> 
		<div class="form-group row">
			<label class="control-label col-sm-2"></label>
			<div class="col-sm-10"  style="text-align: center;">
				<button class="btn btn-success col-sm-4 btn-md" ng-disabled="!validEmail || !validPwd">
		 			로그인
			  	</button>  
			  	 
			  	<a class="col-sm-4 " role="button" id="kakao_login"  style="text-decoration:none">
			  	 	<img id="kakao_img" src="/resources/img/kakao_a.png" height="36" />
			  	</a> 
			  	
			  	<a class="col-sm-4" id="naver_id_login"/>  
		  	</div>
 		</div>
	</form>   
	 
	
	<button id="logout">logout test</button>
	 
</div>

<!-- naver -->
<script type="text/javascript">
	var naver_id_login = new naver_id_login("SD08WV5dMSZ7xKZ4chTs", "http://localhost:8080/user/login/");
	var state = naver_id_login.getUniqState();
	naver_id_login.setButton("green", 3, 36);
	naver_id_login.setDomain("http://localhost:8080");
	naver_id_login.setState(state);
	naver_id_login.setPopup();
	naver_id_login.init_naver_id_login();
 </script>
  
<!-- 네이버아디디로로그인 Callback페이지 처리 Script -->
<script type="text/javascript">
	// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
	function naverSignInCallback() {
		// naver_id_login.getProfileData('프로필항목명');
		// 프로필 항목은 개발가이드를 참고하시기 바랍니다.
		alert(naver_id_login.getProfileData('email'));
		alert(naver_id_login.getProfileData('nickname'));
		alert(naver_id_login.getProfileData('age'));
		self.opener = self;
		window.close();
	}

	// 네이버 사용자 프로필 조회
	naver_id_login.get_naver_userprofile("naverSignInCallback()");
</script>
<!-- //네이버아디디로로그인 Callback페이지 처리 Script -->


  
<!-- validaeCtrl -->
<script>
	var app = angular.module('myApp', []);
	app.controller('validateCtrl',
			function($scope) {  
				$scope.validEmail = false;
				$scope.validPwd = false;    
			 
				$scope.$watch('email', function() {$scope.validEmailFunc();});
				$scope.$watch('pwd', function() {$scope.validPwdFunc();});
				
				$scope.validEmailFunc = function() { 
					if($scope.myForm.email.$dirty){
						
						$scope.validEmail = !$scope.myForm.email.$invalid;
					  
						if($scope.validEmail){
							$('#formGroupEmail').attr('class', 'has-success form-group has-feedback');  
						}else{
							$('#formGroupEmail').attr('class', 'has-error form-group has-feedback'); 
						}  
					}
				}
				
				$scope.validPwdFunc = function(){ 
						
						
						if($scope.myForm.pwd.$dirty){
							
							$scope.validPwd =  $scope.myForm.pwd.$valid;
							
							if($scope.validPwd){
								$('#formGroupPwd').attr('class', 'has-success form-group has-feedback'); 
							}else{
								$('#formGroupPwd').attr('class', 'has-error form-group has-feedback'); 
							}
						}
					 
				}
			});
	

	
</script>

 
<!-- kakao -->
<script> 
	Kakao.init('152ee772f83e4db139a908ea13f143c1');
	
	$('#kakao_login').click(function(){ 
		kakaoLogin();
	});
	
	$('#logout').click(function(){
		kakaoLogout();
	});
	
	function kakaoLogin(){
		  Kakao.Auth.login({
				success: function(authObj) {
		      		requestProfile();
		      	},
		      	fail: function(err) {
		        		console.log(JSON.stringify(err));
		      	}
			  });
	}
	
	function kakaoLogout(){
		Kakao.Auth.logout();
		console.log("logout");
	}
	
	function requestProfile(){
		Kakao.API.request({
			url: '/v1/user/me',
			success: function(res) {
				alert(JSON.stringify(res));
	      		//testPost(encodeURIComponent(JSON.stringify(authObj))); 

			},
			fail: function(error) {
				console.log(error);
			}
		});
	}
	
	function testPost(data){
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("demo").innerHTML = this.responseText;
		    }
		  };
		  xhttp.open("GET", "/user/kakaologin?data=" + data, true);
		  xhttp.send();
	}
	 
	
	$('#kakao_img').mouseenter(function(){
		$('#kakao_img').attr('src', '/resources/img/kakao_v.png');
	}).mouseleave(function(){
		$('#kakao_img').attr('src', '/resources/img/kakao_a.png');
	});
	
</script>

