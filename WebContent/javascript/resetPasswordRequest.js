app.controller("resetPasswordRequestController", function($scope, $http, $cookies) {
	var accountURL = rest_url + "AccountService/";

	init();
	function init() {

		$scope.email = '';
		$scope.incomplete = true;

		$scope.failedReset = false;
		$scope.successReset = false;
		$scope.errorMessage = '';

		$scope.$watch('email',function() {$scope.test();});

	};

	$scope.test = function() {

		$scope.incomplete = false;

		if ($scope.email.length = 0 || !validateEmail($scope.email))
		{
			$scope.incomplete = true;
		}

	};

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	};

	$scope.resetPassword = function() {
		if($scope.bot)
		{
			return;
		}

		$http.post(accountURL + 'resetPasswordRequest/', $scope.email).success(function(){

			$cookies.put("login_email", $scope.email);
			$scope.successReset = true;
			$scope.failedReset = false;
			$scope.incomplete = true;

		}).error(function (data) {
			$scope.successReset = false;
			$scope.failedReset = true;
			$scope.errorMessage = data.message;
		});
	};

});