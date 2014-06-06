'use strict';

// ZenFoot service

var zenFootService = angular.module("zenFoot.services", [ 'ngResource', 'ngRoute']);


// Contact resource

zenFootService.factory('Session', function ($resource) {
    var s = $resource('/api/sessions/:currentkey', {currentkey: 'current'},
        {
            'get': {method: 'GET', withCredentials: true},
            'save': {method: 'POST', withCredentials: true},
            'delete': {method: 'DELETE', withCredentials: true}
        });
    s.user = { connected: false };

    return  s;
})


    .factory('authService', function ($location, $cookieStore) {
        var redirectUrl;
        return {
            maybeRedirect: function () {
                $location.path(redirectUrl ? redirectUrl : "/index")
            },


            redirectToLogin: function () {
                redirectUrl = $location.path();
                $location.path('/login');
            }

        };
    })

    .factory('User', function ($resource) {

        var user = $resource('/api/sessions/:email', null,
            {email: '@email'},
            {
                'get': {method: 'GET', withCredentials: true},
                'save': {method: 'POST', withCredentials: true}
            });

        return user;
    })

    .factory('Match', ['$resource', function ($resource) {
        return $resource('/api/matchbets');
    }])


/**
 * All the services relative to match and bets. Used to get matches and bets from the server, get matches which were not
 * registered (in case the client sent a bet for a match that has already started), and also to calculate points for a given bet
 * if the result's known
 */
    .factory('betMatchService', ['Match', function (Match) {


        var equals = function (bet1, bet2) {
            if (bet1 && bet2) {
                return (bet1.score1.score == bet2.score1.score ) && (bet1.score2.score == bet2.score2.score);
            }
            return false;
        }

        var mark = function (matchBetCl, matchBetServ) {
            if (!equals(matchBetCl.bet, matchBetServ.bet)) {
                matchBetServ.unreg = true;
            }

        }


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

        }

        /**
         * Return whether or not the prediction contains a result (either the outcome of a match or a bet).
         * @param prediction
         * @returns {boolean} true if the prediction is not empty (i.e. the scores are not ==""
         */
        var scoreGiven = function (prediction) {
            return prediction.score1.score.trim() != "" && prediction.score2.score.trim() != "";
        }


        var betMade = function (bet) {
            return scoreGiven(bet);
        }

        var knownOutcome = function (match) {
            return scoreGiven(match.outcome);
        }

        return {

            getAll: function (callback) {
                var objTmp = Match.query(callback);
                //.bet.score1.score
                return objTmp;
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
    }])

    .factory('postBetService', ['$resource', function ($resource) {
        return $resource('/api/bets');
    }])

/**
 * This service is used for any information that's required for display functionalities
 */
    .factory('displayService', ['betMatchService', function (betMatchService) {
        return {
            isWinner: function (score, scoreConcerne, autreScore) {
                var scoreConcerne = score[scoreConcerne]
                var autreScore = score[autreScore]
                if ((!autreScore.score) || autreScore.score.trim() == '') return false;
                return (scoreConcerne.score > autreScore.score);
            },
            dispPoints: function (matchBet) {
                return betMatchService.knownOutcome(matchBet.match) && betMatchService.betMade(matchBet.bet);
            }
        }
    }])

    .factory('Gambler', ['$resource', function ($resource) {
        return $resource('/api/gambler');
    }])

    .factory('GamblerService', ['Gambler', '$resource', function (Gambler, $resource) {
        return {
            getAll: function () {
                return $resource('/api/gamblers').query();
            },
            get: function (team) {
                return $resource('/api/gamblersTeam/' + team.name).query()
            }
        }
    }])

    .factory('Joiners', ['$resource', function ($resource) {
        return {
            getAll: function () {
                return $resource('/api/wannajoin').query();
            },
            postJoiner: function (joiner) {
                $resource('/api/joiner').save(joiner)
            }
        }
    }])

    .factory('ProfilService', ['$resource', function ($resource) {
        var isOwner = function (statutTeam, gambler) {
            return statutTeam.team.ownerEmail == gambler.email
        }

        var getOwnerTeams = function (statutTeams, gambler) {
            var toRet = [];
            for (var x in statutTeams) {
                if (isOwner(statutTeams[x], gambler)) {
                    toRet.push(statutTeams[x].team);
                }
            }
            return toRet;
        }

        /**
         * Create objects linking a gambler with a team he wishes to join
         * @param statutTeams
         * @param gambler
         * @param gamblers
         */
        var gamblerTeam = function (statutTeams, gambler, gamblers) {
            var coupleArray = [];
            var ownerTeams = getOwnerTeams(statutTeams, gambler)
            for (var x in gamblers) {
                var applicant = gamblers[x];
                for (var y in applicant.statutTeams) {
                    var statutTeam = applicant.statutTeams[y];
                    if (isOwner(statutTeam, gambler)) {
                        coupleArray.push({gambler: applicant, statutTeam: statutTeam})
                    }
                }
            }
            return coupleArray
        }

        return {
            isOwner: isOwner,

            getOwnerTeams: getOwnerTeams,

            gamblerTeam: gamblerTeam,


        }
    }])

    .factory('Team', ['$resource', function ($resource) {
        return {
            getAll: function () {
                return $resource('/api/teams').query()
            },

            hasNewGroup: function (subscribedTeams) {

                for (var x in subscribedTeams) {

                    if (subscribedTeams[x].isNew) {
                        return true;
                    }
                }
                return false;

            }
        }
    }])



