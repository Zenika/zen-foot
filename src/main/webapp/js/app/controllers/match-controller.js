'use strict';

angular.module('zenFoot.app')
    .controller('MatchCtrl', ['$scope', '$timeout', 'betMatchService', '$rootScope', '$q', 'displayService', 'Match', 'Bets', 'Gambler', 'GamblerRanking',
        function ($scope, $timeout, betMatchService, $rootScope, $q, displayService, Match, Bets, Gambler, GamblerRanking) {

            $scope.groups1 = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];
            $scope.groups2 = ['1/8', '1/4', '1/2', 'finale'];
            $scope.groups = $scope.groups1.concat($scope.groups2);
            $scope.groupsFilters = {
                A: false,
                B: false,
                C: false,
                D: false,
                E: false,
                F: false,
                G: false,
                H: false,
                '1/8': false,
                '1/4': false,
                '1/2': false,
                'finale': false
            };
            $scope.matchesByGroup = {};
            $scope.betSavedSuccess = false;
            $scope.betSavedError = false;

            var isGroupsFiltered = function(){
                return _.chain($scope.groupsFilters).values().any(function (v) { return v; }).value();
            };

            $scope.shouldShowGroup = function(group) {
                return $scope.matchesByGroup[group] !== undefined && (!isGroupsFiltered() || $scope.groupsFilters[group]);
            };

            $scope.canBet = function (match) {
                return !match.scoreUpdated && match.date < new Date();
            }

            $q.all([Match.query().$promise, Gambler.get().$promise]).then(function(results){
                var matches = results[0];
                var gambler = results[1];

                var matchesById = {};

                _.each(matches, function(match) {
                    //initialize bet info with empty object
                    match.bet = { matchId: match.id, score1: null, score2: null };
                    matchesById[match.id] = match;
                });

                _.each(gambler.bets, function(bet) {
                    //update bet info
                    matchesById[bet.matchId].bet = bet;
                });

                $scope.matchesByGroup = _.groupBy(matches, function (match) {
                    return match.groupe;
                })
                $scope.matches = matches;
            });


            GamblerRanking.get().$promise.then( function(gamblerRanking) {
                $rootScope.user.points = gamblerRanking.points;
            });

            $scope.scoreRegexp = /^[0-9]{1,2}$|^$/;

            /**
             * This function get bets from the server and compare them to the bets client side,
             * in order to identify bets which were not registered (usefull to identify bets which were
             * voted but were posted after the beginning of the match
             */
//            var updateBets = function () {
//                var matchAndBets = betMatchService.getAll().$promise;
//                matchAndBets.then(function (result) {
//                    var matchBetsCl = angular.copy($scope.matchsBets);
//                    return {result: result, matchBetsCl: matchBetsCl};
//
//                })
//                    .then(function (couple) {
//                        $scope.matchsBets = couple.result;
//                        return couple;
//                    })
//                    .then(function (couple) {
//                        betMatchService.markUnreg(couple.matchBetsCl, $scope.matchsBets);
//                    })
//
//            }


            /**
             * Function called when "postez" is clicked
             */
            $scope.pariez = function () {
                $scope.betSavedSuccess = false;
                $scope.betSavedError = false;

                var now = new Date();
                var bets = _.chain($scope.matches)
                    .filter(function(match) { return !match.scoreUpdated && match.date > now; })
                    .pluck('bet')
                    .value();
                debugger;
                Bets.save(bets, function () {
                    $scope.betSavedSuccess = true;
                    $timeout(function () {
                        $scope.betSavedSuccess = false;
                    }, 2000);
                }, function () {
                    $scope.betSavedError = true;
                    $timeout(function () {
                        $scope.betSavedError = false;
                    }, 2000);
                })
            };

            $scope.isFormer = function (date) {
                var now = new Date();
                var matchDate = new Date(date);
                return  matchDate <= now;
            };

            /**
             * Says whether the formular can be validated (necessary condition). If one score is set, the other one has to
             * be as well.
             * @param bet
             * @returns {boolean}
             */
            $scope.hasTwoScores = function (bet) {
                return !_.isEmpty(bet.score1) || !_.isEmpty(bet.score2);
            };


            $scope.knownResult = function (match) {
                return $scope.isFormer(match.date) && match.scoreUpdated;
            };

            $scope.isTeam1Winner = displayService.isTeam1Winner;
            $scope.isTeam2Winner = displayService.isTeam2Winner;
            $scope.calculatePoints = betMatchService.calculatePoints;
            $scope.dispPoints = displayService.dispPoints;
            $scope.getTeamDisplayName = displayService.getTeamDisplayName;


        }]);
