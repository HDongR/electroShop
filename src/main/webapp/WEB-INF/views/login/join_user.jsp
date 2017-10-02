<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<script type="text/javascript" src="/resources/js/date_utils.js/"></script> 

<div ng-app="myApp" class="container" ng-controller="validateCtrl">
	  
	<form name="myForm" class="form-horizontal">
		<div id="formGroupEmail" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="inputEmail">Email:</label>
			<div class="col-sm-8">
				<input class="form-control" type="email" id="inputEmail" name="email" ng-model="email" placeholder="Enter Email" required ng-pattern="/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/"> 
						<span ng-show="!myForm.email.$dirty || myForm.email.$error.pattern || myForm.email.$error.required" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
			<div class="col-sm-2">
				<button class="btn btn-warning" type="button" name="duplicateEmail" id="duplicateEmail" ng-disabled="!validEmail">
					중복 확인
				</button>
			</div>
		</div>
		<div id="formGroupPwd" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="pwd">Password:</label>
			<div class="col-sm-8">
				<input type="password" id="pwd" class="form-control"name="pwd" ng-model="pwd" placeholder="Enter password" ng-pattern="/^(?=.*\d)(?=.*[!@#$%^*()\-_=+\\\|\[\]{};:\',.\/?])(?=.*[a-z]).{4,15}$/" required>
					<span ng-show="!myForm.pwd.$dirty || myForm.pwd.$error.pattern || myForm.pwd.$error.required" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
			<div class="col-sm-2">
				<h6>
					영문,숫자,특수문자 포함 4~15자
				</h6>
			</div>
		</div> 
		<div id="formGroupPwd2" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="pwd2">Pwd 확인:</label>
			<div class="col-sm-8">
				<input type="password" id="pwd2" class="form-control" name="pwd2" ng-model="pwd2" placeholder="Enter password" ng-pattern="/^(?=.*\d)(?=.*[!@#$%^*()\-_=+\\\|\[\]{};:\',.\/?])(?=.*[a-z]).{4,15}$/" required>
					<span ng-show="!myForm.pwd2.$dirty || myForm.pwd2.$error.pattern || myForm.pwd2.$error.required" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
		</div> 
		
		
	 	<div id="formGroupNickname" class="form-group has-feedback">
			<label class="control-label col-sm-2" for="nickname">닉네임:</label>
			<div class="col-sm-8">
				<input id="nickname" class="form-control" name="nickname" ng-model="nickname" placeholder="Enter Nickname" ng-pattern="/^[0-9가-힣a-zA-Z]{2,10}$/" required>
					<span ng-show="!myForm.nickname.$dirty || myForm.nickname.$error.pattern || myForm.nickname.$error.required" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
			<div class="col-sm-2">
				<h6>
					특문제외 2~10자
				</h6>
			</div>
		</div> 
 		 
 		 <div class="form-group">
			<label class="control-label col-sm-2"></label>
			<div class="col-sm-10">
				<button id="join_complete" type="button" class="btn btn-md btn-primary" ng-disabled="!validEmail || !validPwd || !pwdSame || !validNickname || !isDuplicateCheck">
		 			ElectroShop 회원가입 완료
			  	</button>   
		  	</div>  
 		</div>
 		
 		
	</form>     
	
</div>

 

