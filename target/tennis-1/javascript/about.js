app.controller("aboutController", function($scope, $http, $cookies) {
	var messageUrl = rest_url + "MessageService/";

	$scope.sendError = false;
	$scope.sendSuccess = false;
	$scope.errorMessage = '';
	$scope.email = '';
	$scope.content = '';

	$scope.incomplete = true;
	$scope.error_email = false;

	$scope.sendComment = function() {
		if($scope.bot)
		{
			return;
		}
		$scope.sendError = false;
		$scope.sendSuccess = false;
		$scope.errorMessage = '';
		$scope.incomplete = true;
		$scope.error_email = false;

		var message = new Object();

		message.commentEmailAddr = $scope.email;
		message.content = $scope.content;
		$http.post(messageUrl + 'sendComment/', message).success(function () {
				//sent comment
				$scope.sendSuccess = true;
				$scope.email = "";
			    $scope.content = "";
			}).error(function (data) {
				$scope.sendError = true;
				$scope.errorMessage = data.message;
			});

	};

	$scope.$watch('email',function() {$scope.test();});
	$scope.$watch('content',function() {$scope.test();});
	
	$scope.test = function() {
		$scope.incomplete = false;
		$scope.error_email = false;

		if (!$scope.email.length || !$scope.content.length) {
			$scope.incomplete = true;
		}

		if ($scope.email.length > 0 && !validateEmail($scope.email))
		{
			$scope.error_email = true;
		}
	};

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}
});