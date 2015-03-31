'use strict';

angular.module('zenFoot.app')
    .factory('Events', ['$resource', function ($resource) {
        return $resource('/api/events/:id', {id: '@id'},
            { 
                'update': {method: 'PUT'},
                'matches' : {method: 'GET', 'url' : '/api/events/:id/matches', isArray : true}
            });
    }])