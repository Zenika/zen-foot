'use strict';

angular.module('zenFoot.app')
  .controller('UserCtrl', function ($rootScope, $scope, $state, Session, User) {
        function onConnected(principal) {
           // debugger;
            Session.user.connected = true;
            Session.user.email = principal.email;
            Session.user.fullName = principal.fullName;
        }

        $scope.user = Session.user;
        $scope.$on('AUTHENTICATED', function(event, principal) {
            onConnected(principal);
        })

        User.get({email: 'current' }).$promise
            .then(onConnected)
            .catch(function() {
                Session.user.connected = false;
                delete Session.user.fullName;
                delete Session.user.email;
            });

        $scope.login = function() {
            $location.path('/login');
        }

        $scope.logout = function() {
            Session.delete(function() { $state.go('loginState'); });
            Session.user.connected=false;
            delete Session.user.fullName;
            delete Session.user.email;
        }

        $scope.loggedIn= function(){
            return Session.user.connected;
        }

    });
