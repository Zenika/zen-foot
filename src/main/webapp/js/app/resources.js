'use strict';

angular.module('zenFoot.app')
    .factory('Match', ['$resource', function ($resource) {
        return $resource('/api/matchs/:id', {id: '@id'});
    }])
    .factory('Bets', ['$resource', function ($resource) {
        return $resource('/api/bets');
    }])
    .factory('Pays',['$http',function($http){
        return {
            getPays:function(){
                return $http({method:'GET',url:'/api/pays'});
            }
        }
    }])
    .factory('Pwd', ['$resource', function ($resource) {
        return $resource('/api/changePW');
    }])
    .factory('Team',['$resource',function($resource){
        return $resource('api/teams/:id',{id:'@id'});
    }])
    .factory('GamblerStatutTeam',function($resource){
        return $resource('api/gamblersAndTeam',{},{update:{method:'PUT',isArray:false}});
    })