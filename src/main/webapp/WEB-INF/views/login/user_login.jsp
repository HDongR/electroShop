<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<div ng-app="myApp" class="container" ng-controller="validateCtrl">

	<form name="myForm" class="form-horizontal" action="#">
		<div id="formGroupEmail" class="form-group has-error has-feedback">
			<label class="control-label col-sm-2" for="inputEmail">Email:</label>
			<div class="col-sm-10">
				<input type="email" id="inputEmail" class="form-control" name="email" ng-model="email" placeholder="Enter Email" required> 
						<span ng-show="myForm.$pristine" class="glyphicon glyphicon-remove form-control-feedback"></span><span ng-show="myForm.email.$invalid && myForm.email.$dirty" class="glyphicon glyphicon-remove form-control-feedback"></span>
			</div>
		</div>
		<!--  <div class="form-group">
			<label class="control-label col-sm-2" for="pwd">Password:</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="pwd"
					placeholder="Enter password" name="pwd" ng-model="pwd">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">Submit</button>
			</div>
		</div>
		-->
	</form>

	myform: {{isValidEmail}}<br> 
	<!--  {{!!myForm.$error.email}}-->
</div>


<script>
	var app = angular.module('myApp', []);
	app.controller('validateCtrl',
			function($scope) {  
				$scope.isValidEmail = false;
				 
				$scope.$watch('email', function() {$scope.invalide();});
				$scope.invalide = function() {
					if(!$scope.myForm.$pristine){
						$scope.isValidEmail = !$scope.myForm.email.$error.email && !$scope.myForm.email.$invalid && $scope.myForm.email.$dirty;
					  
						if($scope.isValidEmail){
							$('#formGroupEmail').attr('class',
									'has-success form-group has-feedback');  
						}else{
							$('#formGroupEmail').attr('class',
								'has-error form-group has-feedback'); 
						}
					}else{
						$('#formGroupEmail').attr('class',
						'has-error form-group has-feedback'); 
					}
				}
			});
</script>