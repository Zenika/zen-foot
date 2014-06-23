'use strict';

angular.module('zenFoot.app')
    .factory('Gambler', ['$resource',
        function ($resource) {
            return $resource('/api/gambler/:email',{email:'@email'},{get:{cache:false,method:'GET'}});
        }])
    .factory('GamblerRanking', ['$resource',
        function ($resource) {
            return $resource('/api/ranking',{},{get:{cache:false,method:'GET'}});
        }])
    .factory('GamblerService', ['Gambler', '$resource', function (Gambler, $resource) {
        return {
            getAll: function () {
                return $resource('/api/gamblers',{},{get:{cache:false,method:'GET'}}).query();
            },
            get: function (team) {
                return $resource('/api/gamblersTeam/' + team.name,{},{get:{cache:false,method:'GET'}}).query();
            },
            getFromEmail:function(email){
                return Gambler.get({email:email});
            }
        }
    }]);