'use strict';

/**
 * Created by raphael on 26/08/14.
 */

angular.module('zenFoot.app')
    .factory('AdminService', ['$resource', function ($resource) {
        return {
            archiveEvent: function (eventName, success, error) {
                $resource('/api/archive').save({string: eventName}, success, error);
            },

            eventNames: function () {
                return $resource('/api/eventNames').query();
            },

            invalidEventName: function (registeredEvents, typedEventName) {
                return _.some(registeredEvents, function (eventName) {
                    return eventName.name === typedEventName;
                });
            }
        }
    }])