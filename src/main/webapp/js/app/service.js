'use strict';

// ZenFoot service

var zenFootService = angular.module("zenFoot.services", [ 'ngResource', 'ngRoute' ]);


// Contact resource

zenFootService.factory('Session', function ($resource) {
    var s = $resource('/api/sessions/:sessionKey', {sessionKey: 'current'},
        {
            'get': {method: 'GET', withCredentials: true},
            'save': {method: 'POST', withCredentials: true},
            'delete': {method: 'DELETE', withCredentials: true}
        });
    s.user = { connected: false };
    return  s;
})
    .factory('SecurityHttpInterceptor', function ($q, $location) {
        return function (promise) {
            return promise.then(function (response) {
                return response;
            }, function (response) {
                if (response.status == 401 || response.status == 403) {
                    $location.path('/login');
                   // $state.go('loginState');
                }
                return $q.reject(response);
            });
        }
    })
    .config(function ($httpProvider) {
        $httpProvider.responseInterceptors.push('SecurityHttpInterceptor');
    })

    .factory('authService', function ($location, $cookieStore) {
        var redirectUrl;
        return {
            maybeRedirect: function () {
                $location.path(redirectUrl ? redirectUrl : "/index")
            },
            logout: function () {
                $cookieStore.remove('Auth-Token')
            },
            token: function () {
                return $cookieStore.get('Auth-Token')
            },
            redirectToLogin: function () {
                redirectUrl = $location.path();
                $location.path('/login');
            },
            storeToken: function (response) {
                var token = response.headers('Auth-Token');
                if (token) {
                    $cookieStore.put('Auth-Token', token);
                }
            }
        };
    })

    .factory('User', function ($resource) {
        return $resource('/api/sessions/:email', null,
            {email:'@email'},
            {
                'get': {method: 'GET', withCredentials: true},
                'save': {method: 'POST', withCredentials: true}
            });
    });



