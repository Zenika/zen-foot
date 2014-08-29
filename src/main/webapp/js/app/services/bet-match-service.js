'use strict';

/**
 * All the services relative to match and bets. Used to get matches and bets from the server, get matches which were not
 * registered (in case the client sent a bet for a match that has already started), and also to calculate points for a given bet
 * if the result's known
 */
angular.module('zenFoot.app')
    .factory('Match', ['$resource', function ($resource) {
        return $resource('/api/matchs/:id', {id: '@id'});
    }])
    .factory('Bets', ['$resource', function ($resource) {
        return $resource('/api/bets');
    }])
    .factory('betMatchService', ['Match',
        function (Match) {

            var knownOutcome = function (match) {
                return scoreGiven(match.outcome);
            };

            /**
             * Gives a convention to know whether a bet was won. The choice was made to return arbitrary figures, corresponding to the different
             * cases that can be encountered for the result of a bet, without returning points. Useful if the number of points are changed.
             * @param matchBet
             * @return -1 if the gambler lost the bet, 0 if they had the right winner, 1 if they had the right winner and score
             */
            var issueDuPari = function (match, bet) {
                if (match.score1 === null || !bet || bet.score1 === null || bet.score2 === null)return;

                // If the score given by the gambler is exact :
                if (match.score1 == bet.score1 && match.score2 == bet.score2)return 1;

                // If the gambler has the right winner
                var team1wins = (match.score1 > match.score2) && (bet.score1 > bet.score2);
                var team2wins = (match.score2 > match.score1) && (bet.score2 > bet.score1);
                var equality = (match.score2 == match.score1) && (bet.score1 == bet.score2);

                if (team1wins || team2wins || equality) {
                    return 0;
                }
                //Otherwise
                else {
                    return -1;
                }
            };

            return {

                getAll: function (callback) {
                    return Match.query(callback);
                },


                /**
                 * Gives a convention to know whether a bet was won. The choice was made to return arbitrary figures, corresponding to the different
                 * cases that can be encountered for the result of a bet, without returning points. Useful if the number of points are changed.
                 * @param matchBet
                 * @return -1 if the gambler lost the bet, 0 if they had the right winner, 1 if they had the right winner and score
                 */
                issueDuPari: issueDuPari,

                isMaxPoints: function (match, bet) {
                    return issueDuPari(match, bet) === 1;
                },

                rightWinner: function (match, bet) {
                    return issueDuPari(match, bet) === 0;
                },

                noPoints: function (match, bet) {
                    return issueDuPari(match, bet) === -1;
                },

                knownOutcome: knownOutcome,

                group1:function(){
                    return ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];
                },

                group2:function(){
                    return ['1/8', '1/4', '1/2', 'finale'];
                }
            }
        }]);