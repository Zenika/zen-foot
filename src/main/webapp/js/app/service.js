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
    s.user.isAdmin=function(){
        //return true;

        return _.contains(this.principal.roles,'ADMIN');
    }

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

       var user=  $resource('/api/sessions/:email', null,
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

    .factory('matchService', ['Match', function (Match) {

        var equals=function(bet1,bet2){
            if(bet1&&bet2){
                return (bet1.score1.score==bet2.score1.score )&&(bet1.score2.score==bet2.score2.score);
            }
            return false;
        }

        var mark=function(matchBetCl, matchBetServ){
            if(!equals(matchBetCl.bet,matchBetServ.bet)){
                matchBetServ.unreg=true;
            }

        }



        var findBetByMatchId=function(id, matchBetsServ){
            var toRet;
            for(var x in matchBetsServ){
                var matchBetServ = matchBetsServ[x];
                if(matchBetServ.bet.matchId==id){
                    toRet=matchBetServ;
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



            markUnreg:function(matchBetsCl, matchBetsServ){
                for(var x in matchBetsCl){
                    var matchBetCl = matchBetsCl[x];
                    var matchBetServ = findBetByMatchId(matchBetCl.bet.matchId,matchBetsServ);
                    mark(matchBetCl,matchBetServ);
                }
            }
        }
    }])

    .factory('postBetService',['$resource',function($resource){
        return $resource('/api/bets');
}])


