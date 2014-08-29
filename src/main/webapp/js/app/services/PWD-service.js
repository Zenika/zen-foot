'use strict';

/**
 * Created by raphael on 25/08/14.
 */
angular.module('zenFoot.app').factory('Pwd', ['$resource', function ($resource) {
    return $resource('/api/changePW');
}])