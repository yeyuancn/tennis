app.controller('loginController', function ($scope, $http, $window, $cookies) {
    var baseUrl = rest_url + "PlayerService/";

    $scope.login_error = false;
    

    $scope.login = function () {
        var newAccount = new Object();
        newAccount.email = $scope.login_email;
        newAccount.password = $scope.login_passwd;

        $http.post(baseUrl + 'login/', newAccount).success(function (players) {
            if (players.length == 1)
            {
                player = players[0];
                $cookies.put("login_email", $scope.login_email);
                //Account is only linked to one league, direct user to there
                $cookies.put("player_fname", player.firstName);
                $cookies.put("player_lname", player.lastName);
                $cookies.put("league_id", player.leagueId);
                $cookies.put("acct_id", player.acctId);
                $cookies.put("league_name", player.leagueName);
                $cookies.put("player_id", player.id);
                $cookies.put("is_admin",player.isAdmin?"Y":"N");

                $window.location.href = "manageLeague.html";
            }
            else
            {
                $cookies.put("login_email", $scope.login_email);
                //Account has multiple league associated, let user choose
                $cookies.putObject("players", players);
                $window.location.href = "chooseLeague.html";
            }
        }).error(function (data) {
            $scope.login_error = true;
            $scope.login_errorMessage = data.message;
        });
    };
});

