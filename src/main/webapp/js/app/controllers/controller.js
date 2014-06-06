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

    if($rootScope.subscriber != null) {
    	$scope.login = $rootScope.subscriber.login;
    }

    $scope.submit = function () {
        //This method is called by autofill directive, and form validation could not be tested before 
        //(web browser does not update model values when it auto fill a form)
        if ($scope.loginForm.$invalid){
            return;
        }
        $http.post('/api/sessions',
            {principal: {name: $scope.login.email, passwordHash: $scope.login.password}},
            {withCredentials: true}
        )
            .success(function (data, status, headers, config) {
                //console.log('authenticated', data, status);
                $rootScope.$broadcast('AUTHENTICATED', data.principal);
                //Submit hidden form with classic HTTP POST to enabled password recording in browser
                angular.element("#postLoginForm").submit();
            }).error(function (data, status, headers, config) {
                //console.log('error', data, status);
                alert("Authentication error, please try again.");
            });
    };

    $scope.subscribe = function() {
		$location.path('/subscribe');
	};
});

controllers.controller("subscribeCtrl", ['$scope', '$resource', '$http', '$rootScope', '$location','$modal',function($scope, $resource, $http, $rootScope, $location,$modal) {
	$scope.subscriber = {teams:[{name:"",isNew:false}]};
	$rootScope.subscriber = {};

	


    var checkTeams= function (){
        for(var x in $scope.subscriber.teams){
            if($scope.subscriber.teams[x].name.trim()==""){
                $scope.subscriber.teams.splice(x);
            }
        }
    }

    var subscribe = function() {
        checkTeams();
        var Subscription = $resource('/api/performSubscription');
        Subscription.save({user:$scope.subscriber,teams:$scope.subscriber.teams});
        $rootScope.subscriber = $scope.subscriber;
        $location.path('/login');
    };

    var subscribeGroups= function(){
        var modalInstance = $modal.open({backdrop:'static',scope:$scope,templateUrl:'view/modal-sub.html'})
        $scope.modalInstance= modalInstance

        modalInstance.result.then(function(response){
            if(response==true){
                subscribe()
            }
        })
    }

    var hasNewGroup=function(){
        for(var x in $scope.subscriber.teams){
            if($scope.subscriber.teams[x].isNew){
                return true;
            }
            return false;
        }
    }

   function loadTeams(){
       console.log("called")
        var teams = $resource('/api/teams').query();
        $scope.existingTeams=teams;
    };
    loadTeams();


    $scope.valider=function(){
        if(hasNewGroup()){
            subscribeGroups()
        }
        else{
            subscribe()
        }

    }

    $scope.ok=function(){
        $scope.modalInstance.close(true);
    }

    $scope.cancel=function(){
        $scope.modalInstance.dismiss();
    }



}]);

controllers.controller("confirmSubscriptionCtrl", function($timeout, $location, $stateParams, $resource) {
	var confirmSubscription = $resource('/api/confirmSubscription', {email : $stateParams.id});
	confirmSubscription.get($stateParams.id, function(data) {
		alert( data == "false" );
	});
});

