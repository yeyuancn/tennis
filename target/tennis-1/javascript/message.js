app.controller("messageController", function($scope, $http, $window, $cookies) {

	var messageUrl = rest_url + "MessageService/";
	var matchUrl = rest_url + "MatchResultService/";

	init();

	function init() {
		$scope.uid = $cookies.get('player_id');
		$scope.leagueId = $cookies.get('league_id');
		$scope.selected = {};
		$scope.reply = {};
		$scope.replyContent = {};
		$scope.replyError = {};
		$scope.replyErrorMessage = {};
		$scope.replySuccess = {};
		var mode = $cookies.get('message_mode');
		if (mode == 1)
		{
			switchInbox();
		}
		else if(mode == 2)
		{
			switchOutbox();
		}
		else
		{
			switchNew();
		}
	}

	$scope.switchOutbox = function() {
		switchOutbox();
	};

	$scope.switchInbox = function() {
		switchInbox();
	};

	$scope.switchNew = function() {
		switchNew();
	};

	$scope.replyMessage = function(id) {
		$scope.reply[id] = true;
	}

	$scope.unreplyMessage = function(id) {
		$scope.reply[id] = false;
		$scope.replyContent[id] = "";
		$scope.replyError[id] = false;
		$scope.replyErrorMessage[id] = "";
		$scope.replySuccess[id] = false;
	}

	$scope.sendReply = function(id, fromPlayerId) {

		$scope.replyError[id] = false;
		$scope.replyErrorMessage[id] = "";
		$scope.replySuccess[id] = false;

		if (!$scope.replyContent[id])
		{
			$scope.replyError[id] = true;
			$scope.replyErrorMessage[id] = "Email Content is empty!";
		}
		else
		{
			var message = new Object();
			message.fromPlayerId = $scope.uid;
			message.toPlayerIds = fromPlayerId + ":";
			message.content = $scope.replyContent[id];
			$http.post(messageUrl + 'saveMessage/', message).success(function () {
				//saved message
				$scope.replySuccess[id] = true;
				$scope.replyContent[id] = "";
			}).error(function (data) {
				$scope.replyError[id] = true;
				$scope.replyErrorMessage[id] = data.message;
			});

		}
	};

	$scope.sendEmail = function() {
		$scope.sendError = false;
		$scope.sendSuccess = false;
		$scope.errorMessage = "";
		var length = $scope.playerResults.length;
		var targetIds = "";
		for (var i = 0; i < length; i ++)
		{
			var obj = $scope.playerResults[i];
			if ($scope.selected[obj.playerId] == true)
			{
				targetIds += obj.playerId + ":";
			}
		}
		if (targetIds == "")
		{
			$scope.sendError = true;
			$scope.errorMessage = "Must choose at lease one target player";
		}
		if (!$scope.content)
		{
			$scope.sendError = true;
			$scope.errorMessage = "Email Content is empty!";
		}
		else
		{
			console.log("sending email to id " + targetIds + ", content " + $scope.content);
			var message = new Object();
			message.fromPlayerId = $scope.uid;
			message.toPlayerIds = targetIds;
			message.content = $scope.content;
			$http.post(messageUrl + 'saveMessage/', message).success(function () {
				//saved message
				$scope.sendSuccess = true;
				$scope.content = "";
				$scope.selected = {};
				$scope.master = false;
			}).error(function (data) {
				$scope.sendError = true;
				$scope.errorMessage = data.message;
			});

		}
	};

	$scope.deleteMessage = function(id) {
		if ($scope.isInboxActive)
		{
			$http.delete(messageUrl + 'deleteMessageByTo/' + id).success(function(){
				init();
			});
		}
		else
		{
			$http.delete(messageUrl + 'deleteMessageByFrom/' + id).success(function(){
				init();
			});
		}

	};

	$scope.selectAll = function() {
		var length = $scope.playerResults.length;
		for (var i = 0; i < length; i ++)
		{
			var obj = $scope.playerResults[i];
			if ($scope.master && obj.playerId != $scope.uid) {
				$scope.selected[obj.playerId] = true;
			}
			else {
				$scope.selected[obj.playerId] = false;
			}
		}
	};

	$scope.isSameAsUser = function(playerId) {
		if ($scope.uid == playerId)
		{
			return true;
		}
		return false;
	};

	function switchInbox()
	{
		$cookies.put("message_mode", 1);
		$scope.isInboxActive = true;
		$scope.isOutboxActive = false;
		$scope.isNewActive = false;

		$http.get(messageUrl + 'messagesToPlayer/' + $scope.uid).
		success(function(data) {
			$scope.messages = data;
		});
	}

	function switchOutbox()
	{
		$cookies.put("message_mode", 2);
		$scope.isInboxActive = false;
		$scope.isOutboxActive = true;
		$scope.isNewActive = false;

		$http.get(messageUrl + 'messagesFromPlayer/' + $scope.uid).
		success(function(data) {
			$scope.messages = data;
		});
	}

	function switchNew()
	{
		$cookies.put("message_mode", 3);
		$scope.isInboxActive = false;
		$scope.isOutboxActive = false;
		$scope.isNewActive = true;
		$scope.sendError = false;
		$scope.sendSuccess = false;

		$http.get(matchUrl + 'currentPlayerResults/' + $scope.leagueId).
		success(function(data) {
			$scope.playerResults = data;
		});
	}

	$scope.popup = function(playerId, fName, lName)
	{
		window.open("./match_hist.html?player_id=" + playerId + "&player_fname=" + fName + "&player_lname=" + lName, "Match Result for " + fName + " " + lName + ":", 'width=960,height=700,top=100,left=100,scrollbars=yes');
	};
});