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


    /**
     * This function get bets from the server and compare them to the bets client side,
     * in order to identify bets which were not registered (usefull to identify bets which were
     * voted but were posted after the beginning of the match
     */
    var updateBets=function(){
        var matchAndBets = matchService.getAll().$promise;
        matchAndBets.then(function(result){
            var matchBetsCl = angular.copy($scope.matchsBets);
            return {result:result,matchBetsCl:matchBetsCl};

        })
            .then(function(couple){
                $scope.matchsBets=couple.result;
                return couple;
            })
            .then(function(couple){
                matchService.markUnreg(couple.matchBetsCl,$scope.matchsBets);
            })

    }


    /**
     * Function called when "postez" is clicked
     */
    $scope.postez = function () {
        $scope.modified = false;
        submit();
    }


    /**
     * Function called via $scope.postez() when "postez" is clicked
     */
    var submit = function () {
        checkScores(postBets);

    }
    /**
     * This variable says whether or not a 0 value has been assigned automatically to a score which was not assigned
     * It is put to false again every time the "postez" button is clicked.
     * @type {boolean}
     */
    $scope.modified = false;


    /**
     * Posts the result to the restx backend. Called within checkscores, only if no score was assigned 0 value automatically
     */
    var postBets = function () {


        postBetService.save($scope.matchsBets, function () {
            console.log('bets were sucessfully sent');
           // $scope.matchsBets = matchService.getAll();
            updateBets();
        }, function () {
            console.log('sending bets failed');
        })
    }

    var checkScores = function (callBack) {


        angular.forEach($scope.matchsBets, checkScore);
        if(!$scope.modified){
            callBack();
        }
    }


    var checkScore = function (matchAndBet) {

        var unsetScore = scoreUnset(matchAndBet);
        if (unsetScore) {
            unsetScore.score = 0;
            unsetScore.assignedZero=true;
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

    /**
     * Returns true if one of the scores was assigned a zero value automatically
     * @param bet
     * @returns {boolean|*}
     */
    $scope.showWarning=function(bet){

        return bet.score1.assignedZero||bet.score2.assignedZero;
        //return true;
    }

    $scope.isFormer=function(date){
        var now = new Date(2014,5,14);
        var matchDate = new Date(date);

        var toRet = matchDate<=now;
        return toRet;
    }


    $scope.knownResult=function(match){
        return $scope.isFormer(match.date)&&match.outcome;
    }


}]);


controllers.controller('navBarController', function ($scope, $location, isActiveService) {
    $scope.isActive = function (path) {
        isActiveService.isActive(path);
    }

});

