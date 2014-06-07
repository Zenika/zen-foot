'use strict';

angular.module('zenFoot.app')
    .factory('authService',
    function ($location) {
        var redirectUrl;
        return {
            redirectToLogin: function () {
                redirectUrl = $location.path();
                $location.path('/login');
            }

        };
    });