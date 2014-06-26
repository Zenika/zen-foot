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

            $scope.allowFilter = {
                '1/8': false,
                '1/4': false,
                '1/2': false,
                'finale': false
            }



            $scope.displayGroup=function(group){
                var texte;
                if($scope.isPool(group)){
                    texte="Groupe "+group
                }
                else{
                    texte=group;
                }
                return texte;
            }
            /**
             * Boolean corresponding to the way matches are displayed. True = displayed by group, false = displayed by date
             * @type {boolean}
             */
            $scope.showByDate = false;

            /**
             * Should we display the matchs corresponding to this date ? Used in date displaying mode.
             * This is based on whether or not matches are corresponding to the selected groups
             * Return true if the matches corresponding to one date should be displayed, false otherwise
             */
            $scope.shouldShowDate = function (date) {
                var toRet1 = !isGroupsFiltered()
                return toRet1 || _.some($scope.matchesByDate[date], function (match) {
                    return $scope.groupsFilters[match.groupe]
                })

            }

            var isGroupsFiltered = function () {
                return _.chain($scope.groupsFilters).values().any(function (v) {
                    return v;
                }).value();
            };

            $scope.shouldShowGroup = function (group) {
                return $scope.matchesByGroup[group] !== undefined && (!isGroupsFiltered() || $scope.groupsFilters[group]);
            };

            $scope.canBet = function (match) {
                return !match.scoreUpdated && match.date < new Date();
            }

            $q.all([Match.query().$promise, Gambler.get().$promise]).then(function (results) {
                var matches = results[0];
                var gambler = results[1];

                var matchesById = {};

                matches = _.sortBy(matches, function (m) {
                    return m.date;
                });

                _.each(matches, function (match) {
                    //initialize bets info with empty object
                    match.bet = { matchId: match.id, score1: null, score2: null };
                    matchesById[match.id] = match;
                });

                _.each(gambler.bets, function (bet) {
                    //update bet info
                    matchesById[bet.matchId].bet = bet;
                });

                $scope.matchesByGroup = _.groupBy(matches, function (match) {
                    return match.groupe
                });

                $scope.matchesByDate = _.groupBy(matches,
                    function (match) {
                        var matchDate = new Date(match.date)
                        var matchDay = new Date(matchDate.getFullYear(), matchDate.getMonth(), matchDate.getDate())
                        if (matchDate.getHours() < 8) {
                            matchDay.setDate(matchDay.getDate() - 1)
                        }
                        return matchDay.getTime()
                    });

                $scope.matchsDates = _.keys($scope.matchesByDate);

                $scope.matches = matches;


                /**
                 * Checks whether there are matches for 1/8, 1/4, 1/2 or finale. If there is, adds the info to $scope.allowFilter
                 * This is used to disable/enable filtering buttons
                 */

                var initFilter = function() {
                    $scope.allowFilter['1/8']= _.some($scope.matches,function(match){
                        return match.groupe==='1/8';
                    });
                    $scope.allowFilter['1/4']= _.some($scope.matches,function(match){
                        return match.groupe==='1/4';
                    });
                    $scope.allowFilter['1/2']= _.some($scope.matches,function(match){
                        return match.groupe==='1/2';
                    })
                    $scope.allowFilter['finale']= _.some($scope.matches,function(match){
                        return match.groupe==='finale';
                    })
                }

                initFilter();
            });


            GamblerRanking.get().$promise.then(function (gamblerRanking) {
                $rootScope.user.points = gamblerRanking.points
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
                    .filter(function (match) {
                        return !match.scoreUpdated && new Date(match.date) > new Date(now);
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

            $scope.getDisplayLinkLabel = function () {
                if ($scope.showByDate) {
                    return "Afficher par groupe"
                }
                else {
                    return "Afficher par date"
                }
            };

            $scope.getGroupingKeys = function () {
                if ($scope.showByDate) {
                    return $scope.matchsDates
                }
                else {
                    return $scope.groups
                }
            };

            $scope.matchesForGroup = function (group) {
                if ($scope.showByDate) {
                    return $scope.matchesByDate[group]
                }
                else {
                    return $scope.matchesByGroup[group]
                }
            };

            $scope.shouldShow = function (group) {
                if ($scope.showByDate) {
                    return true;
                }
                else {
                    return $scope.shouldShowGroup(group)
                }
            };

            $scope.showMatch = function (group) {
                if ($scope.showByDate) {
                    return !isGroupsFiltered() || $scope.groupsFilters[group]
                }
                else {
                    return true
                }
            };

            $scope.focusToday = function () {
                $('html,body').animate({
                    scrollTop: $("#todayFocused").offset().top - 55
                }, 1000);
            }

            $scope.isPool=function(groupe){
                return _.contains($scope.groups1,groupe);
            }


        }]);
