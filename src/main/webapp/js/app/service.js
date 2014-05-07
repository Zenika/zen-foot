'use strict';

// ZenFoot service

var zenFootService = angular.module("zenFoot.services", [ 'ngResource', 'ngRoute' ]);


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
        return $resource('/api/sessions/:email', null,
            {email: '@email'},
            {
                'get': {method: 'GET', withCredentials: true},
                'save': {method: 'POST', withCredentials: true}
            });
    })

    .factory('Match', ['$resource', function ($resource) {
        return $resource('/api/matchs');
    }])

    .factory('matchService', ['Match', function (Match) {

        function findMatchById(id, matchBets) {
            var toRet;
            for (var matchBet in matchBets) {
                if (matchBet.match.id == id) {
                    toRet = matchBet;
                    break;
                }
            }
            return toRet;

        }


        return {
            getAll: function () {
                var objTmp = Match.query();
                //.bet.score1.score
                return objTmp;
            },

            signalUnreg: function (betServs, betCls) {
                for (var betCl in betCls) {
                    var betServ = findMatchById(betCl.match.id, betServs);
                    var bool1 = betServ.bet.score1.score == betCl.bet.score.score1;
                    var bool2 = betServ.bet.score2.score == betCl.bet.score.score2;
                    if(!(bool1&&bool2)){
                        betServ.unregistered=true;
                    }
                }
            }
        }
    }])

    .factory('postBetService', ['$resource', function ($resource) {
        return $resource('/api/bets');
    }])


