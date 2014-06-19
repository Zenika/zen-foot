'use strict';

angular.module('zenFoot.app')
    .factory('authService',
    function ($location,$rootScope,Session,$state) {
        var redirectUrl;
        return {
            redirectToLogin: function () {
                redirectUrl = $location.path();
                $state.go('loginState');
            },

            logout:function () {
                Session.delete(function () {
                    $state.go('loginState');
                });
                Session.user.connected = false;
                delete Session.user.fullName;
                delete Session.user.email;
            }



        };
    });