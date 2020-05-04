app.controller('statController', function ($scope, $http, $window, $cookies) {
    var baseUrl = rest_url + "LeagueService/";

        $http.get(baseUrl + 'getLeagueNumber/').success(function (data) {
            //player logged in
            $scope.leagueNumber = data;
        }).error(function (data) {
            console.log("error making call");
        });
});

