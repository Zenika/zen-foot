'use strict';

/**
 * Created by raphael on 09/07/14.
 */

angular.module('zenFoot.app')
    .factory('GuestService', ['$resource',
        function ($resource) {
            return {
                invite: function (gamblerStatutTeam, success, error) {
                    gamblerStatutTeam.statutTeam.invitation = true;
                    $resource('/api/invite').save(gamblerStatutTeam, success, error);
                },
                accept: function (gamblerStatutTeam, success, error) {
                    gamblerStatutTeam.statutTeam.accepted = true;
                    $resource('/api/joinLigue',{}, {update: {method: 'PUT'}}).update(gamblerStatutTeam);
                }
            }
        }
    ]);


