app.controller("manageSeasonController", function($scope, $http, $cookies) {
	var baseUrl = rest_url + "SeasonService/";

	reload();

    $scope.$watch('summarizedName',function() {$scope.test();});

    $scope.test = function() {
        $scope.incomplete = false;
        if (!$scope.summarizedName.length)
        {
            $scope.incomplete = true;
        }
    };

    $scope.prepareSummarize = function() {
        $scope.prepareSum = true;
    };

    $scope.cancel = function() {
        reload();
    };

    $scope.removeSeason = function(id) {
        $http.delete(baseUrl + 'deleteSeason/' + id).success(function(){
            reload();
        });
    };

    $scope.canDelete = function(season) {
        if (!$scope.notAdmin && season.summarizeTime && season.matchPlayed == 0)
        {
            return true;
        }
        return false;
    };
    
    $scope.summarize = function() {
        $scope.errorSummarize = false;
        var name = $scope.summarizedName;
        var leagueId = $cookies.get('league_id');
        $http.get(baseUrl + 'summarizeSeason/' + leagueId + "/" + name).success(function(){
            reload();
        }).error(function (data) {
            $scope.errorSummarize = true;
            $scope.errorMessage = data.message;
        });
	};
    
	function reload() {
        $scope.summarizedName = '';
        $scope.prepareSum = false;
        $scope.incomplete = true;
        $scope.notAdmin = $cookies.get('is_admin') == 'N'?true:false;

        var leagueId = $cookies.get('league_id');
	    $http.get(baseUrl + 'seasons/' + leagueId).
	    success(function(data) {
            $scope.seasons = data;
	    });
	};

});