'use strict';

angular.module('zenFoot.app')
    .factory('Gambler', ['$resource',
        function ($resource) {
            return $resource('/api/gambler',{},{get:{cache:false,method:'GET'},update:{method:'PUT',isArray:true}});
        }])
    .factory('Gamblers',['$resource',
        function($resource){
            return $resource('/api/gamblers/:id',{id:'@id'});
        }])
    .factory('GamblerRanking', ['$resource',
        function ($resource) {
            return $resource('/api/ranking',{},{get:{cache:false,method:'GET'}});
        }])
    .factory('GamblerService', ['Gambler','Gamblers', '$resource','$q', function (Gambler,Gamblers, $resource,) {
        return {
            getAll: function () {
                return $resource('/api/gamblers',{},{get:{cache:false,method:'GET'}}).query();
            },
            get: function (team) {
                return $resource('/api/gamblersTeam/' + team.name,{},{get:{cache:false,method:'GET'}}).query();
            },
            getFromId:function(id){
                if(id) {
                    return Gamblers.get({id: id});
                }
                else{
                    return $q.when({id:''});
                }
            },
            getBet:function(gambler, matchId){
                for(var i = 0;i<gambler.bets.length;i++){
                    if(gambler.bets[i].matchId==matchId){
                        return gambler.bets[i];
                    }
                }
            }
        }
    }]);