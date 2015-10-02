(function () {
    'use strict';

    angular.module('zenFoot.app')
        .factory('User', UserResource);

    function UserResource($resource) {
        var userResource = $resource('/api/users/:id');

        return userResource;
    };
})();