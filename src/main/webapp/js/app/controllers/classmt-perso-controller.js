'use strict';

angular.module('zenFoot.app')
    .controller('PersoRankingCtrl', ['$stateParams', 'GamblerService', '$scope', 'Match', 'Gambler', '$q', 'displayService','betMatchService', function ($stateParams, GamblerService, $scope, Match, Gambler, $q, displayService,betMatchService) {



        $q.all([Match.query().$promise, GamblerService.getCouple($stateParams.gamblerId).$promise]).then(function (results) {
            var matches = results[0];
            $scope.otherGambler = results[1][1];
            $scope.gambler = results[1][0];

            var matchesById = {};

            matches = _.sortBy(matches, function (m) {
                return m.date;
            });

            _.each(matches, function (match) {
                //initialize bets info with empty object
                match.bet = { matchId: match.id, score1: null, score2: null };
                matchesById[match.id] = match;
            });

            _.each($scope.otherGambler.bets, function (bet) {
                //update bet info
                matchesById[bet.matchId].betOther = bet;
            });

            _.each($scope.gambler.bets, function (bet) {
                //Adding the bet of the current connected gambler
                matchesById[bet.matchId].bet = bet;
            })

            $scope.matches = matches;
        });

        /**
         * We display the match if the result of the match is known, and if the other gambler bet on the match
         * @param match
         * @returns {null|.bet.score1|.bet.score2}
         */
        $scope.showMatch = function (match) {
            return match.score1 && match.score2 && $scope.hasBet(match.betOther);
        }

        $scope.getTeamDisplayName = displayService.getTeamDisplayName;

        $scope.hasBet = function (bet) {
            return bet.score1 && bet.score2;
        }

        $scope.points=betMatchService.calculatePoints;

    }])