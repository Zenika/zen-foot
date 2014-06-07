'use strict';

angular.module('zenFoot.app')
    .factory('Team', ['$resource',
        function ($resource) {
            return {
                getAll: function () {
                    return $resource('/api/teams').query()
                },

                hasNewGroup: function (subscribedTeams) {

                    for (var x in subscribedTeams) {

                        if (subscribedTeams[x].isNew) {
                            return true;
                        }
                    }
                    return false;

                }
            }
        }]);