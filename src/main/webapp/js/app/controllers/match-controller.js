'use strict';

angular.module('zenFoot.app')
    .controller('MatchCtrl', ['$scope', '$timeout', 'betMatchService', '$rootScope', '$q', 'displayService', 'Match', 'Bets', 'Gambler', 'GamblerRanking',
        function ($scope, $timeout, betMatchService, $rootScope, $q, displayService, Match, Bets, Gambler, GamblerRanking) {

            $scope.groups1 = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];
            $scope.groups2 = ['1/8', '1/4', '1/2', 'finale'];
            $scope.all=['all']
            $scope.groups = $scope.groups1.concat($scope.groups2).concat($scope.all)
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

            $scope.isGroupsFiltered = function () {
                return _.chain($scope.groupsFilters).values().any(function (v) {
                    return v;
                }).value();
            };

            $scope.shouldShowGroup = function (group) {
                var isGroupSelected= $scope.matchesByGroup[group] !== undefined && $scope.groupsFilters[group];
                var isAll = group==='all'&&!$scope.isGroupsFiltered()
                var toRet = isGroupSelected || isAll
                return toRet;
            };

            $scope.canBet = function (match) {
                return !match.scoreUpdated && match.date < new Date();
            }

            $scope.isAll=function(group){
                return group==='all'
            }

            $q.all([Match.query().$promise, Gambler.get().$promise]).then(function (results) {
                var matches = results[0];
                var gambler = results[1];

                var matchesById = {};

                matches = _.sortBy(matches, function(m) { return m.date; });

                _.each(matches, function(match) {
                    //initialize bets info with empty object
                    match.bet = { matchId: match.id, score1: null, score2: null };
                    matchesById[match.id] = match;
                });

                _.each(gambler.bets, function(bet) {
                    //update bet info
                    matchesById[bet.matchId].bet = bet;
                });

                $scope.matchesByGroup = _.groupBy(matches, function (match) {
                    return match.groupe
                })
                $scope.matches = matches;
            });


            GamblerRanking.get().$promise.then( function(gamblerRanking) {
                $rootScope.user.points = gamblerRanking.points
            });

            $scope.matchSelected = function (group) {
              if(group==='all'){
                  return $scope.matches
              }
                else{
                  return $scope.matchesByGroup[group]
              }

            }

            $scope.scoreRegexp = /^[0-9]{1,2}$|^$/;


            var pariezNotificationTimeout = null;

            /**
             * Function called when "postez" is clicked.
             * We filter the bets whose score is unknown, and whose beginning date is not passed yet.
             */
            $scope.pariez = function () {
                $scope.betSavedSuccess = false;
                $scope.betSavedError = false;

                if (pariezNotificationTimeout !== null) {
                    $timeout.cancel(pariezNotificationTimeout);
                }
                var now = new Date();
                var bets = _.chain($scope.matches)
                    .filter(function (match) { return !match.scoreUpdated && new Date(match.date) > new Date(now);
                    })
                    .pluck('bet')
                    .value();
                Bets.save(bets, function () {
                    $scope.betSavedSuccess = true;
                    pariezNotificationTimeout = $timeout(function () {
                        $scope.betSavedSuccess = false;
                        pariezNotificationTimeout = null;
                    }, 2000);
                }, function () {
                    $scope.betSavedError = true;
                    pariezNotificationTimeout = $timeout(function () {
                        $scope.betSavedError = false;
                        pariezNotificationTimeout = null;
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

            $scope.isTeamWinner = displayService.isTeamWinner;
            $scope.calculatePoints = betMatchService.calculatePoints;
            $scope.dispPoints = displayService.dispPoints;
            $scope.getTeamDisplayName = displayService.getTeamDisplayName;


        }]);
