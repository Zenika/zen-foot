'use strict';

/**
 * Created by raphael on 26/08/14.
 */

angular.module('zenFoot.app')
    .factory('AdminService', function () {
        return {
            invalidEventName: function (registeredEvents, typedEventName) {
                return _.some(registeredEvents, function (eventName) {
                    return eventName.name === typedEventName;
                });
            }
        }
    })
