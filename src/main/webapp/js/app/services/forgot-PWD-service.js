/**
 * Created by raphael on 18/08/14.
 */

angular.module('zenFoot.app')
    .factory('PWDLink', ['$resource', function ($resource) {
        return $resource('api/generateLink', null, {'create':{method:'POST'}});
    }])
    .factory('ResetPWD', ['$resource', function ($resource) {
        return $resource('api/resetPWD');
    }])
