'use strict';

angular.module('zenFoot.app')
    .factory('authService',
    function (Session, $rootScope, $state) {
        return {
            redirectToHome: function () {
                if ($rootScope.isAdmin()) {
                    $state.go('adminState');
                } else {
                    $state.go('betsState');
                }
            },
            logout: function () {
                Session.delete(function () {
                   $state.go($state.current.name,null,{reload:true});
                });
                Session.user.connected = false;
                delete Session.user.fullName;
                delete Session.user.email;
            }
        };
    });