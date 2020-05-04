app.controller("joinLeagueController", function($scope, $http, $window, $cookies) {

	var leagueUrl = rest_url + "LeagueService/";

	$scope.leagueName = '';
	$scope.city = '';
	$scope.state = '';


	$scope.searchLeague = function() {

		if($scope.bot)
		{
			return;
		}

		$scope.hasLeague = false;
		$scope.failedSearchLeague = false;
		$scope.errorMessage = '';

		var newLeague = new Object();
		newLeague.name = $scope.leagueName;
		newLeague.city = $scope.city;
		newLeague.state = $scope.state;

		$http.post(leagueUrl + 'searchLeague/', newLeague).success(function(leagues){
			$scope.leagues = leagues;
			$scope.hasLeague = true;
		}).error(function (data) {
			$scope.failedSearchLeague = true;
			$scope.errorMessage = data.message;
		});
	};

	$scope.joinLeague = function(league) {
		var url = "register.html?mode=join&league_id=" + league.id;
        $window.location.href = url;
    };

	$scope.stateList = state_list;
});