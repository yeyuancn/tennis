app.controller("managePlayersController", function($scope, $http, $cookies) {
	var baseUrl = rest_url + "PlayerService/";

	reload();

	$scope.editPlayer = function(player) {
		$scope.edit = true;
		$scope.player = player;
		$scope.fName = player.firstName;
		$scope.lName = player.lastName;
		$scope.level = player.level;
		$scope.isActive = player.isActive?'Y':'N';
		$scope.isAdmin = player.isAdmin?'Y':'N';
        $scope.isOwner = player.isOwner;
		$scope.incomplete = true;
		$scope.savedChange = false;
		
		$scope.failedEdit = false;
		$scope.errorMessage = '';
		$scope.error_firstname_length = false;
		$scope.error_lastname_length = false;
		$scope.$watch('fName', function() {$scope.test();});
		$scope.$watch('lName', function() {$scope.test();});
	};

	$scope.test = function() {
		$scope.incomplete = false;
		$scope.error_firstname_length = false;
		$scope.error_lastname_length = false;

		if ($scope.fName.trim().length == 0 ||
			$scope.lName.trim().length == 0)
		{
			$scope.incomplete = true;
		}

		if ($scope.fName.length > 16) {
			$scope.error_firstname_length = true;
		}
		if ($scope.lName.length > 16) {
			$scope.error_lastname_length = true;
		}
	};

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}

	$scope.cannotEdit = function(player) {
		if ($scope.login_isAdmin == 'Y' || $scope.login_playerId == player.id)
		{
			return false;
		}
		return true;
	};

	$scope.saveChanges = function() {
			$scope.player.firstName = $scope.fName;
			$scope.player.lastName = $scope.lName;
			$scope.player.level = $scope.level;
			if ($scope.isActive == "Y")
			{
				$scope.player.isActive = true;
			}
			else
			{
				$scope.player.isActive = false;
			}
			if ($scope.isAdmin == "Y")
			{
				$scope.player.isAdmin = true;
			}
			else
			{
				$scope.player.isAdmin = false;
			}

			$http.post(baseUrl + 'updatePlayer/', $scope.player).success(function(){
				if ($scope.player.id == $scope.login_playerId)
				{
					//self info changes, update cookie:
					$cookies.put("player_fname",$scope.fName);
					$cookies.put("player_lname",$scope.lName);
				}
				reload();
				$scope.savedChange = true;
			}).error(function (data) {
				$scope.failedEdit = true;
				$scope.errorMessage = data.message;
			});
	};

	$scope.deletePlayer = function(id) {
		$http.delete(baseUrl + 'deletePlayer/' + id).success(function(){
			reload();
		});
	};

	$scope.reload = function() {
		reload();
	};

	function reload() {
		var leagueId = $cookies.get('league_id');
        $scope.login_isAdmin = $cookies.get('is_admin');
        $scope.login_playerId = $cookies.get('player_id');

	    $http.get(baseUrl + 'players/' + leagueId).
	    success(function(data) {
			$scope.accountWrappers = data;
	    });

		$scope.edit = false;

	}
});