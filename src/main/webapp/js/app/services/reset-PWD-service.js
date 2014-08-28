/**
 * Created by raphael on 18/08/14.
 */

angular.module('zenFoot.app')
    .factory('PWDLink', ['$resource', function ($resource) {
        return $resource('api/generateLink', null, {'create': {method: 'POST'}});
    }])
    .factory('ResetPWD', ['$http', function ($http) {
        return {
            save: function (data, success, error) {
                $http({method: 'POST', url: '/api/resetPWD', data: data}).success(success).error(error);
            }
        }
    }])
    .factory('CheckPWDLink', ['$http', function ($http) {
        return {
            check: function (pwdLinkId, success, error) {
                $http({method: 'GET', url: '/api/checkPWDLink/'+pwdLinkId}).success(success).error(error);
            }
        }
    }]);
