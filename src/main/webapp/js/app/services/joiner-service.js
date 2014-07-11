'use strict';

angular.module('zenFoot.app')
    .factory('Joiners', ['$resource',
        function ($resource) {
            return {
                getAll: function () {
                    return $resource('/api/wannajoin').query();
                },
                getFromTeam : function(id){
                    return $resource('/api/wannajoinTeam/:id',{id:'@id'}).query({id:id});
                },
                postJoiner: function (joiner) {
                    $resource('/api/joiner').save(joiner)
                }
            }
        }]);