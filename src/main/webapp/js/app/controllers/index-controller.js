'use strict';

angular.module('zenFoot.app')
    .controller('IndexCtrl', function ($rootScope, $scope, $state, Session, $http, Gambler, authService, Events, User) {

        function onConnected(principal) {
            Session.user.connected = true;
            Session.user.email = principal.email;
            Session.user.lastName = principal.lastName;
            Session.user.firstName = principal.firstName;
            Session.user.roles = principal.roles;

            Session.get().$promise.then( function(data) {
               $rootScope.user= User.get({id:data.principal.email});
            });
        }

        $rootScope.user = {roles:[]};
        $scope.events = Events.query();

        $scope.$on('AUTHENTICATED', function (event, principal) {
            $log.log('AUTHENTICATED caught');
            onConnected(principal);
        });

        Session.get().$promise
            .then(function (data) {
                return data.principal;
            })
            .then(onConnected)
            .catch(function () {
                Session.user.connected = false;
                delete Session.user.fullName;
                delete Session.user.email;
            });


        $scope.login = function () {
            $state.go('loginState')
        };

        $scope.isAdmin = function(){
            return authService.isAdmin($rootScope.user);
        };

        $scope.isConnected = authService.isConnected;

        $scope.logout = authService.logout;

        $scope.isActive = function (state) {
            return $state.includes(state);
        };

        $scope.hideNavBar = function () {
            return $state.current.name == 'loginState' || $state.current.name.trim() == '';
        };

        $scope.eventChanged = function () {
            $rootScope.$broadcast("eventChanged", {event: $scope.selectedEvent});
        }

    });
