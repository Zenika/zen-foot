'use strict';

angular.module('zenFoot.app')
    .factory('Match', ['$resource', function ($resource) {
        return $resource('/api/matchbets');
    }]);