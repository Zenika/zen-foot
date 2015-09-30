'use strict';

angular.module('zenFoot.app')
    .controller('MatchCtrl', ['$scope', '$timeout', 'betMatchService', '$rootScope', '$q', 'displayService', '$stateParams', 'Events',
        function ($scope, $timeout, betMatchService, $rootScope, $q, displayService, $stateParams, Events) {

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
             * Should we display matchs corresponding to this date ? Used in date displaying mode.
             * This is based on whether or not matches are corresponding to the selected groups
             * Return true if the matches corresponding to one date should be displayed, false otherwise
             */
            $scope.shouldShowDate = function (date) {
                var toRet1 = !isGroupsFiltered();
                return toRet1 || _.some($scope.matchesByDate[date], function (match) {
                    return $scope.groupsFilters[match.groupe]
                })

            }

            /**
             * Return whether or not at least one group button is clicked, and thus whether only some groups
             * should be shown, or all of them.
             * @returns true if one of group button is activated, false otherwise.
             */
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
            
            var calculBets = function (results) {
                    var matches = results[0];
                    var bets = results[1];

                    var matchesById = {};

                    matches = _.sortBy(matches, function (m) {
                        return m.date;
                    });

                    _.each(matches, function (match) {
                        //initialize bets info with empty object
                        match.bet = { matchId: match.id, score1: null, score2: null };
                        matchesById[match.id] = match;
                    });

                    _.each(bets, function (bet) {
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
                };

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
                    .filter(function (match) {
                        return !match.scoreUpdated && new Date(match.date) > new Date(now) 
                        && match.bet.score1 != null && match.bet.score2 != null;
                    })
                    .pluck('bet')
                    .value();
            
                Events.postBets({'id': $scope.event.id}, bets, function () {
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
            $scope.issueDuPari = betMatchService.issueDuPari;
            $scope.isMaxPoints = betMatchService.isMaxPoints;
            $scope.rightWinner = betMatchService.rightWinner;
            $scope.noPoints = betMatchService.noPoints;
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

            $scope.matchesForKey = function (key) {
                if ($scope.showByDate) {
                    return $scope.matchesByDate[key]
                }
                else {
                    return $scope.matchesByGroup[key]
                }
            };


            /**
             * Return whether or not the matches corresponding to this key should be shown. If the display mode is set to
             * true, then matches should always be displayed. If the displaying mode is set to group, then shouldShowGroup
             * is called to decide whether or not matches corresponding to the group should be displayed.
             * @param group
             * @returns true if matches corresponding to the key should be displayed, false otherwise
             */
            $scope.shouldShowKeyMatch = function (key) {
                if ($scope.showByDate) {
                    return true;
                }
                else {
                    return $scope.shouldShowGroup(key)
                }
            };

            $scope.showMatch = function (group) {
                if ($scope.showByDate) {
                    return $scope.shouldShowGroup(group);
                }
                else {
                    return true
                }
            };

            $scope.displayScore = function (score) {
                if (score||score==0) {
                    return score;
                }
                else {
                    return '-';
                }
            }

            $scope.focusToday = function () {
                $('html,body').animate({
                    scrollTop: $("#todayFocused").offset().top - 55
                }, 1000);
            }

            $scope.removeOther = function () {
                delete $scope.otherGambler;
            }
            $scope.isPool=function(groupe){
                return _.contains($scope.groups1,groupe);
            }
            
            $scope.showBets = function(event) {
                $scope.event = event;
                var params = {id : $scope.event.id};
                $q.all([Events.matches(params).$promise, Events.getBets(params).$promise]).then(calculBets);
            }
            
            //si deja selectionné
            if($scope.selectedEvent != undefined) {
                $scope.showBets($scope.selectedEvent);
            }
            $scope.$on('eventChanged', function(event, params) {
                $scope.showBets(params.event);
            })


        }]);
