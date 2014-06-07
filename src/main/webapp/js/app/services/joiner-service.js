'use strict';

angular.module('zenFoot.app')
    .factory('Joiners', ['$resource',
        function ($resource) {
            return {
                getAll: function () {
                    return $resource('/api/wannajoin').query();
                },
                postJoiner: function (joiner) {
                    $resource('/api/joiner').save(joiner)
                }
            }
        }]);