<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div class="jumbotron">
  <div class="container text-center">
    <h1>ElectroShop</h1>      
    <p>Metal, Future & Values</p>
    <h3>Manager Page Login</h3>
  </div>
</div>
 
 
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
				<button id="manager_login" class="btn btn-success col-sm-4 btn-md" ng-disabled="!validEmail || !validPwd">
		 			로그인
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
				
				$("#manager_login").click(function(){
					$('#manager_login').prop('disabled', true);
					$.post("/manager/login",
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
						        			location.href='/manager/'; 
						        		}
						        }else{
						        		alert('다시 시도해 주세요');
						        }
								
								$('#manager_login').prop('disabled', false);
						    });  
				}); 

			});
	
</script> 
		