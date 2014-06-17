'use strict';

angular.module('zenFoot.app')
    .factory('matchService', ['$resource',
        function ($resource) {
            return {

                getAll: function () {
                    return $resource('/api/matchsadmin').query();
                }
            }
        }]);