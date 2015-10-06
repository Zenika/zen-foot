(function () {
    'use strict';

    angular.module('zenFoot.app')
        .factory('User', ['$resource', UserResource])
        .service('UserService', UserService);

    function UserResource($resource) {
        var userResource = $resource('/api/users/:id',
            {id: '@email'},
            {resetPWD : {
                method: 'PUT',
                url: '/api/users/:id/resetpwd'
            }});

        return userResource;
    };

    function UserService() {
        this.deleteUsers = function (users) {
            var promises = [];
            _.forEach(selectedItems, function (user) {
                promises.push(user.$delete());
            });
            return $q.all(promises);
        };

        this.resetPWDs = function (users) {
            var promises = [];
            _.forEach(selectedItems, function (user) {
                promises.push(user.$resetPWD());
            });
            return $q.all(promises);
        };
    };
})();