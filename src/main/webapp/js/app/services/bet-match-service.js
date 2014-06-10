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

            /**
             * Return whether or not the prediction contains a result (either the outcome of a match or a bet).
             * @param prediction
             * @returns {boolean} true if the prediction is not empty (i.e. the scores are not ==""
             */
            var scoreGiven = function (prediction) {
                return prediction.score1.score.trim() != "" && prediction.score2.score.trim() != "";
            };


            var betMade = function (bet) {
                return scoreGiven(bet);
            };

            var knownOutcome = function (match) {
                return scoreGiven(match.outcome);
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
                calculatePoints: function (matchBet) {
                    var match = matchBet.match;
                    var bet = matchBet.bet;

                    //Conditions pour pouvoir calculer les points : l'outcome du match est connu et le parieur a fait un pronostic
                    if (knownOutcome(match) && betMade(bet)) {
                        var actualSc1 = match.outcome.score1.score;
                        var actualSc2 = match.outcome.score2.score;
                        var predicSc1 = bet.score1.score;
                        var predicSc2 = bet.score2.score;
                        if (actualSc1 == predicSc1 && actualSc2 == predicSc2)return 'img/points/full-ball-xs.png'
                        if ((actualSc1 > actualSc2) == (predicSc1 > predicSc2)) {
                            return 'img/points/half-ball-xs.png'
                        }
                        else {
                            return 'img/points/empty-ball-xs.png'
                        }
                    }
                },

                knownOutcome: knownOutcome,

                betMade: betMade
            }
        }]);