<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="/resources/js/date_utils.js/"></script>

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
				<input type="password" id="pwd" class="form-control"name="pwd" ng-model="pwd" placeholder="Enter password" ng-pattern="/^(?=.*\d)(?=.*[!@#$%^*()\-_=+\\\|\[\]{};:\',.\/?])(?=.*[a-z]).{4,15}$/" required>
					<span ng-show="!myForm.pwd.$dirty || myForm.pwd.$error.pattern || myForm.pwd.$error.required" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
		</div> 
		<div class="form-group">
			<label class="control-label col-sm-2"></label>
			<div class="col-sm-10" style="text-align: center;">
				<button id="es_login" class="btn btn-success col-sm-4 btn-md" ng-disabled="!validEmail || !validPwd">
		 			로그인
			  	</button>  
			  	 
			  	<a class="col-sm-4 " role="button" id="kakao_login"  style="text-decoration:none">
			  	 	<img id="kakao_img" src="/resources/img/kakao_a.png" height="36" />
			  	</a> 
			  	
			  	<a class="col-sm-4" id="naver_id_login"></a>
		  	</div>
 		</div> 
		
 		<div class="form-group">
			<label class="control-label col-sm-2"></label>
			<div class="col-sm-10" style="text-align: center;">
				<button type="button" class="btn btn-md btn-link" onclick="location.href='/user/join';">
		 			ElectroShop 회원가입
			  	</button>  
			  	
			  	<button type="button" class="btn btn-md btn-link">
		 			ElectroShop 비밀번호 찾기
			  	</button>  
		  	</div>  
 		</div>
 		
	</form>   
	 
</div>

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
				
				$("#es_login").click(function(){
					$('#es_login').prop('disabled', true);
					$.post("/user/eslogin",
						    {
						        email: $scope.email,
						        password: $scope.pwd
						    },
						    function(data, status){ 
								if(status == 'success'){
						        		if(data == 'validError'){ 
						        			alert('유효하지 않은 데이터를 입력하셨네요');   
						        		}else if(data == "invalid Email or Pwd"){
						        			alert('유효하지 않은 이메일 또는 비밀번호입니다');   
						        		}else if(data == 'loginComplete') {  
						        			//complete
						        			location.href='/'; 
						        		}
						        }else{
						        		alert('다시 시도해 주세요');
						        }
								
								$('#es_login').prop('disabled', false);
						    });  
				}); 

			});
	
</script>

<!-- naver -->
<script type="text/javascript">  
	var id = '<spring:eval expression="@config.getProperty('naverclientkey')" />';
	var naver_id_login = new naver_id_login(id, "http://localhost:8080/user/login/");
	var state = naver_id_login.getUniqState();
	naver_id_login.setButton("green", 3, 36);
	naver_id_login.setDomain("http://localhost:8080");
	naver_id_login.setState(state);
	naver_id_login.setPopup();
	naver_id_login.init_naver_id_login();
	
	naver_id_login.get_naver_userprofile("naverSignInCallback()");
	
	function naverSignInCallback() { 
		naverCheckEmail(naver_id_login.getProfileData('email'), naver_id_login.getProfileData('nickname'))
		 
		self.opener = self;
		window.close();
	}
	
	function naverCheckEmail(email, nickname){ 
		$.post('/user/check_email',{
				email: email
				}, function(data, status){
					if(status == 'success'){ 
						if(data == 'isCommonJoined'){
							alert('이미 ES로 가입하셨습니다.');
						}else if(data == 'isKakaoJoined'){
							alert('이미 Kakao로 가입하셨습니다.');
						}else if(data == 'isNotDuplicateEmail') { 
							naverJoin(email, nickname);
						}else if(data == 'isNaverJoined'){
							naverLogin(email);
						}
					}else{
							alert('다시 시도해 주세요');
					}
		});  
	}  
	
	function naverLogin(nemail){
		$.post("/user/naverlogin",
			    {
			        email: nemail,
			        password: ''
			    },
			    function(data, status){
					if(status == 'success'){
			        		if(data == 'validError'){ 
			        			alert('유효하지 않은 데이터를 입력하셨네요');   
			        		}else if(data == "invalid Email or Pwd"){
			        			alert('유효하지 않은 이메일 또는 비밀번호입니다');   
			        		}else if(data == 'loginComplete') {  
			        			//complete
			        			location.href='/'; 
			        		}
			        }else{
			        		alert('다시 시도해 주세요');
			        }
					 
			    }); 
	}
	
	function naverJoin(email, nickname){
		console.log("email:"+email);
		$.post("/user/naverjoin",
			    {
			        email: email,
			        password: '',
			        nickname: nickname,
			        joinDate: now(),
			        joinType: 'NAVER'
			    },
			    function(data, status){ 
					if(status == 'success'){
			        		if(data == 'validError'){ 
			        			alert('유효하지 않은 데이터를 입력하셨네요');  
			        		}else if(data == 'databaseError'){
			        			alert('database error'); 
			        		}else if(data == 'joinUserComplete') {  
			        			//complete
			        			location.href='/';
			        		}
			        }else{
			        		alert('다시 시도해 주세요');
			        }
					 
			    });
	}
