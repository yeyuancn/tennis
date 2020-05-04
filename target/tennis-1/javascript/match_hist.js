app.controller("matchHistController", function($scope, $http) {
	var matchUrl = rest_url + "MatchResultService/";
    var seasonUrl = rest_url + "SeasonService/";

	init();

	function init()
	{
		$scope.seasonId = 0;
        $http.get(seasonUrl + 'seasons_all/' + leagueId).
        success(function(data) {
            $scope.seasons = data;
        });

		loadResult(getParamValue("player_id"),  getParamValue("player_fname"),  getParamValue("player_lname") );
	}
	
	$scope.loadResult = function(playerId, fname, lname)
	{
		loadResult(playerId, fname, lname);
	};

	function loadResult(playerId, fname, lname)
	{
		$scope.playerFName = fname;
		$scope.playerLName = lname;
		$http.get(matchUrl + 'matchResultsForPlayer/' + playerId + '/' + $scope.seasonId).
		success(function(data) {
			$scope.matchResults = data;
		});
	}
});