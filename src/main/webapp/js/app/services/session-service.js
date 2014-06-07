'use strict';

angular.module('zenFoot.app').
    factory('Session', function ($resource) {
        var s = $resource('/api/sessions/:currentkey', {currentkey: 'current'},
            {
                'get': {method: 'GET', withCredentials: true},
                'save': {method: 'POST', withCredentials: true},
                'delete': {method: 'DELETE', withCredentials: true}
            });
        s.user = { connected: false };

        return  s;
    });