<!-- validaeCtrl -->
<script>
//timer
/* setTimeout(function() {
}, 3000); */

	var app = angular.module('myApp', []);
	app.controller('validateCtrl',
			function($scope) {  
				$scope.validEmail = false;
				$scope.validPwd = false;    
				$scope.validPwd2 = false;
				$scope.pwdSame = false;
				$scope.validNickname = false;
				$scope.isDuplicateCheck = false;
			 
				$scope.$watch('email', function() {$scope.validEmailFunc();});
				$scope.$watch('pwd', function() {$scope.validPwdFunc();});
				$scope.$watch('pwd2', function() {$scope.validPwdFunc();});
				$scope.$watch('nickname', function() {$scope.validNikFunc();});
				
				$scope.validEmailFunc = function() { 
				
					if($scope.myForm.email.$dirty){
						
						$scope.validEmail = !$scope.myForm.email.$invalid;
					  
						if($scope.validEmail){
							$('#formGroupEmail').attr('class', 'has-success form-group has-feedback');  
						}else{
							$('#formGroupEmail').attr('class', 'has-error form-group has-feedback'); 
						}   
					}
					
					if($scope.isDuplicateCheck){ 
						$scope.isDuplicateCheck = false;
						emailCheckBtn(false);
					}
				 
				}
				
				$scope.validPwdFunc = function(){ 
					
						if($scope.myForm.pwd.$dirty || $scope.myForm.pwd2.$dirty ){
							$scope.pwdSame = false; 
							
							$scope.validPwd =  $scope.myForm.pwd.$valid;
							$scope.validPwd2 =  $scope.myForm.pwd2.$valid;
							
							if($scope.validPwd){
								$('#formGroupPwd').attr('class', 'has-success form-group has-feedback'); 
							}else{
								$('#formGroupPwd').attr('class', 'has-error form-group has-feedback'); 
							}
							
							if($scope.validPwd2){
								$('#formGroupPwd2').attr('class', 'has-success form-group has-feedback'); 
							}else{
								$('#formGroupPwd2').attr('class', 'has-error form-group has-feedback'); 
							}
							
							if($scope.validPwd && $scope.validPwd2){ 
								if($scope.pwd != $scope.pwd2){ 
									$('#formGroupPwd').attr('class', 'has-error form-group has-feedback'); 
									$('#formGroupPwd2').attr('class', 'has-error form-group has-feedback'); 
								
								}else{
									$scope.pwdSame = true;
									$('#formGroupPwd').attr('class', 'has-success form-group has-feedback'); 
									$('#formGroupPwd2').attr('class', 'has-success form-group has-feedback'); 							
								}
							}
							
						} 
				}
				
				$scope.validNikFunc = function(){
					if($scope.myForm.nickname.$dirty ){ 
						$scope.validNickname =  $scope.myForm.nickname.$valid; 
						
						if($scope.validNickname){
							$('#formGroupNickname').attr('class', 'has-success form-group has-feedback'); 
						}else{
							$('#formGroupNickname').attr('class', 'has-error form-group has-feedback'); 
						}
					}
				}
				
				//이메일 중복 체크 
				$('#duplicateEmail').click(function(){   
					$('#duplicateEmail').prop('disabled', true);
					console.log(encodeURI($("#inputEmail").val()));
					
					
					$.post('/user/check_email',{
				        			email: $scope.email
				        			}
					, function(data, status){
					        if(status == 'success'){
					        		if(data == 'isManagerJoined' || data == 'isCommonJoined' || data == 'isKakaoJoined' || data == 'isNaverJoined'){
					        			alert('dupl'); 
					        			$scope.isDuplicateCheck = false;
					        			emailCheckBtn(false);
					        		}else if(data == 'isNotDuplicateEmail') { 
					        			$scope.isDuplicateCheck = true;
					        			emailCheckBtn(true); 
					        		}
					        		
					        }else{
					        		alert('다시 시도해 주세요');
					        }
						}); 
					 
					 
				});
				
				function emailCheckBtn(isCheck){ 
					if(isCheck){ 
						$('#duplicateEmail').prop('disabled', true);
						$('#duplicateEmail').attr('class', 'btn btn-success');
	        				$('#duplicateEmail').html('가입가능');
	        				
					}else{
						$('#duplicateEmail').prop('disabled', false);
						$('#duplicateEmail').attr('class', 'btn btn-warning');
	        				$('#duplicateEmail').html('중복확인'); 
					}
				}
				
				
				//회원가입 
				$('#join_complete').click(function(){   
					$('#join_complete').prop('disabled', true);
					$.post("/user/esjoin",
						    {
						        email: $scope.email,
						        password: $scope.pwd,
						        nickname: $scope.nickname,
						        joinDate: now(),
						        joinType: 'COMMON'
						    },
						    function(data, status){ 
								if(status == 'success'){
						        		if(data == 'validError'){ 
						        			alert('유효하지 않은 데이터를 입력하셨네요');  
						        		}else if(data == 'databaseError'){
						        			alert('database error'); 
						        		}else if(data == 'joinUserComplete') {  
						        			alert($scope.nickname + '님 환영합니다.');
						        			location.href='/';
						        		}
						        }else{
						        		alert('다시 시도해 주세요');
						        }
								
								$('#join_complete').prop('disabled', false);
						    });  
				});
			}); 
	  
	
</script>  