app.controller("createLeagueController", function($scope, $http, $window, $cookies) {

	var leagueUrl = rest_url + "LeagueService/";

	$scope.leagueName = '';
	$scope.city = '';
	$scope.state = '';
	$scope.leagueMode = 'searchable';
	$scope.incomplete = true;
	$scope.error_leagueName_length = false;
	$scope.error_city_length = false;
	$scope.failedCreateLeague = false;
	$scope.errorMessage = '';


	$scope.$watch('leagueName',function() {$scope.test();});
	$scope.$watch('city',function() {$scope.test();});
	$scope.$watch('state',function() {$scope.test();});

	$scope.test = function() {
		$scope.incomplete = false;
		$scope.error_leagueName_length = false;
		$scope.error_city_length = false;

		if (!$scope.leagueName.length ||
			!$scope.city.length ||
			!$scope.state.length)
		{
			$scope.incomplete = true;
		}

		if ($scope.leagueName.length > 48) {
			$scope.error_leagueName_length = true;
		}

		if ($scope.city.length > 28) {
			$scope.error_city_length = true;
		}
	};

	$scope.createLeague = function() {
		if($scope.bot)
		{
			return;
		}

		    var newLeague = new Object();
		    newLeague.name = $scope.leagueName;
            newLeague.city = $scope.city;
		    newLeague.state = $scope.state;
		    newLeague.mode = $scope.leagueMode;

			$http.post(leagueUrl + 'addLeague/', newLeague).success(function(league){
				$window.location.href = "register.html?mode=create&league_id=" + league.id;
			}).error(function (data) {
				$scope.failedCreateLeague = true;
				$scope.errorMessage = data.message;
			});
	};

	$scope.stateList = state_list;
});