'use strict';

angular.module('zenFoot.app')
    .factory('authService',
    function (Session, $rootScope, $state, $cookies, $cacheFactory) {
        return {
            redirectToHome: function () {
                if ($rootScope.isAdmin()) {
                    $state.go('adminState');
                } else {
                    $state.go('betsState');
                }
            },
            logout: function () {
                Session.delete(null, function () {
                    delete $cookies.RestxSession;
                    //Cache is set on this resource, delete it on logout
                    var httpCache = $cacheFactory.get('$http');
                    httpCache.remove('/api/sessions/current');
                    $state.go('loginState');
                });
                Session.user.connected = false;
                delete Session.user.fullName;
                delete Session.user.email;
            },
            isAdmin: function(user){
                return _.contains(user.roles, 'ADMIN');
            },
            isConnected: function () {
                return angular.isDefined($cookies.RestxSession);
            }
        };
    });