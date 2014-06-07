'use strict';

angular.module('zenFoot.app')
    .factory('displayService', ['betMatchService',
        function (betMatchService) {
            return {
                isWinner: function (score, scoreConcerne, autreScore) {
                    var scoreConcerne = score[scoreConcerne];
                    var autreScore = score[autreScore];
                    if ((!autreScore.score) || autreScore.score.trim() == '') return false;
                    return (scoreConcerne.score > autreScore.score);
                },
                dispPoints: function (matchBet) {
                    return betMatchService.knownOutcome(matchBet.match) && betMatchService.betMade(matchBet.bet);
                }
            }
        }]);