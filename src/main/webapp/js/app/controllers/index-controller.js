'use strict';

angular.module('zenFoot.app')
    .controller('IndexCtrl', function ($rootScope, $scope, $state, Session, $http, Gambler, authService, Events) {

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
            onConnected(principal);
        });

        $http.get('/api/sessions/current', {withCredentials: true})
            .then(function (response) {
                return response.data.principal;
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

        $scope.logout = authService.logout

        $scope.loggedIn = function () {
            return Session.user.connected;
        };

        $rootScope.isAdmin = function () {
            return _.contains($rootScope.user.roles, 'ADMIN');
        };

        $rootScope.isConnected = function () {
            return $rootScope.user.connected;
        };

        $scope.isActive = function (state) {
            return $state.includes(state);
        };

        $scope.hideNavBar = function () {
            return $state.current.name == 'loginState' || $state.current.name.trim() == ''
        };

        $scope.eventChanged = function () {
            $rootScope.$broadcast("eventChanged", {event: $scope.selectedEvent});
        }

    });
