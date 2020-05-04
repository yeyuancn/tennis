app.controller("manageLeagueController", function($scope, $http, $cookies) {
	var baseUrl = rest_url + "LeagueService/";

	init();

    $scope.$watch('leagueName',function() {$scope.test();});
    $scope.$watch('city',function() {$scope.test();});
    $scope.$watch('state',function() {$scope.test();});

    $scope.test = function() {
        $scope.incomplete = false;
        if (!$scope.leagueName.length ||
            !$scope.city.length ||
            !$scope.state.length)
        {
            $scope.incomplete = true;
        }
    };

	$scope.saveChanges = function() {
        $scope.league.name = $scope.leagueName;
        $scope.league.city = $scope.city;
        $scope.league.state = $scope.state;
        $scope.league.mode = $scope.leagueMode;

        $http.post(baseUrl + 'updateLeagueDesc/', $scope.league).success(function(){
            $cookies.put("league_name",$scope.leagueName);
            $scope.incomplete = true;
            $scope.savedChange = true;
            $scope.failedManageLeague = false;
            $scope.errorMessage = '';
        }).error(function () {
            $scope.savedChange = false;
            $scope.failedManageLeague = true;
            $scope.errorMessage = 'Failed to update league info!'
        });
	};

    $scope.getDirectURL = function() {
        return "http://justtennisleague.com/register.html?mode=join&league_id=" + $scope.league_id;
    }

    $scope.getURL = function() {
        return "http://justtennisleague.com/register.html%3Fmode=join%26league_id=" + $scope.league_id;
    }

    $scope.getTwitterURL = function() {
        return "http://twitter.com/share?text=Join%20my%20league%20at&url=" + $scope.getURL();
    }

    $scope.getFacebookURL = function() {
        return "http://www.facebook.com/sharer.php?u=" + $scope.getURL();
    }

    $scope.getLinkedInURL = function() {
        return "https://www.linkedin.com/shareArticle?mini=true&url=" + $scope.getURL();
    }

    $scope.getGooglePlusURL = function() {
        return "https://plus.google.com/share?url=" + $scope.getURL();
    }

	function init() {
        $scope.leagueName = '';
        $scope.city = '';
        $scope.state = '';
        $scope.incomplete = true;
        $scope.notAdmin = $cookies.get('is_admin') == 'N'?true:false;

        var leagueId = $cookies.get('league_id');
        $scope.league_id = leagueId;
	    $http.get(baseUrl + 'league/' + leagueId).
	    success(function(data) {
            $scope.league = data;

            $scope.leagueName = data.name;
            $scope.city = data.city;
            $scope.state = data.state;
            $scope.leagueMode = data.mode;
            $scope.playerCount = data.playerCount;
            //console.log("COUNT " + data.playerCount);
            $scope.failedManageLeague = false;
            $scope.errorMessage = '';

            $cookies.put("league_name", data.name);
	    });
	};

    $scope.stateList = state_list;

});