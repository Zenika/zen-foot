'use strict';

angular.module('zenFoot.app')
    .factory('Gambler', ['$resource',
        function ($resource) {
            return $resource('/api/gambler');
        }])
    .factory('GamblerRanking', ['$resource',
        function ($resource) {
            return $resource('/api/ranking');
        }])
    .factory('GamblerService', ['Gambler', '$resource', function (Gambler, $resource) {
        return {
            getAll: function () {
                return $resource('/api/gamblers').query();
            },
            get: function (team) {
                return $resource('/api/gamblersTeam/' + team.name).query()
            }
        }
    }]);