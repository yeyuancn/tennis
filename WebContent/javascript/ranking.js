app.controller("rankController", function($scope, $http, $cookies) {
	var matchUrl = rest_url + "MatchResultService/";
    var seasonUrl = rest_url + "SeasonService/";
    
	init();

	function init()
	{

		$scope.uid = $cookies.get('player_id');
		$scope.fName = $cookies.get('player_fname');
		$scope.lName = $cookies.get('player_lname');
		var leagueId = $cookies.get('league_id');
        $scope.seasonId = 0;
        
        $http.get(seasonUrl + 'seasons_all/' + leagueId).
        success(function(data) {
            $scope.seasons = data;
        });

		$http.get(matchUrl + 'playerResults/' + leagueId + '/' + $scope.seasonId).
			success(function(data) {
				$scope.playerResults = data;
		});
	}

    $scope.reload = function()
    {
        $http.get(matchUrl + 'playerResults/' + leagueId + '/' + $scope.seasonId).
        success(function(data) {
            $scope.playerResults = data;
        });
    }

    $scope.popup = function(playerId, fName, lName)
    {
        window.open("./match_hist.html?player_id=" + playerId + "&player_fname=" + fName + "&player_lname=" + lName, "Match Result for " + fName + " " + lName + ":", 'width=960,height=700,top=100,left=100,scrollbars=yes');
    }
});