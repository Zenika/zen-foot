'use strict';

// Contact service

var zenContactService = angular.module("zenContact.services", [ 'ngResource' ]);


// Contact resource

zenContactService.factory('Contact', [ '$resource', function ($resource) {

    return $resource('/rest/contacts/:id', {
        id: '@id'
    }, {
        update: {
            method: 'PUT',
            id: '@id'
        }
    });
} ]);

zenContactService.factory('Session', function ($resource) {
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
                }
                return $q.reject(response);
            });
        }
    })
    .config(function ($httpProvider) {
        $httpProvider.responseInterceptors.push('SecurityHttpInterceptor');
    })
;



