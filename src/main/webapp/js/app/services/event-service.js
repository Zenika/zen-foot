'use strict';

angular.module('zenFoot.app')
    .factory('Events', ['$resource', function ($resource) {
        return $resource('/api/events/:id', {id: '@id', idLigue: '@idLigue'},
            { 
                'update': {method: 'PUT'},
                'matches' : {method: 'GET', 'url' : '/api/events/:id/matches', isArray : true},
                'getBets' : {method: 'GET', 'url' : '/api/events/:id/bets', isArray : true},
                'postBets' : {method: 'POST', 'url' : '/api/events/:id/bets'},
                'updateMatchScore' :  {method: 'POST', 'url' : '/api/events/:id/matchs'},
                'getGamblers' : {method: 'GET', 'url' : '/api/events/:id/gamblers', isArray : true},
                'getGambler' : {method: 'GET', 'url' : '/api/events/:id/gambler'},
                'getLigues' : {method: 'GET', 'url' : '/api/events/:id/ligues', isArray : true},
                'getLigue' : {method: 'GET', 'url' : '/api/events/:id/ligues/:idLigue'},
                'updateLigue' : {method: 'PUT', 'url' : '/api/events/:id/ligues/:idLigue'},
                'createLigues' : {method: 'POST', 'url' : '/api/events/:id/ligues'},
                'joinLigues' : {method: 'POST', 'url' : '/api/events/:id/ligues/:idLigue/join'},
            });
    }])