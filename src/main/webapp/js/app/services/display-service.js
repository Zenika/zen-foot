'use strict';

angular.module('zenFoot.app')
    .factory('displayService', ['betMatchService',
        function (betMatchService) {
            return {
                isTeam1Winner: function (bet) {
                    return (bet.score1 !== undefined && bet.score2 !== undefined && bet.score1 > bet.score2);
                },
                isTeam2Winner: function (bet) {
                    return (bet.score1 !== undefined && bet.score2 !== undefined && bet.score1 < bet.score2);
                },
                isWinner: function (match, scoreConcerne, autreScore) {
                    var scoreConcerne = match[scoreConcerne];
                    var autreScore = match[autreScore];
                    if ((!autreScore) || autreScore.trim() == '') return false;
                    return (scoreConcerne > autreScore);
                },
                dispPoints: function (matchBet) {
                    return betMatchService.knownOutcome(matchBet.match) && betMatchService.betMade(matchBet.bet);
                },
                getTeamDisplayName: function(code){
                    switch (code) {
                        case 'croatie':
                            return 'Croatie';
                        case 'bresil':
                            return 'Brésil';
                        case 'cameroun':
                            return 'Cameroun';
                        case 'mexique':
                            return 'Mexique';
                        case 'espagne':
                            return 'Espagne';
                        case 'paysBas':
                            return 'Pays-Bas';
                        case 'chili':
                            return 'Chili';
                        case 'australie':
                            return 'Australie';
                        case 'colombie':
                            return 'Colombie';
                        case 'grece':
                            return 'Grece';
                        case 'uruguay':
                            return 'Uruguay';
                        case 'costaRica':
                            return 'Costa Rica';
                        case 'angleterre':
                            return 'Angleterre';
                        case 'italie':
                            return 'Italie';
                        case 'coteIvoir':
                            return 'Côte d\'Ivoire';
                        case 'japon':
                            return 'Japon';
                        case 'suisse':
                            return 'Suisse';
                        case 'equateur':
                            return 'Equateur';
                        case 'honduras':
                            return 'Honduras';
                        case 'france':
                            return 'France';
                        case 'argentine':
                            return 'Argentine';
                        case 'bosnie':
                            return 'Bosnie-et-H.';
                        case 'allemagne':
                            return 'Allemagne';
                        case 'portugal':
                            return 'Portugal';
                        case 'iran':
                            return 'Iran';
                        case 'nigeria':
                            return 'Nigéria';
                        case 'ghana':
                            return 'Ghana';
                        case 'usa':
                            return 'USA';
                        case 'belgique':
                            return 'Belgique';
                        case 'algerie':
                            return 'Algérie';
                        case 'russie':
                            return 'Russie';
                        case 'coree':
                            return 'Corée du Sud';
                    }
                }
            }
        }]);