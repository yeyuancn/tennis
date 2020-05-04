app.controller("chooseLeagueController", function($scope, $http, $window, $cookies) {

	init();

	function init() {
		$scope.players = $cookies.getObject("players");

	};
	
	$scope.enterLeague = function(player) {

		$cookies.put("player_fname", player.firstName);
		$cookies.put("player_lname", player.lastName);
		$cookies.put("league_id", player.leagueId);
		$cookies.put("acct_id", player.acctId);
		$cookies.put("league_name", player.leagueName);
		$cookies.put("player_id", player.id);
		$cookies.put("is_admin",player.isAdmin?"Y":"N");

		$window.location.href = "manageLeague.html";
	};

});