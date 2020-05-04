app.controller('logoutController', function($scope, $http, $window, $cookies) {

	var cookies = $cookies.getAll();
	angular.forEach(cookies, function (v, k) {
		$cookies.remove(k);
	});
});

