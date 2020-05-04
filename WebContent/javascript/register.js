app.controller("registerController", function($scope, $http, $window, $cookies) {
	var playerUrl = rest_url + "PlayerService/";
    var leagueUrl = rest_url + "LeagueService/";

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
		$scope.incomplete = true;
		$scope.failedReg = false;
		$scope.errorMessage = '';

		$scope.login_email = '';
		$scope.login_passwd = '';
		$scope.login_error = false;
		$scope.login_errorMessage = '';

		$scope.error_firstname_length = false;
		$scope.error_lastname_length = false;
		$scope.error_email_length = false;

        $scope.$watch('passwd1',function() {$scope.test();});
        $scope.$watch('passwd2',function() {$scope.test();});
        $scope.$watch('email',function() {$scope.test();});
        $scope.$watch('level',function() {$scope.test();});
        $scope.$watch('fName',function() {$scope.test();});
        $scope.$watch('lName',function() {$scope.test();});

        var mode = getParamValue("mode");

        if (mode == 'create')
        {
            $scope.isCreateMode = true;
            $scope.user_role = "Owner";
        }
        else
        {
            $scope.isCreateMode = false;
            $scope.user_role = "Player";
        }

        $scope.league_id = getParamValue("league_id");

        if (!$scope.league_id || !mode)
        {
            window.location.href="error.html";
        }

        $http.get(leagueUrl + 'league/' + $scope.league_id).
            success(function(data) {

                $scope.leagueName = data.name;

                // Error!
                if (!$scope.leagueName)
                {
                    window.location.href="error.html";
                }

                $cookies.put("league_id", $scope.league_id);
                $cookies.put("league_name", $scope.leagueName);
        });
	}

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
	}

	$scope.registerPlayer = function() {
		if($scope.bot)
		{
			return;
		}
		var newPlayer= new Object();
		newPlayer.firstName = $scope.fName;
		newPlayer.lastName = $scope.lName;
		newPlayer.level = $scope.level;
		newPlayer.isActive = true;
		newPlayer.isAdmin = $scope.isCreateMode;
		newPlayer.isOwner = $scope.isCreateMode;
		newPlayer.leagueId = $scope.league_id;
		newPlayer.email = $scope.email;

		var newAccount = new Object();
		newAccount.email = $scope.email;
		newAccount.password = $scope.passwd1;
		newAccount.firstName = $scope.fName;
		newAccount.lastName = $scope.lName;
		newAccount.level = $scope.level;

		var accountWrapper = new Object();
		accountWrapper.account = newAccount;
		accountWrapper.player = newPlayer;

		$http.post(playerUrl + 'addAccountAndPlayer/', accountWrapper).success(function(player){

			$cookies.put("login_email", $scope.email);
			//player logged in after successful registration
			$cookies.put("player_fname",player.firstName);
			$cookies.put("player_lname",player.lastName);
			$cookies.put("player_id",player.id);
			$cookies.put("is_admin",player.isAdmin?"Y":"N");

			url = "manageLeague.html";
			$window.location.href = url;
		}).error(function (data) {
			$scope.failedReg = true;
			$scope.errorMessage = data.message;
		});
	};

	$scope.loginPlayer = function() {
		if($scope.bot)
		{
			return;
		}

		var newAccount = new Object();
		newAccount.email = $scope.login_email;
		newAccount.password = $scope.login_passwd;

		$http.post(playerUrl + 'loginSimple/', newAccount).success(function (account) {
			var newPlayer= new Object();
			newPlayer.firstName = account.firstName;
			newPlayer.lastName = account.lastName;
			newPlayer.level = account.level;
			newPlayer.acctId = account.id;
			newPlayer.isActive = true;
			newPlayer.isAdmin = $scope.isCreateMode;
			newPlayer.isOwner = $scope.isCreateMode;
			newPlayer.leagueId = $scope.league_id;
			newPlayer.email = $scope.login_email;

			$http.post(playerUrl + 'addPlayer/', newPlayer).success(function(player){
				$cookies.put("login_email", $scope.login_email);
				//player logged in after successful registration
				$cookies.put("player_fname",player.firstName);
				$cookies.put("player_lname",player.lastName);
				$cookies.put("player_id",player.id);
				$cookies.put("is_admin",player.isAdmin?"Y":"N");

				url = "manageLeague.html";
				$window.location.href = url;
			}).error(function (data) {
				$scope.login_error = true;
				$scope.login_errorMessage = data.message;
			});
		}).error(function (data) {
			$scope.login_error = true;
			$scope.login_errorMessage = data.message;
		});
	};
});