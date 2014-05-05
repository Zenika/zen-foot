/**
 *
 */

'use strict';
var controllers = angular.module('zenFoot.controllers', ['ngResource']);

controllers.controller('HelloCtrl', ['$scope', '$resource',
    function ($scope, $resource) {

        $scope.sentence = $resource('/api/:param', {param: 'coucou'}).get();

    } ]);

/**
 * This controller sends the login request to restx and spreads an AUTHENTICATED event to scopes.
 */
controllers.controller('LoginCtrl', function ($scope, $rootScope, $http, $location) {
    $scope.login = { };

    $scope.submit = function () {
        $http.post('/api/sessions',
            {principal: {name: $scope.login.email, passwordHash: $scope.login.password}},
            {withCredentials: true}
        )
            .success(function (data, status, headers, config) {
                console.log('authenticated', data, status);
                $rootScope.$broadcast('AUTHENTICATED', data.principal);
                $location.path('/index');
            }).error(function (data, status, headers, config) {
                console.log('error', data, status);
                alert("Authentication error, please try again.");
            });
    }
});

controllers.controller('MatchCtrl', ['$scope', 'matchService', 'postBetService', function ($scope, matchService, postBetService) {
    $scope.matchsBets = matchService.getAll();

    $scope.groupes = ["A", "B", "C", "D", "E", "F", "G", "H"];


    $scope.grpA = "A";
    $scope.grpA = "B";

    $scope.grpA = "C";

    $scope.grpA = "D";

    $scope.grpA = "E";

    $scope.grpA = "F";
    $scope.grpA = "G";
    $scope.grpA = "H";


    $scope.scoreRegexp = /^[0-9]{1,2}$|^$/;

    $scope.postez = function () {
        $scope.modified = false;
        submit();
    }

    var submit = function () {
        checkScores(postBets);

    }

    $scope.modified = false;


    var postBets = function () {


        postBetService.save($scope.matchsBets, function () {
            console.log('bets were sucessfully sent');
            $scope.matchsBets = matchService.getAll();

        }, function () {
            console.log('sending bets failed');
        })
    }

    var checkScores = function (callBack) {
        /*
         for(var matchBet in $scope.matchsBets){
         checkScore(matchBet);
         }
         */

        angular.forEach($scope.matchsBets, checkScore);
        postBets();
    }


    var checkScore = function (matchAndBet) {

        var unsetScore = scoreUnset(matchAndBet);
        if (unsetScore) {
            unsetScore.score = 0;
            return true;
        }
        else {
            return false;
        }
    }

    var scoreUnset = function (matchAndBet) {
        var bool1;
        bool1 = matchAndBet.bet.score1.score === '' && matchAndBet.bet.score2.score !== '';

        if (bool1) {
            $scope.modified = true;
            return matchAndBet.bet.score1;
        }

        var bool2;
        bool2 = matchAndBet.bet.score2.score === '' && matchAndBet.bet.score1.score !== '';

        if (bool2) {
            $scope.modified = true;
            return matchAndBet.bet.score2;
        }

        return undefined;
    }

}]);


controllers.controller('navBarController', function ($scope, $location, isActiveService) {
    $scope.isActive = function (path) {
        isActiveService.isActive(path);
    }

});

