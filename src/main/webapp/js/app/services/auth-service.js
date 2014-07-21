'use strict';

angular.module('zenFoot.app')
    .factory('authService',
    function (Session, $rootScope, $state) {
        return {
            redirectToHome: function () {
                if ($rootScope.isAdmin()) {
                    $state.go('index.adminState');
                } else {
                    $state.go('index.betsState');
                }
            },
            logout: function () {
                Session.delete(function () {
                    $state.go('unconnected.login');
                });
                Session.user.connected = false;
                delete Session.user.fullName;
                delete Session.user.email;
            }
        };
    });