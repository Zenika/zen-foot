'use strict';

angular.module('zenFoot.app')
    .factory('Match', ['$resource', function ($resource) {
        return $resource('/api/matchs/:id', {id: '@id'});
    }])
    .factory('Bets', ['$resource', function ($resource) {
        return $resource('/api/bets');
    }]);