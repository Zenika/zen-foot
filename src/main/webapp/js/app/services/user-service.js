(function () {
    'use strict';

    angular.module('zenFoot.app')
        .factory('User', ['$resource', UserResource])
        .service('UserService', ['$q', UserService]);

    function UserResource($resource) {
        var userResource = $resource('/api/users/:id',
            {id: '@email'},
            {
                resetPWD : {method: 'PUT', url: '/api/users/:id/resetpwd'},
                activate : {method: 'PUT', url: '/api/users/:id/activate'}
            });

        return userResource;
    };

    function UserService($q) {
        this.deleteUsers = function (users) {
            return queueActionForAll(function(user){
                return user.$delete();
            }, users);
        };

        this.resetPWDs = function (users) {
            return queueActionForAll(function(user){
                return user.$resetPWD();
            }, users);
        };

        this.activateUsers = function (users) {
            return queueActionForAll(function(user){
                return user.$activate();
            }, users);
        };

        function queueActionForAll(action, all){
            var promises = [];
            _.forEach(all, function (one) {
                promises.push(action(one));
            });
            return $q.all(promises);
        };
    };
})();