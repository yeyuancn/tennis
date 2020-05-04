app.controller("manageAccountController", function($scope, $http, $cookies) {
	var accountURL = rest_url + "AccountService/";

	init();

	function init() {
		$scope.fName = '';
		$scope.lName = '';
		$scope.email = '';
		$scope.level = '4.0';
		$scope.passwd1 = '';
		$scope.passwd2 = '';
		$scope.error_email = false;
		$scope.error_passwd_length = false;
		$scope.error_passwd_match = false;


		$scope.error_firstname_length = false;
		$scope.error_lastname_length = false;
		$scope.error_email_length = false;

		$scope.failedUpdate = false;
		$scope.successUpdate = false;
		$scope.errorMessage = '';

		$scope.$watch('passwd1',function() {$scope.test();});
		$scope.$watch('passwd2',function() {$scope.test();});
		$scope.$watch('email',function() {$scope.test();});
		$scope.$watch('level',function() {$scope.test();});
		$scope.$watch('fName',function() {$scope.test();});
		$scope.$watch('lName',function() {$scope.test();});


		$http.get(accountURL + 'accountByEmail/' + $cookies.get('login_email')).success(function(account) {
			$scope.id = account.id;
			$scope.fName = account.firstName;
			$scope.lName = account.lastName;
			$scope.email = account.email;
			$scope.level = account.level;

		});

		$scope.incomplete = true;
	};

	$scope.test = function() {
		$scope.error_passwd_match = false;
		$scope.error_passwd_length = false;
		$scope.error_email = false;
		$scope.incomplete = false;

		$scope.error_firstname_length = false;
		$scope.error_lastname_length = false;
		$scope.error_email_length = false;

		if ($scope.passwd1.length != 0 && $scope.passwd2.length != 0 && $scope.passwd1 != $scope.passwd2) {
			$scope.error_passwd_match = true;
		}
		if ($scope.passwd1.length > 0 && $scope.passwd1.length < 6) {
			$scope.error_passwd_length = true;
		}
		if ($scope.email.length > 0 && !validateEmail($scope.email))
		{
			$scope.error_email = true;
		}
		if ($scope.fName.trim().length == 0 ||
			$scope.lName.trim().length == 0 ||
			$scope.passwd1.trim().length == 0 ||
			$scope.passwd2.trim().length == 0 ||
			$scope.email.trim().length == 0)
		{
			$scope.incomplete = true;
		}

		if ($scope.fName.length > 18) {
			$scope.error_firstname_length = true;
		}
		if ($scope.lName.length > 18) {
			$scope.error_lastname_length = true;
		}
		if ($scope.email.length > 38) {
			$scope.error_email_length = true;
		}
	};

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	};

	$scope.updateAccount = function() {
		if($scope.bot)
		{
			return;
		}

		var newAccount = new Object();
		newAccount.id = $scope.id;
		newAccount.email = $scope.email;
		newAccount.password = $scope.passwd1;
		newAccount.firstName = $scope.fName;
		newAccount.lastName = $scope.lName;
		newAccount.level = $scope.level;

		$http.post(accountURL + 'updateAccount/', newAccount).success(function(){

			$cookies.put("login_email", $scope.email);
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