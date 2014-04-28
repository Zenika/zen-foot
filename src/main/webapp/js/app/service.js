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

    .factory('matchService',['Match', function(Match){
        return {
            getAll:function(){
                return Match.query();
            }
        }
    }])