</script>
 
 
<!-- kakao -->
<script>  
	var id = '<spring:eval expression="@config.getProperty('kakao')" />';

	Kakao.init(id);
	
	$('#kakao_login').click(function(){ 
		requestKakao();
	});
	 
	
	function requestKakao(){ 
		  Kakao.Auth.login({
				success: function(authObj) {
		      		requestProfile();
		      	},
		      	fail: function(err) {
		        		console.log(JSON.stringify(err));
		      	}
			  });
	} 
	
	function requestProfile(){ 
		Kakao.API.request({
			url: '/v1/user/me',
			success: function(res) {
				 
				kakaoCheckEmail(JSON.parse(JSON.stringify(res))); 
				
			},
			fail: function(error) {
				console.log(error);
			}
		});
	}
	
	function kakaoCheckEmail(jo){ 
		$.post('/user/check_email',{
				email: jo.kaccount_email
				}
				, function(data, status){
				if(status == 'success'){ 
						if(data == 'isCommonJoined'){
							alert('이미 ES로 가입하셨습니다.');
						}else if(data == 'isNaverJoined'){
							alert('이미 Naver로 가입하셨습니다.');
						}else if(data == 'isNotDuplicateEmail') { 
							kakaoJoin(jo);
						}else if(data == 'isKakaoJoined'){
							kakaoLogin(jo.kaccount_email);
						}
				}else{
						alert('다시 시도해 주세요');
				}
		});  
	}  
	
	function kakaoLogin(kemail){
		$.post("/user/kakaologin",
			    {
			        email: kemail,
			        password: ''
			    },
			    function(data, status){
					if(status == 'success'){
			        		if(data == 'validError'){ 
			        			alert('유효하지 않은 데이터를 입력하셨네요');   
			        		}else if(data == "invalid Email or Pwd"){
			        			alert('유효하지 않은 이메일 또는 비밀번호입니다');   
			        		}else if(data == 'loginComplete') {  
			        			//complete
			        			location.href='/'; 
			        		}
			        }else{
			        		alert('다시 시도해 주세요');
			        }
					 
			    }); 
	}
	
	function kakaoJoin(jo){
		$.post("/user/kakaojoin",
			    {
			        email: jo.kaccount_email,
			        password: '',
			        nickname: jo.properties.nickname,
			        joinDate: now(),
			        joinType: 'KAKAO'
			    },
			    function(data, status){ 
					if(status == 'success'){
			        		if(data == 'validError'){ 
			        			alert('유효하지 않은 데이터를 입력하셨네요');  
			        		}else if(data == 'databaseError'){
			        			alert('database error'); 
			        		}else if(data == 'joinUserComplete') {  
			        			//complete
			        			location.href='/';
			        		}
			        }else{
			        		alert('다시 시도해 주세요');
			        }
					 
			    });
	}
	
	$('#kakao_img').mouseenter(function(){
		$('#kakao_img').attr('src', '/resources/img/kakao_v.png');
	}).mouseleave(function(){
		$('#kakao_img').attr('src', '/resources/img/kakao_a.png');
	});
	
</script>

