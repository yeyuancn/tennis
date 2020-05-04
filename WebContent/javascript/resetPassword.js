app.controller("resetPasswordController", function($scope, $http, $cookies) {
	var accountURL = rest_url + "AccountService/";

	init();
	function init() {
		
		$scope.incomplete = true;

		$scope.failedUpdate = false;
		$scope.successUpdate = false;
		$scope.errorMessage = '';

		$scope.error_passwd_length = false;
		$scope.error_passwd_match = false;

		$scope.passwd1 = '';
		$scope.passwd2 = '';

		$scope.$watch('passwd1',function() {$scope.test();});
		$scope.$watch('passwd2',function() {$scope.test();});

		$scope.account_id = getParamValue("accountId");
		$scope.key = getParamValue("key");

		if (!$scope.account_id || !$scope.key)
		{
			$scope.validRequest = false;
			return;
		}
		else
		{
			$scope.validRequest = true;
		}
		
		$http.get(accountURL + 'verifyResetPassword/' + $scope.account_id + "/" + $scope.key ).success(function(account) {
			$scope.email = account.email;
		}).error(function (data) {
			$scope.validRequest = false;
		});
	};

	$scope.test = function() {
		$scope.error_passwd_match = false;
		$scope.error_passwd_length = false;
		$scope.incomplete = false;

		if ($scope.passwd1.length != 0 && $scope.passwd2.length != 0 && $scope.passwd1 != $scope.passwd2) {
			$scope.error_passwd_match = true;
		}
		if ($scope.passwd1.length > 0 && $scope.passwd1.length < 6) {
			$scope.error_passwd_length = true;
		}
		if ($scope.passwd1.trim().length == 0 ||
			$scope.passwd2.trim().length == 0 )
		{
			$scope.incomplete = true;
		}

	};

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	};

	$scope.updatePassword = function() {
		if($scope.bot)
		{
			return;
		}

		var account = new Object();
		account.id = $scope.account_id;
		account.password = $scope.passwd1;

		$http.post(accountURL + 'updatePassword/', account).success(function(){

			$scope.successUpdate = true;
			$scope.failedUpdate = false;
			$scope.incomplete = true;

		}).error(function (data) {
			$scope.successUpdate = false;
			$scope.failedUpdate = true;
			$scope.errorMessage = data.message;
		});
	};

});