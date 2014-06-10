'use strict';

angular.module('zenFoot.app')
    .factory('User',
    function ($resource) {

        var user = $resource('/api/sessions/:email', null,
            {email: '@email'},
            {
                'get': {method: 'GET', withCredentials: true},
                'save': {method: 'POST', withCredentials: true}
            });

        return user;
    });