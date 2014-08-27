'use strict';

/**
 * Created by raphael on 26/08/14.
 */

angular.module('zenFoot.app')
    .factory('AdminService', ['$resource',function ($resource) {
        return {
            archiveEvent: function(eventName){
                $resource('/api/archive').save({string:eventName});
            }
        }
    }])