'use strict';

angular.module('zenFoot.app')
    .controller('IndexCtrl', function ($rootScope, $scope, $state, Session, authService, Events, $log, $cookies) {

        function onConnected(principal) {
            Session.user.connected = true;
            Session.user.email = principal.email;
            Session.user.nom = principal.nom;
            Session.user.prenom = principal.prenom;
            Session.user.roles = principal.roles;
        }

        $scope.events = Events.query();

        $rootScope.user = Session.user;
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
