'use strict';

/**
 * All the services relative to match and bets. Used to get matches and bets from the server, get matches which were not
 * registered (in case the client sent a bet for a match that has already started), and also to calculate points for a given bet
 * if the result's known
 */
angular.module('zenFoot.app')
    .factory('postBetService', ['$resource',
        function ($resource) {
            return $resource('/api/bets');
        }]);