controllers.controller('MatchCtrl', ['$scope', 'betMatchService', 'postBetService', '$rootScope', '$q', 'displayService', '$rootScope', 'Gambler', function ($scope, betMatchService, postBetService, $rootScope, $q, displayService, Match, Gambler) {


    var groupeFilter = function () {
        var matchs = betMatchService.getAll();
        /* $scope.matchsBets = _.groupBy(matchs, function (matchBet) {
         return matchBet.match.participant1.groupe;
         })*/
        $scope.matchsBets = {all: matchs};
    }

    groupeFilter();


    $scope.$watch('showAll()', function (newValue, oldValue) {
        if (newValue != oldValue) {
            if (newValue == true) {
                $scope.matchsBets = {all: _.flatten(_.values($scope.matchsBets))}

            }
            else {
                if (newValue == false) {
                    var matchsBets = _.flatten(_.values($scope.matchsBets));
                    $scope.matchsBets = _.groupBy(matchsBets, function (matchBet) {
                        return matchBet.match.participant1.groupe;
                    })
                }
            }
        }
    }, true)

    Gambler.get().$promise
        .then(function (response) {
            return response;
        })
        .then(onGamblerRetrieved)
        .catch(function () {
        })


    function onGamblerRetrieved(gambler) {
        $rootScope.user.points = gambler.points;
    }


    var fetchMatchs = function () {

        var defer = $q.defer();
        defer.promise
            .then(function () {
                var clMatch = angular.copy($scope.matchsBets);
                return clMatch;

            })
            .then(function (clMatch) {
                var srvMatchs = betMatchService.getAll();
                return {srvMatchs: srvMatchs, clMatch: clMatch};

            })
            .then(function (matchLists) {
                $scope.matchsBets = matchLists.srvMatchs;
                return matchLists;
            })
            .then(function (matchLists) {
                betMatchService.signalUnreg(matchLists.srvMatchs, matchLists.clMatch)
            })


            .catch(function () {
                console.log('error retrieving bets from server');
            })
        defer.resolve();
    }


    /* $scope.groupes = [{lettre:"A",checked:false}, {lettre:"B",checked:false}, {lettre:"C",checked:false}, {lettre:"D",checked:false}, {lettre:"E",checked:false}, {lettre:"F",checked:false},
     {lettre:"G",checked:false},{lettre:"H",checked:false}];*/

    $scope.groupes = {
        A: {checked: false, letter: 'A'},
        B: {checked: false, letter: 'B'},
        C: {checked: false, letter: 'C'},
        D: {checked: false, letter: 'D'},
        E: {checked: false, letter: 'E'},
        F: {checked: false, letter: 'F'},
        G: {checked: false, letter: 'G'},
        H: {checked: false, letter: 'H'}
    }

    $scope.phases={
        I:{checked:false,fraction:'1/8', order:1},
        J:{checked:false,fraction:'1/4',order:2},
        K:{checked:false,fraction:'1/2',order:3},
        L:{checked:false,fraction:'finale',order:4}
    }


    var showAll = function () {
        var toRet = true;
        for (var index in $scope.groupes) {
            toRet = toRet && !$scope.groupes[index].checked
        }
        return toRet;
    }

    $scope.showAll = showAll;

    $scope.scoreRegexp = /^[0-9]{1,2}$|^$/;


    /**
     * This function get bets from the server and compare them to the bets client side,
     * in order to identify bets which were not registered (usefull to identify bets which were
     * voted but were posted after the beginning of the match
     */
    var updateBets = function () {
        var matchAndBets = betMatchService.getAll().$promise;
        matchAndBets.then(function (result) {
            var matchBetsCl = angular.copy($scope.matchsBets);
            return {result: result, matchBetsCl: matchBetsCl};

        })
            .then(function (couple) {
                $scope.matchsBets = couple.result;
                return couple;
            })
            .then(function (couple) {
                betMatchService.markUnreg(couple.matchBetsCl, $scope.matchsBets);
            })

    }


    /**
     * Function called when "postez" is clicked
     */
    $scope.postez = function () {
        $scope.modified = false;
        postBets();
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

        var wellFormated = _.flatten(_.values($scope.matchsBets));
        postBetService.save(wellFormated, function () {
            console.log('bets were sucessfully sent');
        }, function () {
            console.log('sending bets failed');
        })
    }

    var checkScores = function (callBack) {


        angular.forEach($scope.matchsBets, checkScore);
        if (!$scope.modified) {
            callBack();
        }
    }


    var checkScore = function (matchAndBet) {

        var unsetScore = scoreUnset(matchAndBet);
        if (unsetScore) {
            unsetScore.score = 0;
            unsetScore.assignedZero = true;
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
    $scope.showWarning = function (bet) {

        return bet.score1.assignedZero || bet.score2.assignedZero;
        //return true;
    }

    $scope.isFormer = function (date) {
        var now = new Date();
        var matchDate = new Date(date);

        var toRet = matchDate <= now;

        return toRet;
    }

    /**
     * Says whether the formular can be validated (necessary condition). If one score is set, the other one has to
     * be as well.
     * @param bet
     * @returns {boolean}
     */
    $scope.hasTwoScores = function (bet) {
        var sc1Empty = !bet.score1.score || bet.score1.score.trim() == ""
        var sc2empty = !bet.score2.score || bet.score2.score.trim() == "";
        var notOk = (sc1Empty && !sc2empty) || (!sc1Empty && sc2empty)

        return notOk;
    }


    $scope.knownResult = function (match) {
        return $scope.isFormer(match.date) && match.outcome;
    }

    $scope.isWinner = displayService.isWinner;

    $scope.calculatePoints = betMatchService.calculatePoints;

    $scope.dispPoints = displayService.dispPoints;


}]);


controllers.controller('navBarController', function ($scope, $location, isActiveService) {
    $scope.isActive = function (path) {
        isActiveService.isActive(path);
    }

});

