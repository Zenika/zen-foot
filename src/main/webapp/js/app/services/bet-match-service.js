'use strict';

/**
 * All the services relative to match and bets. Used to get matches and bets from the server, get matches which were not
 * registered (in case the client sent a bet for a match that has already started), and also to calculate points for a given bet
 * if the result's known
 */
angular.module('zenFoot.app')
    .factory('betMatchService', ['Match',
        function (Match) {

            var equals = function (bet1, bet2) {
                if (bet1 && bet2) {
                    return (bet1.score1.score == bet2.score1.score ) && (bet1.score2.score == bet2.score2.score);
                }
                return false;
            };

            var mark = function (matchBetCl, matchBetServ) {
                if (!equals(matchBetCl.bet, matchBetServ.bet)) {
                    matchBetServ.unreg = true;
                }

            };

            var findBetByMatchId = function (id, matchBetsServ) {
                var toRet;
                for (var x in matchBetsServ) {
                    var matchBetServ = matchBetsServ[x];
                    if (matchBetServ.bet.matchId == id) {
                        toRet = matchBetServ;
                        break;
                    }
                }
                return toRet;

            };

            var knownOutcome = function (match) {
                return scoreGiven(match.outcome);
            };

            var calculatePoints = function (match, bet) {
                if (match.score1 === null || !bet || bet.score1 === null || bet.score2 === null)return;
                var actualSc1 = match.score1;
                var actualSc2 = match.score2;
                var predicSc1 = bet.score1;
                var predicSc2 = bet.score2;
                if (actualSc1 == predicSc1 && actualSc2 == predicSc2)return 1;

                //Team 1 wins
                var team1w = (actualSc1 > actualSc2) && (predicSc1 > predicSc2);
                //Team 2 wins
                var team2w = (actualSc2 > actualSc1) && (predicSc2 > predicSc1);
                //equality
                var equality = (actualSc2 == actualSc1) && (predicSc1 == predicSc2);
                if (team1w || team2w || equality) {
                    return 0;
                }
                else {
                    return -1;
                }
            };

            return {

                getAll: function (callback) {
                    return Match.query(callback);
                },

                markUnreg: function (matchBetsCl, matchBetsServ) {
                    for (var x in matchBetsCl) {
                        var matchBetCl = matchBetsCl[x];
                        var matchBetServ = findBetByMatchId(matchBetCl.bet.matchId, matchBetsServ);
                        mark(matchBetCl, matchBetServ);
                    }
                },


                /**
                 * Calculates the score for one bet once the result is known. This is only used to display to
                 * the user, as the score server side is not kept for every bet.
                 * @param matchBet
                 */
                calculatePoints: calculatePoints,

                is3Points: function (match, bet) {
                    return calculatePoints(match, bet) === 1;
                },

                is1Point: function (match, bet) {
                    return calculatePoints(match, bet) === 0;
                },

                is0Point: function (match, bet) {
                    return calculatePoints(match, bet) === -1;
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